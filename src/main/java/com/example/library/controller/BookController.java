package com.example.library.controller;

import com.example.library.dto.book.BookCreateUpdateDto;
import com.example.library.dto.book.BookListDto;
import com.example.library.dto.book.BookReadDto;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /api/books
    @GetMapping
    public List<BookListDto> getAllBooks() {
        return bookService.findAll().stream()
                .map(this::toBookListDto)
                .toList();
    }

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BookReadDto> getBook(@PathVariable UUID id) {
        Optional<Book> bookOptional = bookService.findById(id);
        return bookOptional
                .map(book -> ResponseEntity.ok(toBookReadDto(book))) //200
                .orElseGet(() -> ResponseEntity.notFound().build()); //404
    }

    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<BookReadDto> updateBook(@PathVariable UUID id,
                                                  @RequestBody BookCreateUpdateDto dto) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); //404
        }

        Book book = bookOptional.get();
        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setGenre(dto.genre());
        book.setPublicationYear(dto.publicationYear());
        book.setPages(dto.pages());

        Book saved = bookService.save(book);
        return ResponseEntity.ok(toBookReadDto(saved)); //200
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        Optional<Book> bookOpt = bookService.findById(id);
        if (bookOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); //404
        }

        bookService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // MAPPERS
    private BookListDto toBookListDto(Book book) {
        return new BookListDto(book.getId(), book.getTitle());
    }

    private BookReadDto toBookReadDto(Book book) {
        UUID authorId = book.getAuthor() != null ? book.getAuthor().getId() : null;
        return new BookReadDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getGenre(),
                book.getPublicationYear(),
                book.getPages(),
                authorId
        );
    }
}