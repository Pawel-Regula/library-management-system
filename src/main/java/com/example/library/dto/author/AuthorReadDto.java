package com.example.library.dto.author;

import java.util.UUID;

public record AuthorReadDto(
        UUID id,
        String name,
        String middleName,
        String surname)
{ }