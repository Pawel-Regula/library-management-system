package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="books")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "isbn")
public class Book implements Comparable<Book>,  Serializable {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "publication_year")
    private int publicationYear;

    private int pages;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        String sMiddleName = author.getMiddleName() == null ? "" : author.getMiddleName();
        return "Book{" +
                "ISBN='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", Author='" + author.getName() + ' ' +  sMiddleName +
                ' ' +  author.getSurname() +
                ", genre=" + genre +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                '}';
    }
}
