package com.example.library.dto.book;

import com.example.library.model.Genre;

public record BookCreateUpdateDto(
        String isbn,
        String title,
        Genre genre,
        int publicationYear,
        int pages
) {}