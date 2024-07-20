package com.cucumber.allure.service;

import com.cucumber.allure.rest.clients.BookClient;
import com.cucumber.allure.rest.model.BookDTO;
import com.cucumber.allure.rest.model.CreateBookForUserRequestDTO;
import com.cucumber.allure.rest.model.GenericErrorResponse;
import com.cucumber.allure.rest.model.UserDTO;
import io.cucumber.spring.ScenarioScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ScenarioScope
public class BookService {
    private static final Logger LOG = LogManager.getLogger(BookService.class);

    private final BookClient bookClient;
    private final RandomService randomService;

    public BookService(final BookClient bookClient,
                       final RandomService randomService) {
        this.bookClient = bookClient;
        this.randomService = randomService;
    }


    public BookDTO initContextBook(final Long userId) {
        BookDTO book = new BookDTO();
        book.setUserId(userId);
        book.setAuthor(randomService.getRandomString(10));
        book.setTitle(randomService.getRandomString(10));
        LOG.debug("Book: {}", book);
        return book;
    }

    public ResponseEntity<BookDTO> registerBook(final BookDTO book) {
        CreateBookForUserRequestDTO body = new CreateBookForUserRequestDTO();
        body.setAuthor(book.getAuthor());
        body.setTitle(book.getTitle());
        return bookClient.createBookForUser(book.getUserId(), body);
    }

    public ResponseEntity<GenericErrorResponse> registerBookNegative(final BookDTO book) {
        CreateBookForUserRequestDTO body = new CreateBookForUserRequestDTO();
        body.setAuthor(book.getAuthor());
        body.setTitle(book.getTitle());
        return bookClient.createBookForUserNeg(book.getUserId(), body);
    }

    public ResponseEntity<List<BookDTO>> getBooks(final BookDTO book) {
        return bookClient.getBooksForUser(book.getUserId());
    }

    public ResponseEntity<Void> deleteBook(final UserDTO user, final BookDTO book) {
        return bookClient.deleteBookForUser(user.getId(), book.getUserId());
    }
}