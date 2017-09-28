package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BooksController.class)
public class BooksControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllBooks() throws Exception {
        given(bookService.listAllBooks()).willReturn(new ArrayList<>());
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    public void getOneBook() throws Exception {
        Book book1 = new Book().setId(1L).setName("first book");
        given(bookService.getBookById(1L)).willReturn(book1);
        given(bookService.getBookById(2L)).willReturn(null);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)));

        mockMvc.perform(get("/books/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createBook() throws Exception {
        String json = "{\"name\":\"test\",\"description\":\"hehe\",\"price\":9.56}";
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());

        doThrow(new Exception()).when(bookService).createNewBook(any());
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateBook() throws Exception {
        given(bookService.updateBook(any())).willReturn(true);
        String json = "{\"name\":\"test\",\"description\":\"hehe\",\"price\":9.56}";
        mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isNoContent());

        given(bookService.updateBook(any())).willReturn(false);
        mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isNotFound());

        given(bookService.updateBook(any())).willThrow(new Exception());
        mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteBook() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }

}