package com.lijiazhen.springboot.controller;

import com.lijiazhen.springboot.service.UserService;
import com.lijiazhen.springboot.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/loginPage")
    public String loginPage(){
        return "layout/login";
    }

    @RequestMapping("/login")
    public String login(String name, String password, ModelMap map, HttpSession session){
        User user =userService.login(name,password);
//        System.out.println(user);
//        System.out.println("login"+name);
//        System.out.println("login"+password);
        if (user!=null){
            session.setAttribute("user_login",user);
            return "redirect:/index";
        }
        map.put("msg","用户名或密码错误。");
        return "layout/login";
    }

    @RequestMapping("/register")
    public String register(User user){
        userService.add(user);
        return "redirect:/loginPage";
    }

    @RequestMapping("/registerPage")
    public String registerPage(Model model){

        return "layout/register";

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/loginPage";
    }

}
