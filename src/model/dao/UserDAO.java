package model.dao;

import model.domain.Users;
import utils.page.CriteriaUsers;
import utils.page.Page;

public interface UserDAO
{
    /**
     * 添加User
     * @param users
     */
    void insertUser(Users users);

    /**
     * 删除User(根据主键)
     * @param userId
     */
    void deleteUser(int userId);

    /**
     * 更新User
     * @param users
     */
    void updateUser(Users users);

    /**
     * 查看User(根据主键)
     * @param userId
     * @return
     */
    Users getUser(int userId);

    /**
     * 根据邮箱查找User(验证注册时账户是否存在)
     * @param email
     * @return
     */
    Users getUser(String email);

    /**
     * 获取分页
     * @param criteriaUsers
     * @return
     */
    Page<Users> getPage(CriteriaUsers criteriaUsers);

}
