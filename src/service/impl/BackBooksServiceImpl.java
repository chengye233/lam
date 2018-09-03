package service.impl;

import model.dao.AccountDAO;
import model.dao.UserDAO;
import model.dao.impl.AccountDAOImpl;
import model.dao.impl.UserDAOImpl;
import model.domain.Users;
import model.domain.Account;
import model.domain.Book;
import model.domain.Books;
import model.domain.BorrowedRecord;
import model.dao.BooksDAO;
import model.dao.impl.BooksDAOImpl;
import model.dao.BookDAO;
import model.dao.impl.BookDAOImpl;
import model.dao.BorrowedRecordDAO;
import model.dao.impl.BorrowedRecordDAOImpl;

import service.BackBooksService;

import utils.DateUtils;
import java.util.Date;
import java.util.Calendar;



public class BackBooksServiceImpl implements BackBooksService
{
	 UserDAO userDAO = new UserDAOImpl();
	 AccountDAO accountDAO = new AccountDAOImpl();
	 BookDAO bookDAO = new BookDAOImpl(); 
	 BooksDAO booksDAO=new BooksDAOImpl();
	 BorrowedRecordDAO borrowedrecordDAO=new BorrowedRecordDAOImpl();
	 DateUtils dateutils=new DateUtils();
	 Date date=new Date();
	 Calendar calendar=Calendar.getInstance();
	 int fine=0;
	 int currentyear=calendar.get(Calendar.YEAR);
	 int currentmonth=calendar.get(Calendar.MONTH);
	 int currentdate=calendar.get(Calendar.DAY_OF_MONTH);
	 
@Override
   public boolean isOver(BorrowedRecord borrowedrecord)//true means 
   {
	   int backyear=dateutils.getSqlDate(borrowedrecord.getBackTime()).getYear();
	   int backmonth=dateutils.getSqlDate(borrowedrecord.getBackTime()).getMonth();
	   int backdate=dateutils.getSqlDate(borrowedrecord.getBackTime()).getDay();
	   if(backyear>currentyear)
	   {
		  return true;
	   }
	   else if(backyear==currentyear)
	   {
		   if(backmonth>currentmonth)
		   {
			   return true;
		   }
		   else if(backmonth==currentmonth)
		   {
			   if(backdate>currentdate)
			   {
				   return true;
			   }
			   else 
				   return false;
			   
		   }
		   else 
			   return false;
	   }
	   else 
	   return false;
	   
   }
@Override
    public void Fine(Account account,BorrowedRecord borrowedrecord)
   {
	int backyear=dateutils.getSqlDate(borrowedrecord.getBackTime()).getYear();
	int backmonth=dateutils.getSqlDate(borrowedrecord.getBackTime()).getMonth();
	int backdate=dateutils.getSqlDate(borrowedrecord.getBackTime()).getDay();
	fine=365*(currentyear-backyear)+30*(currentmonth-backmonth+1*(currentdate-backdate));
	borrowedrecord.setFine(fine);
	account.setSalary(account.getSalary()-fine);
	
   }
	 
@Override
   public void changeInfo(Book book,Books books,Users user,BorrowedRecord borrowedrecord) 
   {
	
		book.setBookState(0);
		books.setLeftAmount(books.getLeftAmount()+1);
		user.setBookNumber(user.getBookNumber()-1);
		borrowedrecord.setBackTime(dateutils.getSqlDate(date));
		borrowedrecord.setBackState(1);
		borrowedrecord.setFine(fine);
		bookDAO.updateBook(book);
		booksDAO.updateBooks(books);
		userDAO.updateUser(user);
		borrowedrecordDAO.updateRecord(borrowedrecord);
	}
   

}
