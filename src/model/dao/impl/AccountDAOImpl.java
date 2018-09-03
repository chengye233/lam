package model.dao.impl;

import model.dao.AccountDAO;
import model.domain.Account;

public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO
{
    /**
     * 添加账户 返回主键
     * @param balance
     * @return
     */
    @Override
    public int insertAccount(float balance)
    {
        String sql = "INSERT INTO ACCOUNT(BALANCE) VALUES (?) ";
        int id = insert(sql, new String[]{"ACCOUNTID"}, balance);
        return id;
    }

    /**
     * 缴纳罚款
     * @param accountId
     * @param fine
     */
    @Override
    public void updateAccount(int accountId, float fine)
    {
        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE ACCOUNTID = ?";
        update(sql, fine, accountId);

    }

    /**
     * 删除
     * @param accountId
     */
    @Override
    public void deleteAccount(int accountId)
    {
        String sql = "DELETE FROM ACCOUNT WHERE ACCOUNTID = ?";
        update(sql, accountId);
    }

    @Override
    public Account getAccount(int accountId)
    {
        String sql = "SELECT ACCOUNTID, BALANCE AS SALARY FROM ACCOUNT WHERE ACCOUNTID = ?";
        Account account =  query(sql, accountId);
        return account;
    }
}
