package com.colak.springjdbctemplatetutorial.story.service.impl;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import com.colak.springjdbctemplatetutorial.story.repository.StoryRepository;
import com.colak.springjdbctemplatetutorial.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    @Override
    public Optional<Story> findById(Long storyId) {
        return this.storyRepository.findById(storyId);
    }
}
