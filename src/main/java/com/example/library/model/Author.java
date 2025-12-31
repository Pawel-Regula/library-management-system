package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Author implements Comparable<Author>, Serializable {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(nullable = false)
    private String surname;

    @Builder.Default
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    public void addBook(Book b) {
        books.add(b);
        b.setAuthor(this);
    }

    public void addBooks(List<Book> newBooks) {
        if (newBooks == null) return;
        newBooks.forEach(book -> {
            book.setAuthor(this);
            books.add(book);
        });
    }

    @Override
    public int compareTo(Author other) {
        int firstCriterion = this.surname.compareToIgnoreCase(other.surname);
        if (firstCriterion  != 0) return firstCriterion;

        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        String sMiddleName = middleName == null ? "" : middleName;
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", middleName='" + sMiddleName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
