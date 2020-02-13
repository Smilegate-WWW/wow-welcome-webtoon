package com.www.file.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.www.file.domain.entity.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>{
	
}
