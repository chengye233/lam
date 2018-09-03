package service;

import model.domain.Book;
import model.domain.Users;
import model.domain.Books;
import model.domain.BorrowedRecord;


public interface BorrowBooksService
{
	//update the state of user
	void changeUser(Users user);
	//update the state of books
	void changeBooks(Books books);
	//update the state of book
	void changeBook(Book book);
	//judge whether the book could be borrowed or not 
	boolean judgeBooks(Books books);
	//judge whether the book could be borrowed to the user
	boolean judgeUser(Users user);
	
	//update the state of borrowrecord
	void changeRecord(Book book, Users user, Books books);
	
	
   
}
