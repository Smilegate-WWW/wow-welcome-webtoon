package com.www.platform.service;

import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;

import com.www.core.common.Response;
import com.www.core.file.entity.Episode;
import com.www.core.file.repository.EpisodeRepository;
import com.www.core.platform.entity.Comments;
import com.www.core.platform.entity.CommentsDislike;
import com.www.core.platform.repository.CommentsDislikeRepository;
import com.www.core.platform.repository.CommentsLikeRepository;
import com.www.core.platform.repository.CommentsRepository;
import com.www.platform.dto.CommentsDto;
import com.www.platform.dto.CommentsResponseDto;
import com.www.platform.dto.MyPageCommentsDto;
import com.www.platform.dto.MyPageCommentsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * //import javax.transaction.Transactional;
 *
 * @Transactional에서 org.springframework.transaction.annotation.Transactional
 * 가 아니라에서 javax.transaction.Transactional같은 readOnly속성은
 * Spring 트랜잭션에 따라 다릅니다.
 */


@AllArgsConstructor
@Service
public class  CommentsService {
    private CommentsRepository commentsRepository;
    private CommentsLikeRepository commentsLikeRepository;
    private CommentsDislikeRepository commentsDislikeRepository;
    private UsersRepository usersRepository;
    private EpisodeRepository episodeRepository;

    // 예외 발생시 모든 DB작업 초기화 해주는 어노테이션 ( 완료시에만 커밋해줌 )
    @Transactional
    public Response<Integer> insertComments(int userIdx, int epIdx, String content) {
        Response<Integer> result = new Response<Integer>();
        Optional<Users> user = usersRepository.findById(userIdx);
        Optional<Episode> episode = episodeRepository.findById(epIdx);

        if(!episode.isPresent()){ // 에피소드가 존재하지 않을 때
            result.setCode(20);
            result.setMsg("fail : episode don't exists");
        }
        else{   // 댓글 DB 저장
            Comments comments = Comments.builder()
                    .users(user.get())
                    .ep(episode.get())
                    .content(content)
                    .build();
            int entityIdx = commentsRepository.save(comments).getIdx();

            result.setCode(0);
            result.setMsg("request complete : insert comment");
        }
        return result;
    }

    @Transactional
    public Response<Integer> deleteComments(int userIdx, int commentsIdx) {
        Response<Integer> result = new Response<Integer>();
        Optional<Users> users = usersRepository.findById(userIdx);
        Optional<Comments> comments = commentsRepository.findById(commentsIdx);

        if(comments.isPresent()){ // 유저가 해당 댓글의 주인이 아닐 때
            if(userIdx != comments.get().getUsers().getIdx()){
                result.setCode(22);
                result.setMsg("fail : user isn't comment owner");
            }
            else{   // 댓글 삭제
                commentsLikeRepository.deleteAllByCommentsIdx(commentsIdx);
                commentsDislikeRepository.deleteAllByCommentsIdx(commentsIdx);
                commentsRepository.deleteById(commentsIdx);
                result.setCode(0);
                result.setMsg("request complete : delete comment");
            }
        }
        else    // 댓글이 이미 없을 때
        {
            result.setCode(21);
            result.setMsg("fail : comment don't exists");
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Response<List<CommentsDto>> findAllDesc() {
        Response<List<CommentsDto>> result = new Response<List<CommentsDto>>();
        result.setData(commentsRepository.findAllDesc()
                .map(CommentsDto::new)
                .collect(Collectors.toList()));
        result.setCode(0);
        result.setMsg("findAllDesc complete");

        return result;
    }

    @Transactional(readOnly = true)
    public Response<CommentsResponseDto> getCommentsByPageRequest(int epIdx, int page) {
        Response<CommentsResponseDto> result = new Response<CommentsResponseDto>();

        if(!episodeRepository.existsById(epIdx)) {    // 에피소드가 존재하지 않을 때
            result.setCode(20);
            result.setMsg("fail : episode don't exists");
        }
        else{
            // repository에서 Page<entity>로 받은 내용을 Page<dto>로 변환하는 법 아래 참고
            // https://stackoverflow.com/questions/27557240/getting-a-page-of-dto-objects-from-spring-data-repository

            if(page < 1){
                result.setCode(23);
                result.setMsg("fail : page is not in valid range");
                return result;
            }

            Pageable pageable = PageRequest.of(page - 1, 15, Sort.Direction.DESC, "idx");
            Page<Comments> commentsPage = commentsRepository.findAllByEpIdx(pageable, epIdx);

            if(page > commentsPage.getTotalPages() & page != 1){
                result.setCode(23);
                result.setMsg("fail : page is not in valid range");
            }
            else{
                result.setCode(0);
                result.setMsg("request complete : get comments by page request");
                CommentsResponseDto commentsResponseDto
                        = CommentsResponseDto.builder()
                        .comments(commentsPage
                                .stream()
                                .map(CommentsDto::new)
                                .collect(Collectors.toList()))
                        .total_pages(commentsPage.getTotalPages())
                        .build();
                result.setData(commentsResponseDto);
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Response<List<CommentsDto>> getBestComments(int epIdx) {
        Response<List<CommentsDto>> result = new Response<List<CommentsDto>>();

        if(!episodeRepository.existsById(epIdx)) {  // 에피소드가 존재하지 않을 때
            result.setCode(20);
            result.setMsg("fail : episode don't exists");
        }
        else{
            result.setData(commentsRepository.findBestCommentsByEpIdx(epIdx)
                    .map(CommentsDto::new)
                    .collect(Collectors.toList()));
            result.setCode(0);
            result.setMsg("requset complete : get best comments");
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Response<MyPageCommentsResponseDto> getMyPageComments(int userIdx, int page){
        Response<MyPageCommentsResponseDto> result = new Response<MyPageCommentsResponseDto>();

        if(page < 1){
            result.setCode(23);
            result.setMsg("fail : page is not in valid range");
            return result;
        }

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "idx");
        Page<Comments> commentsPage = commentsRepository.findAllByUsersIdx(pageable, userIdx);
        int totalElements = commentsPage.getContent().size();

        if(page > commentsPage.getTotalPages() && page != 1){
            result.setCode(23);
            result.setMsg("fail : page is not in valid range");
        }
        else{
            List<MyPageCommentsDto> myPageCommentsDtosList = new ArrayList<>(totalElements);
            for(int i = 0 ; i < totalElements; i++){
                myPageCommentsDtosList.add(new MyPageCommentsDto(commentsPage.getContent().get(i)));
            }

            result.setCode(0);
            result.setMsg("request complete : get my page comments");
            result.setData(MyPageCommentsResponseDto.builder()
                    .comments(myPageCommentsDtosList)
                    .total_pages(commentsPage.getTotalPages())
                    .build());
        }
        return result;
    }
}
