package com.www.platform.service;

import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;

import com.www.core.common.Response;
import com.www.core.file.entity.Episode;
import com.www.core.file.repository.EpisodeRepository;
import com.www.core.platform.entity.Comments;
import com.www.core.platform.repository.CommentsRepository;
import com.www.platform.dto.CommentsDeleteRequestDto;
import com.www.platform.dto.CommentsMainResponseDto;
import com.www.platform.dto.CommentsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
public class  CommentsService {
    private CommentsRepository commentsRepository;
    private UsersRepository usersRepository;
    private EpisodeRepository episodeRepository;

    // 예외 발생시 모든 DB작업 초기화 해주는 어노테이션 ( 완료시에만 커밋해줌 )
    @Transactional
    public Response<Integer> save(int usersIdx, int epIdx, String content) {
        Response<Integer> result = new Response<Integer>();
        Optional<Users> user = usersRepository.findById(usersIdx);
        Optional<Episode> episode = episodeRepository.findById(epIdx);

        if(!episode.isPresent()){ // 에피소드가 존재하지 않을 때
            result.setCode(20);
            result.setMsg("fail : episode not exist");
        }
        else{   // 댓글 DB 저장
            Comments comments = Comments.builder()
                    .users(user.get())
                    .ep(episode.get())
                    .content(content)
                    .build();
            int entityIdx = commentsRepository.save(comments).getIdx();

            result.setCode(0);
            result.setMsg("request complete : save comment");
        }
        return result;
    }

    @Transactional
    public Response<Integer> delete(int usersIdx, int epIdx, int commentsIdx) {
        Response<Integer> result = new Response<Integer>();
        Optional<Users> users = usersRepository.findById(usersIdx);
        Optional<Comments> comments = commentsRepository.findById(commentsIdx);

        if(comments.isPresent()){ // 유저가 해당 댓글의 주인이 아닐 때
            if(usersIdx != comments.get().getUsers().getIdx()){
                result.setCode(22);
                result.setMsg("fail : not comment owner");
            }
            else{   // 댓글 삭제
                commentsRepository.deleteById(commentsIdx);
                result.setCode(0);
                result.setMsg("request complete : delete comment");
            }
        }
        else    // 댓글이 이미 없을 때
        {
            result.setCode(21);
            result.setMsg("fail : comment not exist");
        }

        return result;
    }

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

    @Transactional(readOnly = true)
    public Response<Page<CommentsMainResponseDto>> findCommentsByPageRequest(int epIdx, int page) {
        Response<Page<CommentsMainResponseDto>> result = new Response<Page<CommentsMainResponseDto>>();

        if(!episodeRepository.existsById(epIdx)) {    // 에피소드가 존재하지 않을 때
            result.setCode(20);
            result.setMsg("fail : episode not exist");
        }
        else{
            // repository에서 Page<entity>로 받은 내용을 Page<dto>로 변환하는 법 아래 참고
            // https://stackoverflow.com/questions/27557240/getting-a-page-of-dto-objects-from-spring-data-repository

            Pageable pageable = PageRequest.of(page <= 0 ? 0 : page - 1, 15, Sort.Direction.DESC, "idx");
            Page<Comments> commentsPage = commentsRepository.findAllByEpIdx(pageable, epIdx);
            int totalElements = (int) commentsPage.getTotalElements();
            Page<CommentsMainResponseDto> commentsMainResponseDtoPage
                    = new PageImpl<CommentsMainResponseDto>(commentsPage
                    .stream()
                    .map(comments -> new CommentsMainResponseDto(comments))
                    .collect(Collectors.toList()), pageable, totalElements);
            result.setData(commentsMainResponseDtoPage);

            if(page > commentsPage.getTotalPages()){
                result.setCode(23);
                result.setMsg("fail : entered page exceeds the total pages");
            }
            else{
                result.setCode(0);
                result.setMsg("request complete : find Comments By Page Request");
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Response<List<CommentsMainResponseDto>> findBestComments(int epIdx) {
        Response<List<CommentsMainResponseDto>> result = new Response<List<CommentsMainResponseDto>>();

        if(!episodeRepository.existsById(epIdx)) {  // 에피소드가 존재하지 않을 때
            result.setCode(20);
            result.setMsg("fail : episode not exist");
        }
        else{
            result.setData(commentsRepository.findBestCommentsByEpIdx(epIdx)
                    .map(CommentsMainResponseDto::new)
                    .collect(Collectors.toList()));
            result.setCode(0);
            result.setMsg("requset complete : find Best Comments");
        }

        return result;
    }
}
