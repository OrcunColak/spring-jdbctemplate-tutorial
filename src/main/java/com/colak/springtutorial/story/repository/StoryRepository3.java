package com.colak.springtutorial.story.repository;

import com.colak.springtutorial.story.dto.Story;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Slf4j
public class StoryRepository3 {

    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public StoryRepository3(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("stories")
                .usingGeneratedKeyColumns("id");  // Specify the generated key column(s)
    }

    public Long insert(Story story) {
        // Prepare parameters to insert into the table
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", story.title())
                .addValue("body", story.body())
                .addValue("created_at", story.createdAt());  // Optional if created_at is auto-generated

        // Execute the insert and retrieve the generated ID
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);

        // Return the generated ID
        return newId.longValue();
    }
}
