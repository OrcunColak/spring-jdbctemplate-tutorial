package com.colak.springjdbctemplatetutorial.story.repository;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import com.colak.springjdbctemplatetutorial.story.repository.impl.StoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.OffsetDateTime;
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
    void testInsert() {
        Story story = new Story(null, "My Title", "My Body", OffsetDateTime.now());
        Long id = repository.insert(story);
        assertThat(id).isNotNull();

        Optional<Story> optional = repository.findById(id);
        assertThat(optional).isPresent();
    }

    @Test
    void testDelete() {
        Story story = new Story(null, "My Title", "My Body", OffsetDateTime.now());
        Long id = repository.insert(story);

        int count = repository.delete(id);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testById() {
        Optional<Story> optional = repository.findById(1L);
        assertThat(optional).isPresent();
    }

    @Test
    void testById_NotFound() {
        Optional<Story> optional = repository.findById(10L);
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
        Story story = new Story(null, "My Title", "My Body", OffsetDateTime.now());
        Long id = repository.insert(story);

        Story updatedStory = new Story(id, "My Updated Title", "My Updated Body", story.createdAt());
        assertThat(story.createdAt()).isEqualTo(updatedStory.createdAt());

        int updated = repository.update(updatedStory);
        assertThat(updated).isEqualTo(1);

        Story receivedStory = repository.findById(id).orElseThrow();

        Story truncatedReceivedStory = Story.truncateCreatedAt(updatedStory);

        assertThat(receivedStory).isEqualTo(truncatedReceivedStory);
    }
}
