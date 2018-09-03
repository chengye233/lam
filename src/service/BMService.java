package service;

import model.domain.Book;
import model.domain.BookType;
import model.domain.Books;
import utils.page.CriteriaBook;
import utils.page.CriteriaBooks;
import utils.page.Page;

import java.util.List;

public interface BMService
{
    /**
     * 获取Books分页
     * @param criteriaBooks
     * @return
     */
    Page<Books> getBooks(CriteriaBooks criteriaBooks);


    /**
     * 获取图书类型列表
     * @return
     */
    List<BookType> getTypeList();

    /**
     * 登记Books
     * @param book
     */
    void registerBook(Books book);

    /**
     * 获取Books
     * @param booksId
     * @return
     */
    Books getBook(int booksId);

    /**
     * 修改Books
     * @param book
     * @param amount
     */
    void updateBooks(Books book, int amount);

    /**
     * 获取Book分页
     * @param criteriaBook
     * @return
     */
    Page<Book> getBookPage(CriteriaBook criteriaBook);

    /**
     * 获取Book
     * @param bookId
     * @return
     */
    Book getBookContent(int bookId);

    /**
     * 更新Book
     * @param book
     */
    void updateBook(Book book);

    /**
     * 删除Books
     * @param booksId
     */
    void deleteBooks(int booksId);

    /**
     * 删除Book
     * @param bookId
     */
    void deleteBook(int bookId);

    /**
     * 获得图书类别
     * @param bookTypeStr
     * @return
     */
    BookType getBookType(String bookTypeStr);

    /**
     * 更新图书类别
     * @param bookType
     */
    void updateType(BookType bookType);

    /**
     * 添加类别
     * @param bookType
     */
    void addType(BookType bookType);
}
