package service.impl;

import model.dao.BookTypeDAO;
import model.dao.BooksDAO;
import model.dao.BorrowedRecordDAO;
import model.dao.UserDAO;
import model.dao.impl.BookTypeDAOImpl;
import model.dao.impl.BooksDAOImpl;
import model.dao.impl.BorrowedRecordDAOImpl;
import model.dao.impl.UserDAOImpl;
import model.domain.BookType;
import model.domain.Books;
import model.domain.BorrowedRecord;
import model.domain.Users;
import service.UserInfoService;
import utils.page.CriteriaBooks;
import utils.page.CriteriaUsers;
import utils.page.Page;

import java.util.List;

public class UserInfoServiceImpl implements UserInfoService
{
    UserDAO userDAO = new UserDAOImpl();
    BooksDAO booksDAO = new BooksDAOImpl();
    BookTypeDAO bookTypeDAO = new BookTypeDAOImpl();
    BorrowedRecordDAO borrowedRecordDAO = new BorrowedRecordDAOImpl();

    @Override
    public Page<Users> getUsers(CriteriaUsers criteriaUsers)
    {
        return userDAO.getPage(criteriaUsers);
    }

    @Override
    public Page<Books> getBooks(CriteriaBooks criteriaBooks)
    {
        return booksDAO.getPage(criteriaBooks);
    }

    @Override
    public List<BookType> getBookTypeList()
    {
        return bookTypeDAO.getBookTypeList();
    }

    @Override
    public Books getBook(int booksId)
    {
        return booksDAO.getBooks(booksId);
    }

    @Override
    public Users getUser(int userId)
    {
        return userDAO.getUser(userId);
    }

    @Override
    public List<BorrowedRecord> getBorrowedRecord(int userId)
    {
        return borrowedRecordDAO.getRecordList(userId);
    }

    @Override
    public List<BorrowedRecord> getRecords(int userId)
    {
        return borrowedRecordDAO.getAllRecordList(userId);
    }

    @Override
    public void updateUser(Users user)
    {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(int userId)
    {
        userDAO.deleteUser(userId);
    }
}
