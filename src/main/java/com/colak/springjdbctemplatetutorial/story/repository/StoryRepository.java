package com.colak.springjdbctemplatetutorial.story.repository;

import com.colak.springjdbctemplatetutorial.story.dto.Story;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface StoryRepository {

    Long insert(Story story);

    int delete(Long id);

    List<Story> findAll();

    Stream<Story> findAllStream();

    Optional<Story> findById(Long storyId);

    int update(Story story);


}
