package javaFW.A.ShiftManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javaFW.A.ShiftManager.service.UsersService;

@Controller
public class LoginController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/")
    public String login(Model model) {
        return "login";
      }

    @PostMapping("/login")
    public String processLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, RedirectAttributes redirectAttributes, HttpSession session) {
        if (usersService.authenticate(userName, password)) {
        	String authority = usersService.getUserAuthority(userName);
        	if (usersService.getUserAuthority(userName).equals("管理者") && usersService.getUserAuthority(userName) != null) {
        		Long userId = usersService.getUserIdByUserName(userName);
        	    session.setAttribute("userId", userId);
        	    session.setAttribute("authority", authority);
        	    session.setAttribute("userName", userName);
                return "redirect:/admin"; 
        	}
        	else {
        		Long userId = usersService.getUserIdByUserName(userName);
        	    session.setAttribute("userId", userId);
        	    session.setAttribute("authority", authority);
        	    session.setAttribute("userName", userName);
        	    return "redirect:/index";
        	}
        } else {
            redirectAttributes.addFlashAttribute("error", "ログインに失敗しました");
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // セッションを無効にすることでログアウトさせる
        }
        return "redirect:/"; // ログインページにリダイレクト
    }
}
