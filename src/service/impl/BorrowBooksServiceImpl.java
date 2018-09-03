package service.impl;

import java.util.Calendar;
import java.util.Date;

import model.dao.AccountDAO;
import model.dao.UserDAO;
import model.dao.impl.AccountDAOImpl;
import model.dao.impl.UserDAOImpl;
import model.domain.Users;
import model.domain.Book;
import model.domain.Books;
import model.domain.BorrowedRecord;
import model.dao.BooksDAO;
import model.dao.impl.BooksDAOImpl;
import model.dao.BookDAO;
import model.dao.impl.BookDAOImpl;
import model.dao.BorrowedRecordDAO;
import model.dao.impl.BorrowedRecordDAOImpl;

import service.BorrowBooksService;
import utils.DateUtils; 
import java.util.Date;
import java.util.Calendar;





public class BorrowBooksServiceImpl implements BorrowBooksService
{
	 UserDAO userDAO = new UserDAOImpl();
	 AccountDAO accountDAO = new AccountDAOImpl();
	 BookDAO bookDAO = new BookDAOImpl(); 
	 BooksDAO booksDAO=new BooksDAOImpl();
	 BorrowedRecordDAO borrowedrecordDAO=new BorrowedRecordDAOImpl();
	 Date date=new Date();
	 Calendar calendar=Calendar.getInstance();
	
	 int currentyear=calendar.get(Calendar.YEAR);
	 int currentmonth=calendar.get(Calendar.MONTH);
	 int currentdate=calendar.get(Calendar.DAY_OF_MONTH);
	 
	 @Override
	 public void changeUser(Users user)//increase the number of user's books
	 {
	 	if (user.getBookNumber() < 5)
		{
			int tempNumber=user.getBookNumber();
			user.setBookNumber(tempNumber+1);
			userDAO.updateUser(user);
		}
	 }
	 @Override
	 public void changeBook(Book book)//change the state of book has been borrowed
	 {
		int BookTempID=book.getBookId();
		bookDAO.borrowBook(BookTempID);
		
		
    }
	 @Override
	 public void changeBooks(Books books)//change the state of books
	 {
		books.setLeftAmount(books.getLeftAmount()-1);
		booksDAO.updateBooks(books);
	 }
	 @Override
	 public  boolean judgeUser(Users user)
	 {
		 if(user.getBookNumber()<=5)
		 {
			 return true;
		 }
		 else 
			 return false;
	 }
	 @Override
	 public boolean judgeBooks(Books books)
	 {
		 if(books.getLeftAmount()>=1)
		 {
			 return true;
		 }
		 else
			 return false;
	 }
	 @Override
	 
	 public void changeRecord(Book book,Users user,Books books)
	 
	 {  
		BorrowedRecord borrowedrecord =new BorrowedRecord();
		borrowedrecord.setBookId(book.getBookId());
		borrowedrecord.setUserId(user.getUserId());
		borrowedrecord.setBookName(books.getBookName());
		borrowedrecord.setUserId(user.getUserId());
		borrowedrecord.setUserName(user.getUserName());
		borrowedrecord.setBorrowedTime(DateUtils.getSqlDate(date));
		borrowedrecord.setBackTime(DateUtils.updateDate(DateUtils.getSqlDate(date), 30));
		borrowedrecord.setBackState(0);
		
		
		borrowedrecordDAO.insertRecord(borrowedrecord);
		
	 }
	 
	 
}
