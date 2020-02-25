package com.www.core.platform.repository;

import com.www.core.platform.entity.Comments;
import com.www.core.platform.entity.StarRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface StarRatingRepository extends JpaRepository<StarRating, Integer> {
    boolean existsByEpIdxAndUsersIdx(@Param("ep_idx") int epIdx, @Param("users_idx") int usersIdx);

    StarRating findByEpIdxAndUsersIdx(@Param("ep_idx") int epIdx, @Param("users_idx") int usersIdx);


    @Query(value = "SELECT AVG(s.rating) " +
            "FROM star_rating s " +
            "GROUP BY s.ep_idx " +
            "HAVING s.ep_idx = :ep_idx", nativeQuery = true)
    float getRatingAvgByEpIdx(@Param("ep_idx") int epIdx);
}
