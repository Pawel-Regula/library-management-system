package com.example.library.controller;

import com.example.library.dto.author.AuthorCreateUpdateDto;
import com.example.library.dto.author.AuthorListDto;
import com.example.library.dto.author.AuthorReadDto;
import com.example.library.dto.book.BookCreateUpdateDto;
import com.example.library.dto.book.BookListDto;
import com.example.library.dto.book.BookReadDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService,
                            BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    // GET /api/authors
    @GetMapping
    public List<AuthorListDto> getAllAuthors() {
        return authorService.findAll().stream()
                .map(this::toAuthorListDto)
                .toList();
    }

    // GET /api/authors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AuthorReadDto> getAuthor(@PathVariable UUID id) {
        Optional<Author> authorOptional = authorService.findById(id);
        return authorOptional
                .map(author -> ResponseEntity.ok(toAuthorReadDto(author))) // 200
                .orElseGet(() -> ResponseEntity.notFound().build()); //404
    }

    // POST /api/authors
    @PostMapping
    public ResponseEntity<AuthorReadDto> createAuthor(@RequestBody AuthorCreateUpdateDto dto) {
        Author author = new Author();
        author.setName(dto.name());
        author.setMiddleName(dto.middleName());
        author.setSurname(dto.surname());

        Author saved = authorService.save(author);

        URI location = URI.create("/api/authors/" + saved.getId());
        return ResponseEntity
                .created(location)     // 201 Created + Location
                .body(toAuthorReadDto(saved));
    }

    // PUT /api/authors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AuthorReadDto> updateAuthor(@PathVariable UUID id,
                                                      @RequestBody AuthorCreateUpdateDto dto) {
        Optional<Author> authorOptional = authorService.findById(id);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Author author = authorOptional.get();
        author.setName(dto.name());
        author.setMiddleName(dto.middleName());
        author.setSurname(dto.surname());

        Author saved = authorService.save(author);
        return ResponseEntity.ok(toAuthorReadDto(saved)); // 200
    }

    // DELETE /api/authors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        Optional<Author> authorOptional = authorService.findById(id);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // GET /api/authors/{authorId}/books
    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<BookListDto>> getBooksOfAuthor(@PathVariable UUID authorId) {
        Optional<Author> authorOptional = authorService.findById(authorId);
        // czy kategoria istnieje
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); //404
        }

        List<Book> books = authorOptional.get().getBooks();
        List<BookListDto> result = books.stream()
                .map(this::toBookListDto)
                .toList();

        return ResponseEntity.ok(result); //200
    }

    @GetMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<BookReadDto> getBookOfAuthor(@PathVariable UUID authorId,
                                                       @PathVariable UUID bookId) {
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Book> bookOptional = bookService.findById(bookId);
        if (bookOptional.isEmpty() || !bookOptional.get().getAuthor().getId().equals(authorId)) {
            return ResponseEntity.notFound().build(); //404
        }

        return ResponseEntity.ok(toBookReadDto(bookOptional.get())); //200
    }

    // POST /api/authors/{authorId}/books  -> dodaj książkę do autora
    @PostMapping("/{authorId}/books")
    public ResponseEntity<BookReadDto> addBookToAuthor(@PathVariable UUID authorId,
                                                       @RequestBody BookCreateUpdateDto dto) {
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); //404
        }

        Author author = authorOptional.get();

        Book book = new Book();
        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setGenre(dto.genre());
        book.setPublicationYear(dto.publicationYear());
        book.setPages(dto.pages());
        book.setAuthor(author);

        Book saved = bookService.save(book);

        URI location = URI.create("/api/books/" + saved.getId());
        return ResponseEntity
                .created(location)        // 201 Created
                .body(toBookReadDto(saved));
    }

    @PutMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<BookReadDto> updateBookOfAuthor(@PathVariable UUID authorId,
                                                          @PathVariable UUID bookId,
                                                          @RequestBody BookCreateUpdateDto dto) {

        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Book> bookOptional = bookService.findById(bookId);
        if (bookOptional.isEmpty() || !bookOptional.get().getAuthor().getId().equals(authorId)) {
            return ResponseEntity.notFound().build(); //404
        }

        Book book = bookOptional.get();
        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setGenre(dto.genre());
        book.setPublicationYear(dto.publicationYear());
        book.setPages(dto.pages());

        Book saved = bookService.save(book);
        return ResponseEntity.ok(toBookReadDto(saved)); // 200
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Void> deleteBookOfAuthor(@PathVariable UUID authorId,
                                                   @PathVariable UUID bookId) {
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); //404
        }

        Optional<Book> bookOptional = bookService.findById(bookId);
        if (bookOptional.isEmpty() || !bookOptional.get().getAuthor().getId().equals(authorId)) {
            return ResponseEntity.notFound().build(); //404
        }

        bookService.deleteById(bookId);
        return ResponseEntity.noContent().build(); //204
    }

    // MAPPERS

    private AuthorListDto toAuthorListDto(Author author) {
        String middle = author.getMiddleName() == null || author.getMiddleName().isBlank()
                ? ""
                : " " + author.getMiddleName();
        String fullName = author.getName() + middle + " " + author.getSurname();
        return new AuthorListDto(author.getId(), fullName);
    }

    private AuthorReadDto toAuthorReadDto(Author author) {
        return new AuthorReadDto(
                author.getId(),
                author.getName(),
                author.getMiddleName(),
                author.getSurname()
        );
    }

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