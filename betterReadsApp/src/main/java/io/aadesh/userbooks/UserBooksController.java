package io.aadesh.userbooks;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import io.aadesh.book.Book;
import io.aadesh.book.BookRepo;
import io.aadesh.booksbyuser.BooksByUser;
import io.aadesh.booksbyuser.BooksbyUserRepo;

@Controller
public class UserBooksController {

    @Autowired
    UserbooksRepo userBookRepo;

    @Autowired
    BooksbyUserRepo booksbyUserRepo;

    @Autowired
    BookRepo bookRepository;

    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData,
            @AuthenticationPrincipal OAuth2User principal) {

        UserBooks userBook = new UserBooks();
        BooksByUser booksByUser = new BooksByUser();
        UserbooksPrimaryKey key = new UserbooksPrimaryKey();
        if (principal == null || principal.getAttribute("login") == null) {
            return null;
        }

        String bookId = formData.getFirst("bookId");
        String userId = principal.getAttribute("login");
        int rating = Integer.parseInt(formData.getFirst("rating"));

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return new ModelAndView("redirect:/");
        }
        Book book = optionalBook.get();

        key.setBookId(bookId);
        key.setUserId(userId);
        userBook.setKey(key);
        userBook.setStartDate(LocalDate.parse(formData.getFirst("startDate")));
        userBook.setCompletedData(LocalDate.parse(formData.getFirst("completedDate")));
        userBook.setReadingStatus(formData.getFirst("readingStatus"));
        userBook.setRating(rating);

        userBookRepo.save(userBook);

        booksByUser.setId(userId);
        booksByUser.setBookId(bookId);
        booksByUser.setBookName(book.getName());
        booksByUser.setCoverIds(book.getCoverIds());
        booksByUser.setAuthorNames(book.getCauthorNames());
        booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
        booksByUser.setRating(rating);
        booksbyUserRepo.save(booksByUser);

        return new ModelAndView("redirect:/book/" + bookId);
    }
}
