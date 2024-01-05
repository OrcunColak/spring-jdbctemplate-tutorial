package com.colak.springjdbctemplatetutorial.story.dto;

import java.time.Instant;

public record Story(
        Long id,
        String title,
        String body,
        Instant createdAt
) {
}
