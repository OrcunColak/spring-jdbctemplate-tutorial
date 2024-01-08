package com.colak.springjdbctemplatetutorial.story.service;

import com.colak.springjdbctemplatetutorial.story.dto.Story;

import java.util.List;
import java.util.Optional;

public interface StoryService {

    Optional<Story> findById(Long storyId);

    List<Story> findAllStream();
}
