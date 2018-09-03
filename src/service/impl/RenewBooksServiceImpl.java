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

import service.RenewBooksService;
import utils.DateUtils; 
import java.util.Date;
import java.util.Calendar;

public class RenewBooksServiceImpl implements RenewBooksService
{
	 UserDAO userDAO = new UserDAOImpl();
	 AccountDAO accountDAO = new AccountDAOImpl();
	 BookDAO bookDAO = new BookDAOImpl(); 
	 BooksDAO booksDAO=new BooksDAOImpl();
	 BorrowedRecordDAO borrowedrecordDAO=new BorrowedRecordDAOImpl();
	 DateUtils dateutils=new DateUtils();
	 Date date=new Date();
	 Calendar calendar=Calendar.getInstance();
	 
	 @Override
	 public boolean judge(BorrowedRecord borrowedrecord)
	 {
		 if(borrowedrecord.getRenewState()==0)
		 {
			 return true;
		 }
		 else
			 return false;
	 }
	 
	 @Override
	 public void changeInfo(Book book,BorrowedRecord borrowedrecord)
	 {
		 borrowedrecord.setRenewState(1);//set the renewstate
		 borrowedrecord.setBackTime(dateutils.updateDate(borrowedrecord.getBackTime(), 30));//increase the time
		 borrowedrecordDAO.updateRecord(borrowedrecord);
		 
		 
	 }
}
