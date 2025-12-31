package com.example.library.dto.author;

import java.util.UUID;

public record AuthorListDto(
        UUID id,
        String fullName
) {}