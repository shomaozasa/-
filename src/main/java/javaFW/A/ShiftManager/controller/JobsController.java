package javaFW.A.ShiftManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import javaFW.A.ShiftManager.model.Jobs;
import javaFW.A.ShiftManager.repository.JobsRepository;
import javaFW.A.ShiftManager.service.JobsService;
import javaFW.A.ShiftManager.util.WageCalculator;

@Controller
public class JobsController {

    @Autowired
    private JobsService jobsService;
    
    @Autowired
    private JobsRepository jobsRepository;

    @GetMapping("/jobs/register")
    public String showRegisterForm(Model model,HttpSession session) {
        model.addAttribute("job", new Jobs());
        return "job_register";
    }

    @PostMapping("/jobs/register")
    public String registerJob(@ModelAttribute("job") Jobs job) {
    	int hourlyWage = job.getHourlyWage();
        int nightHourlyWage = WageCalculator.calculateNightHourlyWage(hourlyWage);
        job.setNightHourlyWage(nightHourlyWage);
        jobsService.saveJob(job);
        return "redirect:/jobs/list"; // 登録後に一覧ページにリダイレクトする
    }
    
    @GetMapping("/jobs/list")
    public String jobList(Model model,HttpSession session) {
    	String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
    	if (!authority.equals("管理者")) {
    		return "redirect:/index";
    	}
    	model.addAttribute("jobsList", jobsService.getJobsList());
    	return "job_list";
    }
    
    @PostMapping("/jobs/list")
    public String deleteJob(@RequestParam("id") Long id) {
        // IDを使用して役職データを削除する
    	jobsService.deleteJob(id);
        return "redirect:/jobs/list"; // 役職一覧ページにリダイレクト
    }
    
    @GetMapping("/jobs/edit/{jobId}")
	public String editJob(@PathVariable Long jobId, Model model, HttpSession session) {
    	String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
    	if (!authority.equals("管理者")) {
    		return "redirect:/index";
    	}
		// 役職情報の取得
		Jobs job = jobsRepository.findById(jobId).orElse(null);
		if (job == null) {
			return "redirect:/jobs/list";
		}

		// モデルへの追加
		model.addAttribute("job", job);

		return "job_edit";
	}

    @PostMapping("/jobs/edit/{jobId}")
    public String processEditJob(
        @PathVariable Long jobId,
        @ModelAttribute Jobs job) {
        // 役職情報の更新
    	int hourlyWage = job.getHourlyWage();
        int nightHourlyWage = WageCalculator.calculateNightHourlyWage(hourlyWage);
        job.setNightHourlyWage(nightHourlyWage);
        jobsRepository.save(job);

        return "redirect:/jobs/list";
    }



}
