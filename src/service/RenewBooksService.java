package service;

import model.domain.Book;
import model.domain.Users;
import model.domain.Books;
import model.domain.BorrowedRecord;

public interface RenewBooksService 
{
    boolean judge(BorrowedRecord borrowedrecord);
    void changeInfo(Book book, BorrowedRecord borrowedrecord);
}
