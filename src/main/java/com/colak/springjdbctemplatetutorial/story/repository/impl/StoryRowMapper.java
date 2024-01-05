package com.colak.springjdbctemplatetutorial.story.repository.impl;

import com.colak.springjdbctemplatetutorial.story.dto.Story;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class StoryRowMapper implements RowMapper<Story> {

    @Override
    public Story mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Story(
                resultSet.getLong("ID"),
                resultSet.getString("TITLE"),
                resultSet.getString("BODY"),
                resultSet.getObject("CREATED_AT", OffsetDateTime.class)
        );
    }
}
