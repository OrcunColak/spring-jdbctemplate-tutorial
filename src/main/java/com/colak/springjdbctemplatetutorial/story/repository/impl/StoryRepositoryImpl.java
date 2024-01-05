package com.colak.springjdbctemplatetutorial.story.repository.impl;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import com.colak.springjdbctemplatetutorial.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StoryRepositoryImpl implements StoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StoryRowMapper storyRowMapper = new StoryRowMapper();

    @Override
    public Long save(Story story) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            String sql = "insert into stories(title, body, created_at) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, story.title());
            preparedStatement.setString(2, story.body());
            preparedStatement.setTimestamp(3, Timestamp.from(story.createdAt()));
            return preparedStatement;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from stories where id = ?";
        int count = jdbcTemplate.update(sql, id);
        if (count == 0) {
            throw new RuntimeException("Story not found");
        }
        return count;
    }

    @Override
    public List<Story> findAll() {
        String sql = "SELECT id, title, body, created_at FROM stories";
        return jdbcTemplate.query(sql, storyRowMapper);
    }

    @Override
    public Optional<Story> findStoryById(Long storyId) {
        String sql = "SELECT id, title, body, created_at  FROM stories WHERE ID = ?";
        try {
            Story story = jdbcTemplate.queryForObject(sql,
                    storyRowMapper,
                    storyId);
            return Optional.of(story);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Story story) {
        String sql = "update stories set title = ?, body = ? where id = ?";
        int count = jdbcTemplate.update(sql, story.title(), story.body(), story.id());
        if (count == 0) {
            throw new RuntimeException("Bookmark not found");
        }
    }
}
