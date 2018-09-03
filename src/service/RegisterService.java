package service;

import model.domain.Users;

public interface RegisterService
{
    /**
     * 注册
     * @param user
     */
    void register(Users user);

    /**
     * 激活用户
     * @param userId
     */
    boolean activeUser(int userId);

    /**
     * 根据邮箱获取user
     * @param email
     * @return
     */
    Users getUser(String email);

    /**
     * 找回密码
     * @param email
     */
    void password(String email);
}
