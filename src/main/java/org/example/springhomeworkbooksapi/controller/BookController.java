 package org.example.springhomeworkbooksapi.controller;

import org.example.springhomeworkbooksapi.entity.Books;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Books> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Books("Title one", "Author one", "science"),
                new Books("Title two", "Author two", "science"),
                new Books("Title three", "Author three", "history"),
                new Books("Title four", "Author four", "math"),
                new Books("Title five", "Author five", "math"),
                new Books("Title six", "Author six", "math")
        ));
    }

    //  Fetch all books or by category using stream
    @GetMapping
    public List<Books> getBooks(@RequestParam(name = "category", required = false) String category) {
        if (category == null) {
            return books;
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    //  Fetch book by title using stream
    @GetMapping("/{title}")
    public Books getBookByTitle(@PathVariable(name = "title") String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    //  Create book if title doesn't exist
    @PostMapping
    public void createBook(@RequestBody Books newBook) {
        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if (isNewBook) {
            books.add(newBook);
        }
    }

    //  Update existing book by title
    @PutMapping("/{title}")
    public void updateBook(@PathVariable(name = "title") String title, @RequestBody Books updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    //  Delete book by title
    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable(name = "title") String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));

    }
}
