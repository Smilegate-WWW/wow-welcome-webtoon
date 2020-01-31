package com.www.platform.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface CommentsRepositroy extends JpaRepository<Comments, Integer> {
    @Query("SELECT c " +
            "FROM Comments c " +
            "ORDER BY c.idx DESC")
    Stream<Comments> findAllDesc();
}
