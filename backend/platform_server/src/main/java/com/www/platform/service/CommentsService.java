package com.www.platform.service;

import com.www.platform.domain.comments.CommentsMainResponseDto;
import com.www.platform.domain.comments.CommentsRepositroy;
import com.www.platform.domain.comments.CommentsSaveRequestDto;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Service
public class CommentsService {
    private CommentsRepositroy commentsRepository;

    // 예외 발생시 모든 DB작업 초기화 해주는 어노테이션 ( 완료시에만 커밋해줌 )
    @Transactional
    public Integer save(CommentsSaveRequestDto dto) {
        return commentsRepository.save(dto.toEntity()).getIdx();
    }

    @Transactional(readOnly = true)
    public List<CommentsMainResponseDto> findAllDesc() {
        return commentsRepository.findAllDesc()
                .map(CommentsMainResponseDto::new)
                .collect(Collectors.toList());
    }
}
