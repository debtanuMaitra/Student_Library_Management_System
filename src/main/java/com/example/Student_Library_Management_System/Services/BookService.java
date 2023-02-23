package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.BookRequestDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;
/*
    public String addBook(Book book) {

        //I want to get the Author Entity
        int authorId = book.getAuthor().getId();

        //Now I can fetch the author entity
        Author author = authorRepository.findById(authorId).get();

        //Basic attributes are already set from postman

        //Setting the foreign key attribute in the child class
        book.setAuthor(author);

        //We need to update the listOfBooks written in the parent class
        List<Book> currentBooksWritten = author.getBooksWritten();
        currentBooksWritten.add(book);

        //Now the book is to be saved, but also author is also to be saved.

        //Why do we need to again save (updating) the author? because
        // the author entity has been updated ... we need to resave / update it.

        authorRepository.save(author); // Date was modified

        //.save function works both as save function and as update function

        //bookRepo.save is not required : because it will auto called by cascading effect

        return "Book added successfully";
    }
*/
    public String addBook(BookRequestDto bookRequestDto) {

        //I want to get the Author Entity
        int authorId = bookRequestDto.getAuthorId();

        //Now I can fetch the author entity
        Author author = authorRepository.findById(authorId).get();

        //CONVERTERS
        //We have created this entity so that we can save it into the DB
        Book book = new Book();

        //Basic attributes are being set from DTO to the entity layer
        book.setGenre(bookRequestDto.getGenre());
        book.setIssued(false);
        book.setName(bookRequestDto.getName());
        book.setPages(bookRequestDto.getPages());

        //Setting the foreign key attribute in the child class
        book.setAuthor(author);

        //We need to update the listOfBooks written in the parent class
        List<Book> currentBooksWritten = author.getBooksWritten();
        currentBooksWritten.add(book);

        //Now the book is to be saved, but also author is also to be saved.

        //Why do we need to again save (updating) the author? because
        // the author entity has been updated ... we need to resave / update it.

        authorRepository.save(author); // Date was modified

        //.save function works both as save function and as update function

        //bookRepo.save is not required : because it will auto called by cascading effect

        return "Book added successfully";
    }
}
