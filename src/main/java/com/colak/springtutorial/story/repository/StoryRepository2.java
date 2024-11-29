package com.colak.springtutorial.story.repository;

import com.colak.springtutorial.story.dto.Story;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

// This example shows how to use NamedParameterJdbcTemplate
@Slf4j
@RequiredArgsConstructor
public class StoryRepository2 {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final StoryRowMapper storyRowMapper = new StoryRowMapper();

    public Long insert(Story story) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO stories(title, body, created_at) VALUES(:title, :body, :createdAt)";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", story.title())
                .addValue("body", story.body())
                .addValue("createdAt", story.createdAt());

        int updated = namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        Assert.state(updated == 1, "Story insert failed");

        // Retrieve the generated key
        Map<String, Object> generatedKeys = keyHolder.getKeys();

        // If multiple keys are returned, we can retrieve the ID from the map
        return (Long) generatedKeys.get("id");
    }

    public int delete(Long id) {
        String sql = "DELETE FROM stories WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    public List<Story> findAll() {
        String sql = "SELECT id, title, body, created_at FROM stories";
        return namedParameterJdbcTemplate.query(sql, storyRowMapper);
    }

    public Stream<Story> findAllStream() {
        String sql = "SELECT id, title, body, created_at FROM stories";
        return namedParameterJdbcTemplate.queryForStream(sql, new MapSqlParameterSource(), storyRowMapper);
    }

    public Optional<Story> findById(Long storyId) {
        String sql = "SELECT id, title, body, created_at FROM stories WHERE id = :storyId";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("storyId", storyId);

        Story story = null;
        try {
            story = namedParameterJdbcTemplate.queryForObject(sql, parameters, storyRowMapper);
        } catch (EmptyResultDataAccessException exception) {
            log.error("Story not found. Id parameter: {}", storyId, exception);
        }
        return Optional.ofNullable(story);
    }

    public Optional<Story> findById2(Long storyId) {
        String sql = "SELECT * FROM stories WHERE id = :storyId";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("storyId", storyId);

        Story story = null;
        try {
            story = namedParameterJdbcTemplate.queryForObject(sql, parameters, storyRowMapper);
        } catch (EmptyResultDataAccessException exception) {
            log.error("Story not found. Id parameter: {}", storyId, exception);
        }
        return Optional.ofNullable(story);
    }

    public int update(Story story) {
        String sql = "UPDATE stories SET title = :title, body = :body, created_at = :createdAt WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", story.title())
                .addValue("body", story.body())
                .addValue("createdAt", story.createdAt())
                .addValue("id", story.id());

        return namedParameterJdbcTemplate.update(sql, parameters);
    }

}
