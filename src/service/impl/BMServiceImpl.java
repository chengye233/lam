package service.impl;

import model.dao.BookDAO;
import model.dao.BookTypeDAO;
import model.dao.BooksDAO;
import model.dao.impl.BookDAOImpl;
import model.dao.impl.BookTypeDAOImpl;
import model.dao.impl.BooksDAOImpl;
import model.domain.Book;
import model.domain.BookType;
import model.domain.Books;
import service.BMService;
import utils.DateUtils;
import utils.page.CriteriaBook;
import utils.page.CriteriaBooks;
import utils.page.Page;

import java.util.List;


public class BMServiceImpl implements BMService
{
    BooksDAO booksDAO = new BooksDAOImpl();
    BookDAO bookDAO = new BookDAOImpl();
    BookTypeDAO bookTypeDAO = new BookTypeDAOImpl();

    @Override
    public Page<Books> getBooks(CriteriaBooks criteriaBooks)
    {
        return booksDAO.getPage(criteriaBooks);
    }

    @Override
    public List<BookType> getTypeList()
    {
        return bookTypeDAO.getBookTypeList();
    }

    @Override
    public void registerBook(Books book)
    {
        //1.设置时间
        book.setRegisterTime(DateUtils.getSqlDate(new java.util.Date()));

        //2.插入Books表
        booksDAO.insertBooks(book);

        //3.插入Book表 (数量)
        bookDAO.insertBook(book.getBooksId(), book.getBookType()+book.getBooksId(), book.getTotalAmount());

    }

    @Override
    public Books getBook(int booksId)
    {
        return booksDAO.getBooks(booksId);
    }

    @Override
    public void updateBooks(Books book, int amount)
    {
        //更新Books
        booksDAO.updateBooks(book);
        //更新Book
        if (amount > 0)
        {
            bookDAO.insertBook(book.getBooksId(), book.getBookType()+book.getBooksId(), amount);
        }
    }

    @Override
    public Page<Book> getBookPage(CriteriaBook criteriaBook)
    {
        return bookDAO.getPage(criteriaBook);
    }

    @Override
    public Book getBookContent(int bookId)
    {
        return bookDAO.getBook(bookId);
    }

    @Override
    public void updateBook(Book book)
    {
        bookDAO.updateBook(book);
    }

    @Override
    public void deleteBooks(int booksId)
    {
        booksDAO.deleteBooks(booksId);
    }

    @Override
    public void deleteBook(int bookId)
    {
        bookDAO.deleteBook(bookId);
    }

    @Override
    public BookType getBookType(String bookTypeStr)
    {
        return bookTypeDAO.getBookType(bookTypeStr);
    }

    @Override
    public void updateType(BookType bookType)
    {
        bookTypeDAO.updateBookType(bookType);
    }

    @Override
    public void addType(BookType bookType)
    {
        bookTypeDAO.insertBookType(bookType);
    }


}
