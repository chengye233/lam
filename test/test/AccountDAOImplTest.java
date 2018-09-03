package test;

import model.dao.AccountDAO;
import model.dao.impl.AccountDAOImpl;
import model.domain.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDAOImplTest
{
    AccountDAO accountDAO = new AccountDAOImpl();
    @Test
    public void insertAccount()
            throws Exception
    {
        Account account = accountDAO.getAccount(43);
        System.out.println(account.getAccountId());
        System.out.println(account.getSalary());
    }

    @Test
    public void updateAccount()
            throws Exception
    {
//        accountDAO.updateAccount(21, 400);
        accountDAO.deleteAccount(2);
    }

}