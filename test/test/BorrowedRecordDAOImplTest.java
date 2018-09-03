package test;

import model.dao.BorrowedRecordDAO;
import model.dao.impl.BorrowedRecordDAOImpl;
import model.domain.BorrowedRecord;
import org.junit.Test;
import utils.DateUtils;
import utils.page.CriteriaRecords;
import utils.page.Page;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BorrowedRecordDAOImplTest
{
    BorrowedRecordDAO borrowedRecordDAO = new BorrowedRecordDAOImpl();

    @Test
    public void isBackTest()
    {
        System.out.println(borrowedRecordDAO.isBack(24));
    }

    @Test
    public void insertRecord()
            throws Exception
    {
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.setUserName("ddd");
        borrowedRecord.setBookName("fff");
        /**
         * 日期
         */
        borrowedRecord.setBorrowedTime(DateUtils.getSqlDate(new Date()));
        borrowedRecord.setBackTime(DateUtils.updateDate(borrowedRecord.getBorrowedTime(), 60));
        borrowedRecordDAO.insertRecord(borrowedRecord);
    }

    @Test
    public void deleteRecord()
            throws Exception
    {
        borrowedRecordDAO.deleteRecord(22);
    }

    @Test
    public void updateRecord()
            throws Exception
    {
        BorrowedRecord borrowedRecord = borrowedRecordDAO.getBorrowedRecord(1);
        borrowedRecord.setBorrowedTime(DateUtils.getSqlDate(new Date()));
        borrowedRecordDAO.updateRecord(borrowedRecord);

    }

    @Test
    public void getBorrowedRecord()
            throws Exception
    {
        BorrowedRecord borrowedRecord = borrowedRecordDAO.getBorrowedRecord(24);
        System.out.println(borrowedRecord.getBorrowedTime());
    }

    @Test
    public void getRecordList()
    {
        List<BorrowedRecord> list = borrowedRecordDAO.getRecordList(0);
        System.out.println(list);
    }

    @Test
    public void getPage()
            throws Exception
    {
        CriteriaRecords criteriaRecords = new CriteriaRecords(1, "d", 2, 2);
        Page<BorrowedRecord> page = borrowedRecordDAO.getPage(criteriaRecords);

        for (int i = 0; i < page.getPageMap().size(); i++)
        {
            for (BorrowedRecord borrowedRecord : page.getPageMap().get(i))
            {
                System.out.print(borrowedRecord.getRecordId() +"\t");
            }
            System.out.println();
        }
    }

}