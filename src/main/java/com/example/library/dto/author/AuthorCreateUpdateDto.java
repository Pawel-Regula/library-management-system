package com.example.library.dto.author;

public record AuthorCreateUpdateDto(
        String name,
        String middleName,
        String surname)
{ }
