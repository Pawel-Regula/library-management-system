package org.example.library.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Author implements Comparable<Author>, Serializable {

    private UUID id;

    private String name;
    private String middleName;
    private String surname;

    @Builder.Default
    private List<Book> books = new ArrayList<>();

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
