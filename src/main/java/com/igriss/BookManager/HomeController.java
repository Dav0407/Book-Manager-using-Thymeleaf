package com.igriss.BookManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {


    @Value("${welcome.message}")
    private String message;
    private final JdbcTemplate jdbcTemplate;

    public HomeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("message", message);
        return "home";
    }
    
    @GetMapping("/show/books")
    public String showBooks(Model model){
        model.addAttribute("message", message);
        String sql = "select * from books;";
        List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
        model.addAttribute("books", books);
        return "showBooks";
    }

    @GetMapping("/add/book")
    public String addBookPage(){

        return "addBook";
    }

    @PostMapping("/add/book")
    public String addBook(@ModelAttribute BookDTO bookDTO){
        String sql = "insert into books (title, description, price, author) values(?, ?, ?, ?);";
        jdbcTemplate.update(sql, bookDTO.getTitle(), bookDTO.getDescription(), bookDTO.getPrice(), bookDTO.getAuthor());
        return "redirect:/show/books";
    }

    @GetMapping("/delete/book")
    public String deleteBookPage(){
        return "deleteBook";
    }

    @PostMapping("/delete/book")
    public String deleteBook(@ModelAttribute BookDTO bookDTO){
        String sql = "delete from books where book_id = ?";
        jdbcTemplate.update(sql, bookDTO.getId());
        return "redirect:/";
    }

}
