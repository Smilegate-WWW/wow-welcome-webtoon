package com.www.platform.service;

import com.www.platform.domain.Response;
import com.www.platform.domain.comments.CommentsDeleteRequestDto;
import com.www.platform.domain.comments.CommentsMainResponseDto;
import com.www.platform.domain.comments.CommentsRepository;
import com.www.platform.domain.comments.CommentsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * //import javax.transaction.Transactional;
 *
 * @Transactional에서 org.springframework.transaction.annotation.Transactional
 * 가 아니라에서 javax.transaction.Transactional같은 readOnly속성은
 * Spring 트랜잭션에 따라 다릅니다.
 */

// TODO : response<T> code, msg 정리

@AllArgsConstructor
@Service
public class CommentsService {
    private CommentsRepository commentsRepository;

    // 예외 발생시 모든 DB작업 초기화 해주는 어노테이션 ( 완료시에만 커밋해줌 )
    @Transactional
    public Response<Integer> save(CommentsSaveRequestDto dto) {
        Response<Integer> result = new Response<Integer>();

        int entityIdx = commentsRepository.save(dto.toEntity()).getIdx();

        if(1 <= entityIdx)
        {
            result.setCode(0);
            result.setMsg("save complete");
            result.setData(entityIdx);
        }
        else
        {
            result.setCode(1);
            result.setMsg("save fail");
            result.setData(-1);
        }
        return result;
    }

    @Transactional
    public Response<Integer> delete(CommentsDeleteRequestDto dto)
    {
        Response<Integer> result = new Response<Integer>();
        if(commentsRepository.existsById(dto.getIdx())){
            commentsRepository.deleteById(dto.getIdx());
            result.setCode(0);
            result.setMsg("delete complete");
            result.setData(dto.getIdx());
        }
        else
        {
            result.setCode(1);
            result.setMsg("delete fail");
            result.setData(-1);
        }

        return result;
    }

    // TODO : ep idx에 따른 댓글 리스트 출력, 페이징 처리
    @Transactional(readOnly = true)
    public Response<List<CommentsMainResponseDto>> findAllDesc() {
        Response<List<CommentsMainResponseDto>> result = new Response<List<CommentsMainResponseDto>>();
        result.setData(commentsRepository.findAllDesc()
                .map(CommentsMainResponseDto::new)
                .collect(Collectors.toList()));
        result.setCode(0);
        result.setMsg("findAllDesc complete");

        return result;
    }

    // TODO : ep idx에 따른 베스트 댓글 리스트 출력
    @Transactional(readOnly = true)
    public Response<List<CommentsMainResponseDto>> findBestComments() {
        Response<List<CommentsMainResponseDto>> result = new Response<List<CommentsMainResponseDto>>();

        result.setData(commentsRepository.findBestComments()
                .map(CommentsMainResponseDto::new)
                .collect(Collectors.toList()));
        result.setCode(0);
        result.setMsg("complete : find Best Comments");

        return result;
    }
}
