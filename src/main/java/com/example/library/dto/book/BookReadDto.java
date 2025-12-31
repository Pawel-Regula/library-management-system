package com.example.library.dto.book;

import com.example.library.model.Genre;

import java.util.UUID;

public record BookReadDto(
        UUID id,
        String isbn,
        String title,
        Genre genre,
        int publicationYear,
        int pages,
        UUID authorId
) {}