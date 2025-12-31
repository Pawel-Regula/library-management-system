package com.example.library.dto.book;

import java.util.UUID;

public record BookListDto (
        UUID id,
        String title
) {}