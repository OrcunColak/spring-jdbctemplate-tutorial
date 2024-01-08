package com.colak.springjdbctemplatetutorial.story.service.impl;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import com.colak.springjdbctemplatetutorial.story.repository.StoryRepository;
import com.colak.springjdbctemplatetutorial.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Story> findById(Long storyId) {
        return storyRepository.findById(storyId);
    }

    @Transactional
    @Override
    public List<Story> findAllStream() {
        return storyRepository.findAllStream().toList();
    }
}
