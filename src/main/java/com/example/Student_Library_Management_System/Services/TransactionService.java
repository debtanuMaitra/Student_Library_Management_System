package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transactions;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;

    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        int bookId = issueBookRequestDto.getBookId();
        int cardId = issueBookRequestDto.getCardId();

        //Get the book entity and card entity. We are doing this because we want to set the transaction attributes
        Book book = bookRepository.findById(bookId).get();
        Card card = cardRepository.findById(cardId).get();

        //Final goal is to make a transaction entity, set its attributes and save it
        Transactions transactions = new Transactions();

        //Setting the attributes
        transactions.setBook(book);
        transactions.setCard(card);
        transactions.setTransactionId(UUID.randomUUID().toString());
        transactions.setIssueOperation(true);
        transactions.setTransactionStatus(TransactionStatus.PENDING);

        //Check for validations
        if(book==null || book.isIssued()==true){
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transactions);
            throw new Exception("Book is not available");

        }

        if(card==null || (card.getCardStatus()!= CardStatus.ACTIVATED)){
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transactions);
            throw new Exception("Card is not valid");
        }

        //We have reached a success case now
        transactions.setTransactionStatus(TransactionStatus.SUCCESS);

        //Set the attributes of book
        book.setIssued(true);
        //Between the book and transaction: Bidirectional
        List<Transactions> listOfTransactionForBook = book.getListOfTransactions();
        listOfTransactionForBook.add(transactions);
        book.setListOfTransactions(listOfTransactionForBook);

        //We need to make changes in the card also
        //Book and the Card
        List<Book> issuedBooksForCard = card.getBooksIssued();
        issuedBooksForCard.add(book);
        card.setBooksIssued(issuedBooksForCard);

        //Card and The transaction: Bidirectional (Parent Class)
        List<Transactions> transactionsListForCard = card.getTransactionsList();
        transactionsListForCard.add(transactions);
        card.setTransactionsList(transactionsListForCard);

        //Save the parent
        cardRepository.save(card);
        //Automatically by cascading: Book and transaction will be saved by saving the parent

        return "Book issued successfully!";
    }

    public String getTransactions(int bookId, int cardId) {
        List<Transactions> transactionsList = transactionRepository.getTransactionsForBookAndCard(bookId, cardId);

        String transactionId = transactionsList.get(0).getTransactionId();

        return transactionId;
    }
}


