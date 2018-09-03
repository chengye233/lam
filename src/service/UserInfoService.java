package service;

import model.domain.BookType;
import model.domain.Books;
import model.domain.BorrowedRecord;
import model.domain.Users;
import utils.page.CriteriaBooks;
import utils.page.CriteriaUsers;
import utils.page.Page;

import java.util.List;

public interface UserInfoService
{
    /**
     * 获取用户列表
     * @param criteriaUsers
     */
    Page<Users> getUsers(CriteriaUsers criteriaUsers);

    /**
     * 获取用户图书列表
     * @param criteriaBooks
     * @return
     */
    Page<Books> getBooks(CriteriaBooks criteriaBooks);

    /**
     * 获取图书类别列表
     * @return
     */
    List<BookType> getBookTypeList();

    /**
     * 获取图书信息
     * @param booksId
     * @return
     */
    Books getBook(int booksId);

    /**
     * 获取用户
     * @param userId
     * @return
     */
    Users getUser(int userId);



    /**
     * 更新User
     * @param user
     */
    void updateUser(Users user);

    /**
     * 删除User
     * @param userId
     */
    void deleteUser(int userId);

    /**
     * 获取某人的借书记录(未还的)
     * @param userId
     * @return
     */
    List<BorrowedRecord> getBorrowedRecord(int userId);

    /**
     * 获取某人的借书记录(全部)
     * @param userId
     * @return
     */
    List<BorrowedRecord> getRecords(int userId);
}
