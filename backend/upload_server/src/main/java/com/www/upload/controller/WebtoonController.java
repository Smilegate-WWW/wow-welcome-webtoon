package com.www.upload.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.www.upload.domain.entity.WebtoonEntity;
import com.www.upload.domain.repository.WebtoonRepository;
import com.www.upload.service.RepositoryService;

@Controller
public class WebtoonController {
	@Autowired
	private RepositoryService repositoryService;
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title","새 작품 등록");
		model.addAttribute("webtoon", new WebtoonEntity());
		return "/form";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute WebtoonEntity webtoon, Model model) {
		WebtoonEntity entity = repositoryService.addWebtoon(webtoon);
		model.addAttribute("result",entity);
		return "/result";
	}
	
	
	

}
