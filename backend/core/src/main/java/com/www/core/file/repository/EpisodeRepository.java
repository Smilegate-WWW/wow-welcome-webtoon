package com.www.core.file.repository;

import com.www.core.file.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>{
	
}
