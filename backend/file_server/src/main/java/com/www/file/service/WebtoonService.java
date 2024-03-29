package com.www.file.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.www.core.file.entity.*;
import com.www.core.file.repository.*;
import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;
import com.www.core.common.Response;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.WebtoonDto;
import com.www.file.dto.WebtoonListDto;
import com.www.file.dto.WebtoonPage;


@Service
public class WebtoonService {
	
	private WebtoonRepository webtoonRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	//한 블럭 내 최대 페이지 번호 수
	private static final int BLOCK_PAGE_NUM_COUNT = 5;
	//한 페이지 내 최대 웹툰 출력 갯수
	private static final int PAGE_WEBTOON_COUNT = 10;
	
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
	public Response<WebtoonDto> createWebtoon(MultipartFile file, WebtoonDto webtoonDto, int user_idx) throws IOException {
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		System.out.println(user_idx);
		
		Optional<Users> users = usersRepository.findById(user_idx);
		Users user = users.get();
		//필수 조건 체크
		checkCondition(file,webtoonDto,res);
		if(res.getCode()!=0)
			return res;
		else {
			
			//필수 입력 조건 만족시
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			webtoonDto.setThumbnail(fileName);
			
			//file 외부 폴더로 이동
			File destinationFile = new File(filePath+"/web_thumbnail/"+fileName);
			destinationFile.getParentFile().mkdir();
			file.transferTo(destinationFile);
			
			Webtoon webtoon = Webtoon.builder()
					.title(webtoonDto.getTitle())
					.toon_type(webtoonDto.getToon_type())
					.genre1(webtoonDto.getGenre1())
					.genre2(webtoonDto.getGenre2())
					.summary(webtoonDto.getSummary())
					.plot(webtoonDto.getPlot())
					.end_flag(webtoonDto.getEnd_flag())
					.users(user)
					.thumbnail(fileName)
					.build();
			webtoonRepository.save(webtoon);
					
			res.setData(webtoonDto);
			return res;
		}
		
	}
	
	@Transactional
	public WebtoonPage getWebtoonList(Integer pageNum, Response<WebtoonPage> res, int user_idx) {
		Pageable pageable = PageRequest.of(pageNum-1, PAGE_WEBTOON_COUNT);
		Page<Webtoon> page = webtoonRepository.findAllByUsersIdx(pageable, user_idx);	
	    List<WebtoonListDto> webtoonListDto = new ArrayList<>();
		WebtoonPage webtoonPage = null;
		int totalpages = page.getTotalPages();
		if(totalpages == 0 ) totalpages =1;
		
		//요청한 페이지 번호가 유효한 범위인지 체크
		if(pageNum>0 && pageNum<=totalpages) {
			List<Webtoon> webtoons = page.getContent();
			for(Webtoon webtoon : webtoons) {
				WebtoonListDto webtoonDto = WebtoonListDto.builder()
						.idx(webtoon.getIdx())
						.title(webtoon.getTitle())
						.thumbnail("http://localhost:8081/static/web_thumbnail/"+webtoon.getThumbnail())
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
			webtoonPage = new WebtoonPage(webtoonListDto, totalpages);
			res.setCode(0);
		    res.setMsg("show complete");
		}
		
		else {
	    	res.setCode(1);
	    	res.setMsg("fail : pageNum is not in valid range");
		}
		
	    return webtoonPage;
        
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
	        	UUID uuid = UUID.randomUUID();
				String fileName = uuid + "_" + file.getOriginalFilename();
				System.out.println(fileName);
				webtoonDto.setThumbnail(fileName);
				//file 외부 폴더로 이동
				File destinationFile = new File(filePath+"/web_thumbnail/"+fileName);
				destinationFile.getParentFile().mkdir();
				file.transferTo(destinationFile);
			}
	        
	        webtoonRepository.save(webtoon);
	        res.setData(webtoonDto);
	        return res;
		}
	}
	
	public Response<Integer> deleteWebtoon(int idx, int user_idx) {
		Response<Integer> res = new Response<Integer>();
        //해당 웹툰 idx가 유효한지 체크
		System.out.println("*****웹툰 삭제 idx 체크 : " + idx);
        if(!webtoonRepository.existsById(idx)) {
        	res.setCode(1);
			res.setMsg("delete fail: Webtoon do not exists");
        }
        else {
        	Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
            Webtoon webtoon = WebtoonEntityWrapper.get();
            if(webtoon.getUsers().getIdx() != user_idx) {
            	res.setMsg("delete fail: user do not have authority");
            	res.setCode(1);
            }
            else {
            	webtoonRepository.delete(webtoon);
            	res.setMsg("delete complete");
                res.setCode(0);
            }
        }
        return res;
        
	}
	
	public Response<WebtoonDto> getWebtoonInfo(int idx){
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		
		if(!webtoonRepository.existsById(idx)) {
			res.setCode(1);
			res.setMsg("Webtoon do not exist");
			return res;
		}
		Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
        Webtoon webtoon = WebtoonEntityWrapper.get();
        
		WebtoonDto webtoonDto = WebtoonDto.builder()
				.title(webtoon.getTitle())
				.toon_type(webtoon.getToon_type())
				.genre1(webtoon.getGenre1())
				.genre2(webtoon.getGenre2())
				.summary(webtoon.getSummary())
				.plot(webtoon.getPlot())
				.end_flag(webtoon.getEnd_flag())
				.thumbnail(webtoon.getThumbnail())
				.build();
		res.setData(webtoonDto);
		res.setCode(0);
		res.setMsg("get webtoon info");
		
		return res;
		
	}

}
