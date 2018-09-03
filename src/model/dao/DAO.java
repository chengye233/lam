package model.dao;

import java.util.List;

public interface DAO<T>
{
    /**
     * 插入
     * @param args
     * @return 主键
     */
    int insert(String sql, String[] col, Object...args);

    /**
     * 更新&删除
     * @param sql
     * @param args
     */
    void update(String sql, Object...args);

    /**
     * 查询单条记录
     * @param sql
     * @param args
     * @return
     */
    T query(String sql, Object...args);

    /**
     * 查询多条
     * @param sql
     * @param args
     * @return
     */
    List<T> queryForList(String sql, Object...args);

    /**
     * 查询单值
     * @param sql
     * @param args
     * @param <V>
     * @return
     */
    <V> V queryForValue(String sql, Object...args);

    //void batch
}
