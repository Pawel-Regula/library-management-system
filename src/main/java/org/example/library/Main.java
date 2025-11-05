package org.example.library;

import org.example.library.model.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Task 2
        System.out.println("\nTask 2: ");
        List<Author> authors = createLibrary();
        authors.forEach(author -> {
                System.out.println(author);
                author.getBooks().forEach(book -> System.out.println("> " + book));
        });

        // Task 3
        Set<Book> Books = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toSet());

        System.out.println("\nTask 3: ");

        Books.forEach(System.out::println);

        // Task 4
        System.out.println("\nTask 4: ");
        Books.stream()
                .filter(b -> b.getGenre() == Genre.HISTORICAL)
                .sorted()
                .forEach(System.out::println);

        // Task 5
        List<BookDto> bookDtos = Books.stream()
                .map(BookDto::from)
                .sorted()
                .toList();

        System.out.println("\nTask 5: ");
        bookDtos.forEach(System.out::println);

        // Task 6
        System.out.println("\nTask 6:");

        String filename = "library.dat";

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            output.writeObject(authors);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Author> loadedAuthors = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
            loadedAuthors = (List<Author>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (loadedAuthors != null) {
            loadedAuthors.forEach(author -> {
                System.out.println(author);
                author.getBooks().forEach(book -> System.out.println("> " + book));
            });
        }

        // Zadanie 7
        System.out.println("\nTask 7:");

        Integer[] threadCounts = new Integer[]{1, 2, 4};
        List<Long> times = new ArrayList<>();

        for (Integer numberOfThreads : threadCounts) {
            times.add(runWithPool(authors, numberOfThreads));
        }

        for (Long time : times) {
            System.out.printf("%d ms%n", time);
        }
    }

    private static long runWithPool(List<Author> authors, int numberOfThreads) {

        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);
        long startTimer = System.nanoTime();
        try {
            pool.submit(() -> authors
                            .parallelStream()
                            .forEach(Main::simulateWorkForAuthor)
            ).join();
        } finally {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(6, java.util.concurrent.TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        long endTimer = System.nanoTime();
        long time = (endTimer - startTimer) / 1_000_000;
        return time;
    }

    private static void simulateWorkForAuthor(Author author) {
        String t = Thread.currentThread().getName();
        System.out.println("[" + t + "] Start: " + author.getName() + " " + author.getSurname());
        try {

            for (Book b : author.getBooks()) {
                System.out.println("BOOK >" + b.getTitle() + " (" +  b.getGenre() + ", " +  b.getPublicationYear() + ")");
                Thread.sleep(300);
            }

            System.out.println("[" + t + "] End:" + author.getName() + " " + author.getSurname());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static List<Author> createLibrary() {
        Author rowling = Author.builder()
                .id(UUID.randomUUID())
                .name("Joanne")
                .middleName("Kathleen")
                .surname("Rowling")
                .build();

        Author mickiewicz = Author.builder()
                .id(UUID.randomUUID())
                .name("Adam")
                .middleName("Bernard")
                .surname("Mickiewicz")
                .build();

        Author sienkiewicz = Author.builder()
                .id(UUID.randomUUID())
                .name("Henryk")
                .surname("Sienkiewicz")
                .build();

        Author tolkien = Author.builder()
                .id(UUID.randomUUID())
                .name("John")
                .middleName("Ronald Reuel")
                .surname("Tolkien")
                .build();

        Author slowacki = Author.builder()
                .id(UUID.randomUUID())
                .name("Juliusz")
                .surname("Słowacki")
                .build();

        Author prus = Author.builder()
                .id(UUID.randomUUID())
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

        rowling.getBooks().add(potter);
        mickiewicz.getBooks().addAll(List.of(dziady, tadeusz));
        sienkiewicz.getBooks().addAll(List.of(quoVadis, ogniemIMieczem, potop, janko));
        tolkien.getBooks().addAll(List.of(lotr, hobbit));
        slowacki.getBooks().addAll(List.of(kordian, balladyna));
        prus.getBooks().addAll(List.of(lalka, faraon));

        return List.of(rowling, mickiewicz, sienkiewicz, tolkien, slowacki, prus);
    }
}
