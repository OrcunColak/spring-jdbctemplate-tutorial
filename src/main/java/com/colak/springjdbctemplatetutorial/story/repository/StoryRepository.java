package com.colak.springjdbctemplatetutorial.story.repository;

import com.colak.springjdbctemplatetutorial.story.dto.Story;

import java.util.List;
import java.util.Optional;

public interface StoryRepository {

    Long insert(Story story);

    int delete(Long id);

    List<Story> findAll();

    Optional<Story> findById(Long storyId);

    int update(Story story);


}
