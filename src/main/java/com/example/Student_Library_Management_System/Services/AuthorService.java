package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public String createAuthor(AuthorEntryDto authorEntryDto) {

        //Important step is: In the parameter the object is of type DTO but the repository interacts only with the entities.
        //Solution: Convert authorEntryDto --> Author

        //Created an object of type author
        Author author = new Author();

        //We are setting its attributes so that we can save correct values in the database.
        author.setName(authorEntryDto.getName());
        author.setAge(authorEntryDto.getAge());
        author.setCountry(authorEntryDto.getCountry());
        author.setRating(authorEntryDto.getRating());

        authorRepository.save(author);

        return "Author added successfully";

    }
}
