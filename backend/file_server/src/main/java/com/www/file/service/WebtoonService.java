package com.www.file.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.www.core.file.entity.*;
import com.www.core.file.repository.*;

import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.core.common.Response;
import com.www.file.dto.WebtoonDto;
import com.www.file.dto.WebtoonListDto;
import com.www.file.dto.WebtoonPage;


//@AllArgsConstructor
@Service
public class WebtoonService {
	
	private WebtoonRepository webtoonRepository;
	
	//한 블럭 내 최대 페이지 번호 수
	private static final int BLOCK_PAGE_NUM_COUNT = 5;
	//한 페이지 내 최대 웹툰 출력 갯수
	private static final int PAGE_WEBTOON_COUNT = 20;
	
	@Value("${custom.path.upload-images}")
	private String filePath;
	
	
	public WebtoonService(WebtoonRepository webtoonRepository) {
		this.webtoonRepository = webtoonRepository;
	}
	
	public void checkCondition(MultipartFile file,WebtoonDto webtoonDto,Response<WebtoonDto> res) {
		
		if(webtoonDto.getTitle()==null) {
			res.setCode(10);
			res.setMsg("insert fail: need to register title");
			return;
		}
		if(webtoonDto.getPlot()==null) {
			res.setCode(11);
			res.setMsg("insert fail: need to register plot");
			return;
		}
		if(webtoonDto.getSummary()==null) {
			res.setCode(12);
			res.setMsg("insert fail: need to register summary");
			return;
		}
		if(file.isEmpty()) {
			res.setCode(13);
			res.setMsg("insert fail: need to register thumbnail");
			return;
		}
		
		res.setCode(0);
		res.setMsg("insert complete");
	}
	
	@Transactional
	public Response<WebtoonDto> createWebtoon(MultipartFile file, WebtoonDto webtoonDto) throws IOException {
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		//필수 조건 체크
		checkCondition(file,webtoonDto,res);
		if(res.getCode()!=0)
			return res;
		else {
			//필수 입력 조건 만족시
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			webtoonDto.setThumbnail(fileName);
			
			//file 외부 폴더로 이동
			File destinationFile = new File(filePath+"/thumbnail/"+fileName);
			destinationFile.getParentFile().mkdir();
			file.transferTo(destinationFile);
	
			webtoonRepository.save(webtoonDto.toEntity());
			res.setData(webtoonDto);
			return res;
		}
		
	}
	
	@Transactional
	public List<WebtoonListDto> getWebtoonList(Integer pageNum, Response<WebtoonPage> res) {
		Page<Webtoon> page = webtoonRepository.findAll(PageRequest.of(pageNum-1, PAGE_WEBTOON_COUNT));
		
		List<Webtoon> webtoons = page.getContent();
		List<WebtoonListDto> webtoonListDto = new ArrayList<>();
		int totalpages = page.getTotalPages();
		    
		//요청한 페이지 번호가 유효한 범위인지 체크
		if(pageNum>0 && pageNum<=totalpages) {
			for(Webtoon webtoon : webtoons) {
				WebtoonListDto webtoonDto = WebtoonListDto.builder()
						.title(webtoon.getTitle())
						.created_date(webtoon.getCreated_date())
						.build();
				
				List<Episode> episodeList = webtoon.getEpisodes();
				
				//웹툰 업데이트일 필드 
				//회차가 1개 이상 등록된 경우 가장 최신 회차의 업데이트 시간으로 설정
				if(!episodeList.isEmpty()) {
		        	Episode e = episodeList.get(episodeList.size()-1);
		        	LocalDateTime lastUpdate = e.getUpdated_date();
		        	webtoonDto.setLast_updated(lastUpdate);
		        }
		        
		        //회차가 등록되어있지 않은 경우 웹툰 생성시간으로 설정
		        else {
		        	webtoonDto.setLast_updated(webtoon.getCreated_date());
		        }
				webtoonListDto.add(webtoonDto);
			}
			res.setCode(0);
		    res.setMsg("show complete");
		}
		
		else {
	    	res.setCode(1);
	    	res.setMsg("fail : pageNum is not in valid range");
		}
		
	    return webtoonListDto;
        
	}
	public Long getWebtoonCount() {
		return webtoonRepository.count();
	}
	
	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
		
		//총 에피소드 갯수
		Double webtoonsTotalCount = Double.valueOf(this.getWebtoonCount());
		
		//총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
		Integer totalLastPageNum = (int)(Math.ceil((webtoonsTotalCount/PAGE_WEBTOON_COUNT)));
		
		//현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
		Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
					? curPageNum + BLOCK_PAGE_NUM_COUNT
					: totalLastPageNum;
		//페이지 시작 번호 조정
		curPageNum = (curPageNum <= 3) ? 1 : curPageNum-2;
		
		//페이지 번호 할당
		for(int val = curPageNum, idx=0; val <= blockLastPageNum; val++, idx++) {
			pageList[idx] = val;
		}
		
		return pageList;
	}
	
	@Transactional
	public Response<WebtoonDto> editWebtoon(int idx, MultipartFile file, WebtoonDto webtoonDto) throws IOException {
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		
		if(!webtoonRepository.existsById(idx)) {
      		res.setCode(1);
      		res.setMsg("fail: Webtoon do not exists");
      		return res;
        }
		
		checkCondition(file, webtoonDto, res);
		
		if(res.getCode()!=0)
			return res;
		else {
			Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
	        Webtoon webtoon = WebtoonEntityWrapper.get();
	        
	        webtoon.setEnd_flag(webtoonDto.getEnd_flag());
	        webtoon.setGenre1(webtoonDto.getGenre1());
	        webtoon.setGenre2(webtoonDto.getGenre2());
	        webtoon.setPlot(webtoonDto.getPlot());
	        webtoon.setSummary(webtoonDto.getSummary());
	        webtoon.setTitle(webtoonDto.getTitle());
	        webtoon.setToon_type(webtoonDto.getToon_type());
	        
	        if(!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				System.out.println(fileName);
				webtoonDto.setThumbnail(fileName);
				//file 외부 폴더로 이동
				File destinationFile = new File("D:/image/"+fileName);
				destinationFile.getParentFile().mkdir();
				file.transferTo(destinationFile);
			}
	        
	        webtoonRepository.save(webtoon);
	        res.setData(webtoonDto);
	        return res;
		}
		
	}
	
	public int deleteWebtoon(int idx) {
        
        //해당 웹툰 idx가 유효한지 체크
        if(!webtoonRepository.existsById(idx)) {
        	return 1;
        }
        else {
        	Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
            Webtoon webtoon = WebtoonEntityWrapper.get();
        	webtoonRepository.delete(webtoon);
        	return 0;
        }
        
	}

}
