package com.www.file.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.www.core.common.Response;
import com.www.core.file.entity.Episode;
import com.www.core.file.entity.Webtoon;
import com.www.file.dto.WebtoonListDto;
import com.www.file.dto.WebtoonPage;
import com.www.file.service.MainService;

@RestController
public class MainController {
	@Autowired
	private MainService mainService;
	

}
