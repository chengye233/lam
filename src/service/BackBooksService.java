package service;

import model.domain.Book;
import model.domain.Users;
import model.domain.Books;
import model.domain.BorrowedRecord;
import model.domain.Account;

public interface BackBooksService 
{
     
     void Fine(Account account, BorrowedRecord borrowedrecord);
     void changeInfo(Book book, Books books, Users user, BorrowedRecord borrowedrecord);
     boolean isOver(BorrowedRecord borrowedrecord);
     
     
}
