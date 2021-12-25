package io.aadesh.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.aadesh.userbooks.UserBooks;
import io.aadesh.userbooks.UserbooksPrimaryKey;
import io.aadesh.userbooks.UserbooksRepo;

@Controller
public class BookController {

    @Autowired
    BookRepo bookrepo;

    @Autowired
    UserbooksRepo userBooksRepo;

    final String base_url = "https://covers.openlibrary.org/b/id/";

    @GetMapping("/book/{book_id}")
    public String bookById(@PathVariable String book_id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<Book> optionalbook = bookrepo.findById(book_id);
        if (optionalbook.isPresent()) {
            Book book = optionalbook.get();
            String finalUrl = "src/main/resources/templates/images/No-Image.png";
            if (book.getCoverIds().size() > 0) {
                String coverId = book.getCoverIds().get(0);
                finalUrl = base_url + coverId + "-L.jpg";
            }

            model.addAttribute("coverImg", finalUrl);
            model.addAttribute("book", book);

            if (principal != null && principal.getAttribute("login") != null) {
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", userId);
                UserbooksPrimaryKey key = new UserbooksPrimaryKey();
                key.setBookId(book_id);
                key.setUserId(userId);

                Optional<UserBooks> optionalUserBook = userBooksRepo.findById(key);
                if (optionalUserBook.isPresent()) {
                    model.addAttribute("userBooks", optionalUserBook.get());
                } else {
                    model.addAttribute("userBooks", new UserBooks());
                }

            }

            return "book";

        }

        return "page-not-found";

    }

}
