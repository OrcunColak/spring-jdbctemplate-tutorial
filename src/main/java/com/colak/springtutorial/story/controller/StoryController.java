package com.colak.springtutorial.story.controller;

import com.colak.springtutorial.story.dto.Story;
import com.colak.springtutorial.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/story")
public class StoryController {

    private final StoryService storyService;

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Story> findStoryById(@PathVariable("id") Long id) {
        return storyService.findById(id)
                .map(story -> ResponseEntity.ok().body(story))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping(value = "getAllStream")
    public List<Story> getAllStream() {
        return storyService.findAllStream();
    }

}
