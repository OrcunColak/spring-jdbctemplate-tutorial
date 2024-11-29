package com.colak.springtutorial.story.repository;

import com.colak.springtutorial.story.dto.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class StoryRepository3Test {

    @Autowired
    private DataSource dataSource;

    private StoryRepository3 repository;


    @BeforeEach
    void setUp() {
        repository = new StoryRepository3(dataSource);
    }

    @Test
    void testInsert() {
        Story story = new Story(null, "My Title", "My Body", OffsetDateTime.now());
        Long id = repository.insert(story);
        assertThat(id).isNotNull();

    }

}
