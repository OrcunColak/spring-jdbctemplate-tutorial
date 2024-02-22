package com.colak.springjdbctemplatetutorial.story.service;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import com.colak.springjdbctemplatetutorial.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoryService {

    private final StoryRepository storyRepository;

    @Transactional(readOnly = true)
    public Optional<Story> findById(Long storyId) {
        return storyRepository.findById(storyId);
    }

    @Transactional
    public List<Story> findAllStream() {
        return storyRepository.findAllStream().toList();
    }
}
