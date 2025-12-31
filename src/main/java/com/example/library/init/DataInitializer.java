package com.example.library.init;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import com.example.library.service.AuthorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {
    private final AuthorService authorService;

    public DataInitializer(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) {

        Author rowling = Author.builder()
                .name("Joanne")
                .middleName("Kathleen")
                .surname("Rowling")
                .build();

        Author mickiewicz = Author.builder()
                .name("Adam")
                .middleName("Bernard")
                .surname("Mickiewicz")
                .build();

        Author sienkiewicz = Author.builder()

                .name("Henryk")
                .surname("Sienkiewicz")
                .build();

        Author tolkien = Author.builder()
                .name("John")
                .middleName("Ronald Reuel")
                .surname("Tolkien")
                .build();

        Author slowacki = Author.builder()
                .name("Juliusz")
                .surname("Słowacki")
                .build();

        Author prus = Author.builder()
                .name("Bolesław")
                .surname("Prus")
                .build();

        Book potter = Book.builder()
                .isbn("978-83-246-5727-8")
                .title("Harry Potter")
                .genre(Genre.FANTASY)
                .publicationYear(1998)
                .pages(341)
                .author(rowling)
                .build();

        Book dziady = Book.builder()
                .isbn("978-83-246-5728-5")
                .title("Dziady II")
                .genre(Genre.DRAMA)
                .publicationYear(1823)
                .pages(435)
                .author(mickiewicz)
                .build();

        Book tadeusz = Book.builder()
                .isbn("978-0-261-10320-7")
                .title("Pan Tadeusz")
                .genre(Genre.EPIC)
                .publicationYear(1834)
                .pages(1178)
                .author(mickiewicz)
                .build();

        Book quoVadis = Book.builder()
                .isbn("978-83-7432-100-2")
                .title("Quo Vadis")
                .genre(Genre.HISTORICAL)
                .publicationYear(1896)
                .pages(512)
                .author(sienkiewicz)
                .build();

        Book ogniemIMieczem = Book.builder()
                .isbn("978-83-7432-101-9")
                .title("Ogniem i mieczem")
                .genre(Genre.HISTORICAL)
                .publicationYear(1884)
                .pages(680)
                .author(sienkiewicz)
                .build();

        Book potop = Book.builder()
                .isbn("978-83-7432-102-6")
                .title("Potop")
                .genre(Genre.HISTORICAL)
                .publicationYear(1886)
                .pages(930)
                .author(sienkiewicz)
                .build();

        Book lotr = Book.builder()
                .isbn("978-0-261-10236-1")
                .title("The Lord of the Rings")
                .genre(Genre.FANTASY)
                .publicationYear(1954)
                .pages(1216)
                .author(tolkien)
                .build();

        Book hobbit = Book.builder()
                .isbn("978-0-261-10221-7")
                .title("The Hobbit")
                .genre(Genre.FANTASY)
                .publicationYear(1937)
                .pages(310)
                .author(tolkien)
                .build();

        Book kordian = Book.builder()

                .isbn("978-83-240-5678-1")
                .title("Kordian")
                .genre(Genre.DRAMA)
                .publicationYear(1834)
                .pages(210)
                .author(slowacki)
                .build();

        Book lalka = Book.builder()
                .isbn("978-83-240-6123-5")
                .title("Lalka")
                .genre(Genre.REALISTIC)
                .publicationYear(1890)
                .pages(688)
                .author(prus)
                .build();

        Book balladyna = Book.builder()
                .isbn("978-83-240-6124-2")
                .title("Balladyna")
                .genre(Genre.DRAMA)
                .publicationYear(1839)
                .pages(250)
                .author(slowacki)
                .build();

        Book faraon = Book.builder()
                .isbn("978-83-240-6125-9")
                .title("Faraon")
                .genre(Genre.HISTORICAL)
                .publicationYear(1897)
                .pages(640)
                .author(prus)
                .build();

        Book janko = Book.builder()
                .isbn("978-83-240-6126-6")
                .title("Janko Muzykant")
                .genre(Genre.REALISTIC)
                .publicationYear(1879)
                .pages(30)
                .author(sienkiewicz)
                .build();

        rowling.addBook(potter);
        mickiewicz.addBooks(List.of(dziady, tadeusz));
        sienkiewicz.addBooks(List.of(quoVadis, ogniemIMieczem, potop, janko));
        tolkien.addBooks(List.of(lotr, hobbit));
        slowacki.addBooks(List.of(kordian, balladyna));
        prus.addBooks(List.of(lalka, faraon));

        authorService.save(rowling);
        authorService.save(mickiewicz);
        authorService.save(sienkiewicz);
        authorService.save(tolkien);
        authorService.save(slowacki);
        authorService.save(prus);
    }
}
