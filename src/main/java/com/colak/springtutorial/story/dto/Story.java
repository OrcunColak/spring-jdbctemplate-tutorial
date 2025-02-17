package com.colak.springtutorial.story.dto;

import java.time.OffsetDateTime;

public record Story(
        Long id,
        String title,
        String body,
        OffsetDateTime createdAt
) {

    public static Story truncateCreatedAt(Story story) {
        OffsetDateTime offsetDateTime = story.createdAt();
        // H2 internally stores timestamps with a precision of 6 digits for nanoseconds.
        // 371967800 becomes 371967000
        // Round the nanoseconds to the nearest microsecond
        int roundedNanos = (offsetDateTime.getNano() + 500) / 1000 * 1000;

        OffsetDateTime truncatedDateTime = offsetDateTime.withNano(roundedNanos);

        return new Story(story.id(), story.title(), story.body(), truncatedDateTime);
    }
}
