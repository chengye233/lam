package model.domain;

/**
 * 用于缴纳罚款
 */
public class Account
{
    private int accountId;  //主键
    private float salary;  //账户余额

    /**
     *setters&getters
     */
    public int getAccountId()
    {
        return accountId;
    }

    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    public float getSalary()
    {
        return salary;
    }

    public void setSalary(float salary)
    {
        this.salary = salary;
    }


}
