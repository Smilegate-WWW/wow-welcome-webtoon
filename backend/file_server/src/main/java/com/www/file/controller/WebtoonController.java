package com.www.file.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.ws.Response;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.www.file.domain.repository.WebtoonRepository;
import com.www.file.dto.WebtoonDto;
import com.www.file.payload.FileUploadResponse;
import com.www.file.service.WebtoonService;

@RestController
public class WebtoonController {
	@Autowired
	private WebtoonRepository webtoonRepository;
	//@Autowired
	//private FileUploadService service;
	@Autowired
	private WebtoonService webtoonService;
	
	/*
	@PostMapping("/fileTest")
	public void postFile(@RequestParam("file") MultipartFile file) throws IOException {
		webtoonService.postImage(file);
	}
	*/
	
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	
	@PostMapping("/webtoon")
	public void registWebtoon(@RequestPart("file") MultipartFile file, @RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException {
		webtoonService.createWebtoon(file, webtoonDto);
	}

}
