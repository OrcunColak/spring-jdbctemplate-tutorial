package com.colak.springjdbctemplatetutorial.story.repository;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import com.colak.springjdbctemplatetutorial.story.repository.impl.StoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class StoryRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StoryRepositoryImpl repository;


    @BeforeEach
    void setUp() {
        repository = new StoryRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testCreate() {
        Story story = new Story(null, "My Title", "My Body", Instant.now());
        Long id = repository.save(story);
        assertThat(id).isNotNull();

        Optional<Story> optional = repository.findStoryById(id);
        assertThat(optional).isPresent();
    }

    @Test
    void testDelete() {
        Story story = new Story(null, "My Title", "My Body", Instant.now());
        Long id = repository.save(story);

        int count = repository.delete(id);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testFindStoryById() {
        Optional<Story> optional = repository.findStoryById(1L);
        assertThat(optional).isPresent();
    }

    @Test
    void testFindStoryById_NotFound() {
        Optional<Story> optional = repository.findStoryById(10L);
        assertThat(optional).isEmpty();
    }

    @Test
    void testFindAll() {
        List<Story> storyList = repository.findAll();
        assertThat(storyList)
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void testUpdate() {
        Story story = new Story(null, "My Title", "My Body", Instant.now());
        Long id = repository.save(story);

        Story updatedStory = new Story(id, "My Updated Title", "My Updated Body", story.createdAt());

        repository.update(updatedStory);

        Story receivedStory = repository.findStoryById(id).orElseThrow();
        assertThat(receivedStory.id()).isEqualTo(id);
        assertThat(receivedStory.title()).isEqualTo("My Updated Title");
        assertThat(receivedStory.body()).isEqualTo("My Updated Body");
    }
}