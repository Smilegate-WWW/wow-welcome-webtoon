package com.www.file.service;

import java.io.File;
import java.io.FileOutputStream;
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

import lombok.AllArgsConstructor;

//@AllArgsConstructor
@Service
public class WebtoonService {
	
	private WebtoonRepository webtoonRepository;
	private EpisodeRepository episodeRepository;
	
	//�� ���� �����ϴ� �ִ� ������ ��ȣ �� 
	private static final int BLOCK_PAGE_NUM_COUNT = 5;
	//�� ������ �ִ� ���� ���� 
	private static final int PAGE_WEBTOON_COUNT = 20;
	
	@Value("${custom.path.upload-images}")
	private String filePath;
	
	
	public WebtoonService(WebtoonRepository webtoonRepository, EpisodeRepository episodeRepository) {
		this.webtoonRepository = webtoonRepository;
		this.episodeRepository = episodeRepository;
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
		//�ʼ� �Է� ���� üũ
		checkCondition(file,webtoonDto,res);
		if(res.getCode()!=0)
			return res;
		else {
			//��� �ʼ� �Է� ���� ����
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			webtoonDto.setThumbnail(fileName);
			//file ����ҿ� ����
			
			File destinationFile = new File(filePath+"/thumbnail/"+fileName);
			//if(!destinationFile.exists()) System.out.println("����!!!!!!!!!!!!!");
			destinationFile.getParentFile().mkdir();
			file.transferTo(destinationFile);
			
			//webtoon���� ����
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
		    
		//��ȿ ���� �� ������ ��û
		if(pageNum>0 && pageNum<=totalpages) {
			for(Webtoon webtoon : webtoons) {
				WebtoonListDto webtoonDto = WebtoonListDto.builder()
						.title(webtoon.getTitle())
						.created_date(webtoon.getCreated_date())
						.build();
				
				List<Episode> episodeList = webtoon.getEpisodes();
				
				//���� �ֽ� ȭ ������Ʈ ��¥
				if(!episodeList.isEmpty()) {
		        	Episode e = episodeList.get(episodeList.size()-1);
		        	LocalDateTime lastUpdate = e.getUpdated_date();
		        	webtoonDto.setLast_updated(lastUpdate);
		        }
		        
		        //��ϵ� ȸ���� ������
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
		
		//�� �Խñ� ����
		Double webtoonsTotalCount = Double.valueOf(this.getWebtoonCount());
		
		//�� �Խñ� �������� ����� ������ ������ ��ȣ ��� (�ø�)
		Integer totalLastPageNum = (int)(Math.ceil((webtoonsTotalCount/PAGE_WEBTOON_COUNT)));
		
		//���� �������� �������� ���� ������ ������ ��ȣ ���
		Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
					? curPageNum + BLOCK_PAGE_NUM_COUNT
					: totalLastPageNum;
		//������ ���� ��ȣ ����
		curPageNum = (curPageNum <= 3) ? 1 : curPageNum-2;
		
		//������ ��ȣ �Ҵ�
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
		
		//�ʼ� ���� üũ
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
	        
	        //����� �̹����� ����Ǿ��� ��� 
	        if(!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				System.out.println(fileName);
				webtoonDto.setThumbnail(fileName);
				//file ����ҿ� ����
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
        
        //�������� �ʴ� ����
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
