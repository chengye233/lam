package model.dao;

import model.domain.BorrowedRecord;
import utils.page.CriteriaRecords;
import utils.page.Page;

import java.util.List;

public interface BorrowedRecordDAO
{

    /**
     * 检测该用户是否有未还并且超过还书日期的书
     * @param userId
     * @return true:没还; false:已还
     */
    boolean isBack(int userId);

    /**
     * 添加借书记录
     * @param borrowedRecord
     */
    void insertRecord(BorrowedRecord borrowedRecord);

    /**
     * 删除记录
     * @param recordId
     */
    void deleteRecord(int recordId);

    /**
     * 更新记录
     * @param borrowedRecord
     */
    void updateRecord(BorrowedRecord borrowedRecord);

    /**
     * 获取某一条记录
     * @param recordId
     * @return
     */
    BorrowedRecord getBorrowedRecord(int recordId);

    /**
     * 获取某人的借书记录(没有还的)
     * @param userId
     * @return
     */
    List<BorrowedRecord> getRecordList(int userId);

    /**
     * 获取某人所有借书记录(包括已还)
     * @param userId
     * @return
     */
    List<BorrowedRecord> getAllRecordList(int userId);

    /**
     * 获取分页
     * @param criteriaRecords
     * @return
     */
    Page<BorrowedRecord> getPage(CriteriaRecords criteriaRecords);

}
