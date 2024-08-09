package com.example.homeworkjpa2.controller;

import com.example.homeworkjpa2.model.Book;
import com.example.homeworkjpa2.model.BookRequest;
import com.example.homeworkjpa2.model.BookResponse;
import com.example.homeworkjpa2.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/books")

public class BookController {

    //inject repo
    private final BookRepository bookRepository;

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse<Book>>getBookByID(@PathVariable UUID id){
        BookResponse<Book>response = BookResponse.<Book>builder()
                .message("Sucessfully get book by ID!")
                .httpStatus(HttpStatus.CREATED)
                .payload(bookRepository.getBookByID(id))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<BookResponse<List<Book>>>getAllBook(){
        BookResponse<List<Book>>response = BookResponse.<List<Book>>builder()
                .message("Get all book successfully")
                .httpStatus(HttpStatus.CREATED)
                .payload(bookRepository.getAllBook())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<BookResponse<Book>> addBook(@RequestBody BookRequest bookRequest){
        UUID book = bookRepository.addBook(bookRequest).getId();
        BookResponse<Book> response = null;
        if(book != null){
            response = BookResponse.<Book>builder()
                    .message("Successfully added book!")
                    .httpStatus(HttpStatus.CREATED)
                    .payload(bookRepository.getBookByID(book))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else {
            response = BookResponse.<Book>builder()
                    .message("Fail to add book")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<BookResponse<List<Book>>>getBookByTitle(@PathVariable String title){
        BookResponse<List<Book>>response = BookResponse.<List<Book>>builder()
                .message("Successfully get book by title!")
                .httpStatus(HttpStatus.OK)
                .payload(bookRepository.getBookByTitle(title))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse<Book>> editBook(@PathVariable UUID id,@RequestBody BookRequest bookRequest){
        Book book = bookRepository.getBookByID(id);
        BookResponse<Book> response = null;
        if(book != null){
            response = BookResponse.<Book>builder()
                    .message("Book updated successfully!")
                    .httpStatus(HttpStatus.CREATED)
                    .payload(bookRepository.editBook(book.getId(),bookRequest))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else {
            response = BookResponse.<Book>builder()
                    .message("Failed to update book")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<BookResponse<Book>> editBook(@PathVariable UUID id){
        Book book = bookRepository.getBookByID(id);
        BookResponse<Book> response = null;
        if(book != null){
            response = BookResponse.<Book>builder()
                    .message("Successfully deleted book")
                    .httpStatus(HttpStatus.OK)
                    .payload(bookRepository.removeBook(book.getId()))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else {
            response = BookResponse.<Book>builder()
                    .message("Failed to delete book!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
    }
}
