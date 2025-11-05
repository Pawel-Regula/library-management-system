package org.example.library.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(of = "isbn")
public class Book implements Comparable<Book>,  Serializable {
    private String isbn;

    private String title;
    private Genre genre;
    private int publicationYear;
    private int pages;

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
