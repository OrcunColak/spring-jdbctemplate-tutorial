package com.colak.springjdbctemplatetutorial.story.repository.impl;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoryRowMapper implements RowMapper<Story> {

    @Override
    public Story mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Story(
                resultSet.getLong("ID"),
                resultSet.getString("TITLE"),
                resultSet.getString("BODY"),
                resultSet.getTimestamp("CREATED_AT").toInstant()
        );
    }
}
