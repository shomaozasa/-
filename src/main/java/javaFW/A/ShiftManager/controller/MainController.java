package javaFW.A.ShiftManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
  @GetMapping("/admin")
  public String admin(Model model,HttpSession session) {
	String authority = (String) session.getAttribute("authority");
  	if (authority == null) {
  		return "redirect:/";
  	}
  	if (!authority.equals("管理者")) {
  		return "redirect:/index";
  	}
    return "admin";
    }
  
  @GetMapping("/index")
  public String index(HttpSession session) {
	String authority = (String) session.getAttribute("authority");
  	if (authority == null) {
  		return "redirect:/";
  	}
    return "index";
  	}
  
  @GetMapping("/keisan")
  public String keisan(HttpSession session) {
	String authority = (String) session.getAttribute("authority");
  	if (authority == null) {
  		return "redirect:/";
  	}
	return "calculation";
  	}
}