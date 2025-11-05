package org.example.library.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class BookDto implements Comparable<BookDto>, Serializable {
    private String isbn;

    private String title;
    private Genre genre;
    private int publicationYear;
    private int pages;

    private UUID authorId;

    @Override
    public int compareTo(BookDto other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public static BookDto from (Book b) {
        return BookDto.builder()
                .isbn(b.getIsbn())
                .title(b.getTitle())
                .genre(b.getGenre())
                .publicationYear(b.getPublicationYear())
                .pages(b.getPages())
                .authorId(b.getAuthor() != null ? b.getAuthor().getId() : null)
                .build();
    }

}
