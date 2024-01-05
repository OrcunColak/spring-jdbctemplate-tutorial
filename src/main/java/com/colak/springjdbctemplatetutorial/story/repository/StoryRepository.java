package com.colak.springjdbctemplatetutorial.story.repository;

import com.colak.springjdbctemplatetutorial.story.dto.Story;

import java.util.List;
import java.util.Optional;

public interface StoryRepository {

    Long save(Story story);

    int delete(Long id);

    List<Story> findAll();

    Optional<Story> findStoryById(Long storyId);

    void update(Story story);


}
