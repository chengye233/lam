package model.dao.impl;

import model.dao.BorrowedRecordDAO;
import model.domain.BorrowedRecord;
import utils.DateUtils;
import utils.page.CriteriaRecords;
import utils.page.Page;

import java.math.BigDecimal;
import java.util.*;

public class BorrowedRecordDAOImpl extends BaseDAO<BorrowedRecord>
        implements BorrowedRecordDAO
{
    /**
     * 检测是否有未还并且超过还书日期的书
     * @param userId
     * @return true:没还; false:已还
     */
    @Override
    public boolean isBack(int userId)
    {
        String sql = "SELECT COUNT(RECORDID) FROM BORROWEDRECORD WHERE USERID = ? " +
                " AND BACKSTATE = ? AND BACKTIME < to_date(?, ?)";
        java.sql.Date date = DateUtils.getSqlDate(new Date());
        BigDecimal bigDecimal = queryForValue(sql, userId, 0,  date.toString(), "yyyy-mm-dd");
        int count = bigDecimal.intValue();
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * 添加记录
     * @param borrowedRecord
     */
    @Override
    public void insertRecord(BorrowedRecord borrowedRecord)
    {
        String sql = "INSERT INTO BORROWEDRECORD(USERID, BOOKID, USERNAME, " +
                " BOOKNAME, BORROWEDTIME, BACKTIME, BACKSTATE, RENEWSTATE, FINE) " +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        update(sql, borrowedRecord.getUserId(), borrowedRecord.getBookId(), borrowedRecord.getUserName(),
                borrowedRecord.getBookName(), borrowedRecord.getBorrowedTime(), borrowedRecord.getBackTime(),
                borrowedRecord.getBackState(), borrowedRecord.getRenewState(), borrowedRecord.getFine());

    }

    /**
     * 删除记录
     * @param recordId
     */
    @Override
    public void deleteRecord(int recordId)
    {
        String sql = "DELETE FROM BORROWEDRECORD WHERE RECORDID = ?";
        update(sql, recordId);
    }

    /**
     * 更新记录 userName, bookName, borrowedTime, backTime, state, fine
     * @param borrowedRecord
     */
    @Override
    public void updateRecord(BorrowedRecord borrowedRecord)
    {
        String sql = "UPDATE BORROWEDRECORD SET USERNAME = ?, " +
                " BOOKNAME = ?, BORROWEDTIME = ?, BACKTIME = ?, BACKSTATE = ?, " +
                " RENEWSTATE = ?, FINE = ? " +
                " WHERE RECORDID = ?";
        update(sql, borrowedRecord.getUserName(), borrowedRecord.getBookName(),
                borrowedRecord.getBorrowedTime(), borrowedRecord.getBackTime(), borrowedRecord.getBackState(),
                borrowedRecord.getRenewState(), borrowedRecord.getFine(), borrowedRecord.getRecordId());
    }

    /**
     * 获取某一条记录
     * @param recordId
     * @return
     */
    @Override
    public BorrowedRecord getBorrowedRecord(int recordId)
    {
        String sql = "SELECT RECORDID, USERID, BOOKID, USERNAME, " +
                " BOOKNAME, BORROWEDTIME, BACKTIME, BACKSTATE, RENEWSTATE, FINE " +
                " FROM BORROWEDRECORD WHERE RECORDID = ?";
        BorrowedRecord borrowedRecord = query(sql, recordId);
        return borrowedRecord;
    }

    /**
     *获取userId的借书记录(没还的)
     * @param userId
     * @return
     */
    @Override
    public List<BorrowedRecord> getRecordList(int userId)
    {
        String sql = "SELECT RECORDID, USERID, BOOKID, USERNAME, " +
                " BOOKNAME, BORROWEDTIME, BACKTIME, BACKSTATE, RENEWSTATE, FINE " +
                " FROM BORROWEDRECORD WHERE USERID = ? AND BACKSTATE = ?";
        List<BorrowedRecord> list = new ArrayList<>(queryForList(sql, userId, 0));
        return list;
    }

    /**
     *获取userId的借书记录(还和没还)
     * @param userId
     * @return
     */
    @Override
    public List<BorrowedRecord> getAllRecordList(int userId)
    {
        String sql = "SELECT RECORDID, USERID, BOOKID, USERNAME, " +
                " BOOKNAME, BORROWEDTIME, BACKTIME, BACKSTATE, RENEWSTATE, FINE " +
                " FROM BORROWEDRECORD WHERE USERID = ?";
        List<BorrowedRecord> list = new ArrayList<>(queryForList(sql, userId));
        return list;
    }

    /**
     * 获取分页
     * @param criteriaRecords
     * @return
     */
    @Override
    public Page<BorrowedRecord> getPage(CriteriaRecords criteriaRecords)
    {
        /**
         * 实例化Page对象
         * 设置参数
         */
        Page<BorrowedRecord> page = new Page<>(criteriaRecords.getPageNo());
        page.setLineSize(criteriaRecords.getLineSize());
        page.setColumnSize(criteriaRecords.getColumnSize());
        page.setPageSize();
        int totalItemNumber = getTotalItemNumber(criteriaRecords);
        page.setTotalItemNumber(totalItemNumber);
        page.setPageMap(getRecordMap(criteriaRecords, page.getPageSize()));
        return page;

    }

    /**
     * 获取总记录数
     * @param criteriaRecords
     * @return
     */
    private int getTotalItemNumber(CriteriaRecords criteriaRecords)
    {
        String keyWord = criteriaRecords.getKeyWords();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        BigDecimal bigDecimal = null;
        int totalItemNumber = 0;
        if ("".equals(keyWord.trim()) || keyWord == null)
        {
            String sql = "SELECT COUNT(RECORDID) FROM BORROWEDRECORD ";
            bigDecimal = queryForValue(sql);
        }
        else
        {
            keyWord = "%" +keyWord +"%";
            String sql = "SELECT COUNT(RECORDID) FROM BORROWEDRECORD WHERE USERNAME LIKE ? " +
                    " OR BOOKNAME LIKE ? ";
            bigDecimal = queryForValue(sql, keyWord, keyWord);
        }
        if (bigDecimal != null)
        {
            totalItemNumber = bigDecimal.intValue();
        }
        else
        {
            totalItemNumber = 0;
        }
        return totalItemNumber;
    }

    /**
     * 获取pageMap
     * @param criteriaRecords
     * @param pageSize
     * @return
     */
    private Map<Integer, List<BorrowedRecord>> getRecordMap
            (CriteriaRecords criteriaRecords, int pageSize)
    {
        /**
         * 参数
         */
        int lineSize = criteriaRecords.getLineSize();
        int columnSize = criteriaRecords.getColumnSize();
        String keyWord = criteriaRecords.getKeyWords();
        //keyWord不能为null
        if (keyWord == null)
        {
            keyWord = "";
        }
        /**
         * pageMap
         */
        Map<Integer, List<BorrowedRecord>> pageMap = new LinkedHashMap<>();
        /**
         * 没有输入条件查询全部
         */
        if ("".equals(keyWord.trim()) || keyWord == null)
        {
            String sql = "SELECT RECORDID, USERID, BOOKID, USERNAME, " +
                    " BOOKNAME, BORROWEDTIME, BACKTIME, BACKSTATE, RENEWSTATE, FINE " +
                    " FROM (SELECT A.*, ROWNUM rn FROM (SELECT * FROM BORROWEDRECORD) A " +
                    " WHERE ROWNUM <= ? ) WHERE rn > ? ";
            int i = 0;
            while (i < lineSize)
            {
                List<BorrowedRecord> booksList = new ArrayList<>(queryForList(sql,
                        (criteriaRecords.getPageNo()-1)*pageSize+(i+1)*columnSize,
                        (criteriaRecords.getPageNo()-1)*pageSize+i*columnSize));
                if (booksList != null && booksList.size() != 0)
                {
                    pageMap.put(i, booksList);
                }
                else
                {
                    i = lineSize;
                }
                i++;
            }
        }
        else
        {
            keyWord = "%" +keyWord +"%";
            String sql = "SELECT RECORDID, USERID, BOOKID, USERNAME, " +
                    " BOOKNAME, BORROWEDTIME, BACKTIME, BACKSTATE, RENEWSTATE, FINE " +
                    " FROM (SELECT A.*, ROWNUM rn FROM(SELECT * FROM BORROWEDRECORD WHERE USERNAME LIKE ? OR BOOKNAME LIKE ? ) A WHERE ROWNUM <= ?)  " +
                    " WHERE rn > ? ";
            int i = 0;
            while (i < lineSize)
            {
                List<BorrowedRecord> booksList = queryForList(sql,keyWord, keyWord,
                        (criteriaRecords.getPageNo()-1)*pageSize+(i+1)*columnSize,
                        (criteriaRecords.getPageNo()-1)*pageSize+i*columnSize);
                if (booksList != null && booksList.size() != 0)
                {
                    pageMap.put(i, booksList);
                }
                else
                {
                    i = lineSize;
                }
                i++;
            }
        }
        return pageMap;
    }

}
