package com.mca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mca.infrastructure.model.VideoGame;

@Repository
public interface VideoGameRepository extends JpaRepository<VideoGame, Long>{

}
