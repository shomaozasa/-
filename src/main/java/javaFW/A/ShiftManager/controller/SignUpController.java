package javaFW.A.ShiftManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import javaFW.A.ShiftManager.model.Jobs;
import javaFW.A.ShiftManager.model.Users;
import javaFW.A.ShiftManager.repository.UsersRepository;
import javaFW.A.ShiftManager.service.JobsService;
import javaFW.A.ShiftManager.service.UsersService;

@Controller
public class SignUpController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private JobsService jobsService;

	@GetMapping("/signup")
	public String showSignUpForm() {
		return "signup";
	}

	@GetMapping("/users/list")
	public String usersList(Model model,HttpSession session) {
		String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
    	if (!authority.equals("管理者")) {
    		return "redirect:/index";
    	}
		model.addAttribute("userList", usersService.getUsersList());
		return "user_list";
	}

	@PostMapping("/signup")
	public String processSignUp(@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam("jobId") Long jobId,
			@RequestParam("age") Integer age,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			RedirectAttributes redirectAttributes) {

		try {
			usersService.signUp(userName, password, jobId, age, phone, email);
			return "redirect:/users/list";
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/signup";
		}

	}

	@ModelAttribute("jobList")
	public Iterable<Jobs> getJobs() {
		return jobsService.getJobs(); // すべての有効な職種を取得
	}

	@PostMapping("/users/list")
	public String deleteUser(@RequestParam("id") Long id) {
		// IDを使用してユーザデータを削除する
		usersService.deleteUser(id);
		return "redirect:/users/list"; // ユーザ一覧ページにリダイレクト
	}

	@GetMapping("/users/edit/{userId}")
	public String editUser(@PathVariable Long userId, Model model,HttpSession session) {
		String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
    	if (!authority.equals("管理者")) {
    		return "redirect:/index";
    	}
		// ユーザー情報の取得
		Users user = usersRepository.findById(userId).orElse(null);
		if (user == null) {
			return "redirect:/users/list";
		}

		// モデルへの追加
		model.addAttribute("user", user);

		return "user_edit";
	}

	@PostMapping("/users/edit/{userId}")
	public String processEditUser(
			@PathVariable Long userId,
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam("jobId") Long jobId,
			@RequestParam("age") Integer age,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email) {
		// ユーザー情報の更新
		Users user = usersRepository.findById(userId).orElse(null);
		if (user != null) {
			user.setUserName(userName);
			user.setPassword(password);
			user.setJobId(jobId);
			user.setAge(age);
			user.setPhone(phone);
			user.setEmail(email);
			usersRepository.save(user);
			}

		return "redirect:/users/list";
	}

}
