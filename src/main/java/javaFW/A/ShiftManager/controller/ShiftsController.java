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
import javaFW.A.ShiftManager.model.Shifts;
import javaFW.A.ShiftManager.repository.ShiftsRepository;
import javaFW.A.ShiftManager.service.ShiftsService;

@Controller
public class ShiftsController {

    @Autowired
    private ShiftsService shiftsService;
    @Autowired
    private ShiftsRepository shiftsRepository;

    
    @GetMapping("/shifts/list")
    public String shiftsList(Model model,HttpSession session) {
    	String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
    	model.addAttribute("shiftsList", shiftsService.getshiftsList());
    	return "shift_list";
    }
    
    @GetMapping("/shifts/register")
    public String showShiftForm(Model model, HttpSession session) {
    	String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
        // セッションからログイン済みユーザー情報取得
        
        // ユーザーID取得
        Long userId = (Long) session.getAttribute("userId");
        
        // モデルに追加
        model.addAttribute("userId", userId);
        
        // 新しいShiftsオブジェクトを作成してモデルに追加
        model.addAttribute("shift", new Shifts());
        
        return "shift_register";
    }
    
    @PostMapping("/shifts/register")
    public String registerShift(@ModelAttribute("shift") Shifts shift) {
        // フォームから送信されたデータを処理する
        shiftsService.saveShift(shift);
        return "redirect:/shifts/list"; // シフト一覧ページにリダイレクト
    }
    
    @PostMapping("/shifts/list")
    public String deleteShift(@RequestParam("id") Long id) {
        // IDを使用してシフトデータを削除する
    	shiftsService.deleteShift(id);
        return "redirect:/shifts/list"; // シフト一覧ページにリダイレクト
    }
    
    @GetMapping("/shifts/edit/{shiftId}")
	public String editShift(@PathVariable Long shiftId, Model model, HttpSession session) {
    	String authority = (String) session.getAttribute("authority");
    	if (authority == null) {
    		return "redirect:/";
    	}
    	if (!authority.equals("管理者")) {
    		return "redirect:/index";
    	}
		// シフト情報の取得
		Shifts shift = shiftsRepository.findById(shiftId).orElse(null);
		if (shift == null) {
			return "redirect:/shifts/list";
		}

		// モデルへの追加
		model.addAttribute("shift", shift);

		return "shift_edit";
	}

    @PostMapping("/shifts/edit/{shiftId}")
    public String processEditShift(
        @PathVariable Long shiftId,
        @ModelAttribute Shifts updatedShift) {
        // 既存のシフト情報を取得
        Shifts existingShift = shiftsRepository.findById(shiftId).orElse(null);
        if (existingShift == null) {
            // シフトが見つからない場合はリダイレクト
            return "redirect:/shifts/list";
        }

        // 新しい時間やメモで既存のシフト情報を更新
        existingShift.setStartTime(updatedShift.getStartTime());
        existingShift.setEndTime(updatedShift.getEndTime());
        existingShift.setMemo(updatedShift.getMemo());

        // 更新を保存
        shiftsRepository.save(existingShift);

        return "redirect:/shifts/list";
    }

    @GetMapping("/shifts/date/{date}")
    public String showShiftsByDate(@PathVariable String date, Model model) {
        // 日付を使用してシフトを取得し、モデルに追加するなどの処理を行う
        return "shift_detail"; // 表示するテンプレート名を返す
    }
    
}
