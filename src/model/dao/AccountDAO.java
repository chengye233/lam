package model.dao;

import model.domain.Account;

public interface AccountDAO
{
    /**
     * 添加账户, 注册时使用
     * @param balance
     * @return
     */
    int insertAccount(float balance);

    /**
     * 缴纳罚款用
     * @param accountId
     * @param fine
     */
    void updateAccount(int accountId, float fine);

    /**
     * 删除账户
     * @param accountId
     */
    void deleteAccount(int accountId);

    /**
     * 获取账户
     * @param accountId
     * @return
     */
    Account getAccount(int accountId);

}
