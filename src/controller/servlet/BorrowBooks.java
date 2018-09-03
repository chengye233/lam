package controller.servlet;


import model.domain.Users;
import model.domain.Account;
import model.domain.BorrowedRecord;
import model.domain.Books;
import model.domain.Book;
import model.dao.BorrowedRecordDAO;
import model.dao.impl.BorrowedRecordDAOImpl;
import model.dao.BookDAO;
import model.dao.impl.BookDAOImpl;
import model.dao.BooksDAO;
import model.dao.impl.BooksDAOImpl;
import model.dao.AccountDAO;
import model.dao.impl.AccountDAOImpl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import service.BackBooksService;
import service.BorrowBooksService;
import service.impl.BackBooksServiceImpl;
import service.impl.BorrowBooksServiceImpl;
import utils.XMLUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BorrowBooks extends javax.servlet.http.HttpServlet
{
      //service
	BorrowBooksService borrowbooksservice = new BorrowBooksServiceImpl();
	 
	BorrowedRecordDAO borrowedrecordDAO=new BorrowedRecordDAOImpl();
	BookDAO bookDAO=new BookDAOImpl();
	BooksDAO booksDAO=new BooksDAOImpl();
	AccountDAO accountDAO=new AccountDAOImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * 反射执行
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */ 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String methodName = request.getParameter("m");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/user/borrowError.jsp").forward(request, response);
        }
    }
    
    
    
    
    
    
    
    
    
    
    protected void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String page = request.getParameter("p");
        if (page == null || "".equals(page))
        {
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/user/borrowError.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/user/"
              +page +".jsp").forward(request, response);
    }


    

	protected  boolean judgeUser(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		int num;
		HttpSession session=request.getSession();
		Users user=(Users)session.getAttribute("user");
		num=user.getBookNumber();
		if(num<=5)
		{
			return true;
		}
		else 
			return false;
	 }
	
	
	protected boolean judgeBook(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		int bookState;
		int  useState;
		HttpSession session=request.getSession();
		Book book=(Book)session.getAttribute("book");
		bookState=book.getBookState();
		useState=book.getUseState();
		if(bookState==0&&useState==1)    //the book has not been borrowed and is in a good condition
		{
			return  true;
		}
		else
			return false;
		
	}
	
	
	
	
	
	//Finish the work of updating DB
	
	protected void changeUser(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{   
		if (judgeUser(request,response)==true)
		{
		HttpSession session=request.getSession();
		Users user=(Users)session.getAttribute("user");
    	String booksID=request.getParameter("booksID");
    	Books books=booksDAO.getBooks(Integer.parseInt(booksID));
    	Book book=bookDAO.borrowBook(books.getBooksId());


			//change the state of user
		
	    borrowbooksservice.changeUser(user);
	    //change the state of books
	    borrowbooksservice.changeBooks(books);

		//change the state of borrowedrecord
		borrowbooksservice.changeRecord(book,user,books);

			request.getRequestDispatcher("/user/ubServlet?m=getBook&booksId=" +booksID).forward(request, response);
		}
		else
		{
			request.setAttribute("errorMessage", "借书失败");
			request.getRequestDispatcher("/WEB-INF/user/borrowError.jsp").forward(request, response);
			return;
		}



	}
	
}