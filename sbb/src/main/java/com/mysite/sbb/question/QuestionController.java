package com.mysite.sbb.question;

import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";	
	}
	
    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }    
    @GetMapping("/create")
    public String questionCreate() {
    	return "question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
    		return "question_forms";
    	}
    	this.questionService.create(questionForm.getSubject(), questionForm.getContent());
    	return "redirect:/question/list";// 질문 저장후 질문 목록으로 이동
    }
}