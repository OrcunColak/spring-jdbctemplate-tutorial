package com.colak.springtutorial.story.repository;

import com.colak.springtutorial.story.dto.Story;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository

@Slf4j
@RequiredArgsConstructor
public class StoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StoryRowMapper storyRowMapper = new StoryRowMapper();

    public Long insert(Story story) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updated = jdbcTemplate.update(connection -> {
            String sql = "INSERT INTO stories(title, body, created_at) VALUES(?,?,?)";
            // We can not use exception connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            // because two values are generated. id and createdAt fields
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, story.title());
            preparedStatement.setString(2, story.body());
            preparedStatement.setObject(3, story.createdAt());
            return preparedStatement;
        }, keyHolder);

        Assert.state(updated == 1L, "Story updated failed: " + story.id());

        return (Long) keyHolder.getKey();
    }

    public int delete(Long id) {
        String sql = "DELETE FROM stories WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Story> findAll() {
        String sql = "SELECT id, title, body, created_at FROM stories";
        return jdbcTemplate.query(sql, storyRowMapper);
    }

    public Stream<Story> findAllStream() {
        String sql = "SELECT id, title, body, created_at FROM stories";
        return jdbcTemplate.queryForStream(sql, storyRowMapper);
    }

    public Optional<Story> findById(Long storyId) {
        String sql = "SELECT id, title, body, created_at  FROM stories WHERE ID = ?";
        Story story = null;
        try {
            story = jdbcTemplate.queryForObject(sql,
                    storyRowMapper,
                    storyId);
        } catch (EmptyResultDataAccessException exception) {
            log.error("Story not found. Id parameter: {}", storyId, exception);
        }
        return Optional.ofNullable(story);
    }

    public Optional<Story> findById2(Long storyId) {
        String sql = "SELECT *  FROM stories WHERE ID = ?";
        Story story = null;
        try {
            story = jdbcTemplate.queryForObject(sql,
                    storyRowMapper,
                    storyId);
        } catch (EmptyResultDataAccessException exception) {
            log.error("Story not found. Id parameter: {}", storyId, exception);
        }
        return Optional.ofNullable(story);
    }

    public int update(Story story) {
        String sql = "UPDATE stories SET title = ?, body = ? , created_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, story.title(), story.body(), story.createdAt(), story.id());
    }
}
