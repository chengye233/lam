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

import service.BorrowBooksService;
import service.RenewBooksService;
import service.impl.BackBooksServiceImpl;
import service.impl.BorrowBooksServiceImpl;
import service.impl.RenewBooksServiceImpl;
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
public class RenewBooksServlet extends javax.servlet.http.HttpServlet
{
	/**
     * Service
     */
    RenewBooksService renewbooksService=new RenewBooksServiceImpl();
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
            request.getRequestDispatcher("/WEB-INF/user/renewError.jsp").forward(request, response);
        }
    }

    /**
     * 跳转
     * @param request
     * @param response
     */
    protected void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String page = request.getParameter("p");
        if (page == null || "".equals(page))
        {
            request.setAttribute("errorMessage", "找不到页面!");
            request.getRequestDispatcher("/WEB-INF/user/renewError.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/user/"
              +page +".jsp").forward(request, response);
    }
    
    
    protected void renewBooks (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	HttpSession session=request.getSession();
		Users user=(Users)session.getAttribute("user");
    	String recordID=request.getParameter("recordID");
    	BorrowedRecord borrowedrecord=borrowedrecordDAO.getBorrowedRecord(Integer.parseInt(recordID));
    	int bookID=borrowedrecord.getBookId();
    	Book book=bookDAO.getBook(bookID);
    	int booksID=book.getBooksId();
    	Books books=booksDAO.getBooks(booksID);
    	int accountID=user.getAccountId();
    	Account account=accountDAO.getAccount(accountID);
        if (renewbooksService.judge(borrowedrecord)==true)
        {
        	renewbooksService.changeInfo(book, borrowedrecord);
        }
        else
        {
            request.setAttribute("errorMessage", "renewerror");
            request.getRequestDispatcher("/WEB-INF/user/renewError.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/user/ubServlet?m=getBorrowedRecords&userId=" +user.getUserId()).forward(request, response);
    }
}
