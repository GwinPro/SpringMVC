package net.jmsolotask.springMVC.controller;


import net.jmsolotask.springMVC.model.User;
import net.jmsolotask.springMVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {
    private UserService userService;
    private String add;
    private String update;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setBookService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(Model model, @RequestParam("name") String name,
                          @RequestParam("email") String email,
                          @RequestParam("phone") String phone) {
        String result = "User not added";
        if (this.userService.addUser(name, email, phone, "user")) {
            result = "User added successfully";
        }
        model.addAttribute("addResult", result);
        return "index";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, Model model, @RequestParam("name") String name,
                          @RequestParam("email") String email) {
        User user = this.userService.getUser(name, email);
        if (user != null) {
            final HttpSession session = request.getSession();
            session.setAttribute("getUser", user);
            if (user.getRole().equals("admin")) {
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }
        } else {
            String result = "Please, register";
            model.addAttribute("addAuth", result);
            return "index";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.removeAttribute("getUser");
        return "index";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage(HttpServletRequest request, Model model) {
        final HttpSession session = request.getSession();
        User user = (User) session.getAttribute("getUser");
        model.addAttribute("userName", user.getName());
        return "user-jsp";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String MainAdminPageGet(Model model) {
        List<User> listUser = userService.getAllClient();
        model.addAttribute("listUser", listUser);
        model.addAttribute("addResult", add);
        model.addAttribute("UpdateResult", update);
        return "admin-jsp";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String MainAdminPageGet() {
       return "redirect:/admin";
    }



    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    public String AddFromAdmin(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("phone") String phone,
                               @RequestParam("role") String role){
        add = "User not added";
        if (userService.addUser(name, email, phone, role)) {
            add = "User added successfully";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateGet(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "forward:/admin";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateGet(@ModelAttribute("user") User user) {
        update = "User not Updated";
        if (userService.updateUser(user)) {
            update = "User updated successfully";
        }
        return "forward:/admin";
    }


}
