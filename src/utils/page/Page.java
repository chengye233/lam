package utils.page;

import java.util.List;
import java.util.Map;

/**
 * 分页类 根据T分页
 * 手动setLineSize;setColumnSize;setTotalItemNumber;setPageSize
 * @param <T>
 */
public class Page<T>
{
    private int pageNo;  //当前页号
    private int totalItemNumber;  //总记录数
    private Map<Integer, List<T>> pageMap;  //每行的记录内容
    private int lineSize;  //行数
    private int columnSize = 1;  //每行显示的记录数
    private int pageSize = 5;  //每页显示的记录数 lineSize*columnSize

    /**
     * 构造器
     * @param pageNo
     */
    public Page(int pageNo)
    {
        this.pageNo = pageNo;
    }

    /**
     * setters&getters
     * @return
     */
    //获取当前页号
    public int getPageNo()
    {
        //pageNo:1-getTotalPageNumber
        if (pageNo > getTotalPageNumber())
        {
            pageNo = getTotalPageNumber();
        }
        else if (pageNo < 1)
        {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    //获取总页数
    public int getTotalPageNumber()
    {
        if (totalItemNumber / pageSize == 0)
        {
            return 1;
        }
        else if (totalItemNumber%pageSize == 0)
        {
            return totalItemNumber/pageSize;
        }
        else
        {
            return totalItemNumber/pageSize +1;
        }
    }

    public int getTotalItemNumber()
    {
        return totalItemNumber;
    }

    public void setTotalItemNumber(int totalItemNumber)
    {
        this.totalItemNumber = totalItemNumber;
    }

    public Map<Integer, List<T>> getPageMap()
    {
        return pageMap;
    }

    public void setPageMap(Map<Integer, List<T>> pageMap)
    {
        this.pageMap = pageMap;
    }

    public int getLineSize()
    {
        return lineSize;
    }

    //设置行数 默认:5行
    public void setLineSize(int lineSize)
    {
        if (lineSize != 0)
        {
            this.lineSize = lineSize;
        }
        else
        {
            this.lineSize = 5;
        }
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    //设置每行记录数 默认:1
    public void setColumnSize(int columnSize)
    {
        if (columnSize != 0)
        {
            this.columnSize = columnSize;
        }
        else
        {
            columnSize = 1;
        }

    }

    public int getPageSize()
    {
        return pageSize;
    }

    //设置当前页的记录数 lineSize*columnSize 默认:5
    public void setPageSize()
    {
        if (lineSize != 0 && columnSize != 0)
        {
            this.pageSize = lineSize *columnSize;
        }
        else
        {
            this.pageSize = 5;
        }
    }

    /**
     * nextPage prevPage
     */
    //判断有没有
    public boolean isHasNext()
    {
        if (getPageNo() >= getTotalPageNumber())
        {
            return false;
        }
        return true;
    }
    public boolean isHasPrev()
    {
        if (getPageNo() <= 1)
        {
            return false;
        }
        return true;
    }
    //获取
    public int getNextPageNo()
    {
        if (isHasNext())
        {
            return getPageNo() +1;
        }
        return getPageNo();
    }
    public int getPrevPageNo()
    {
        if (isHasPrev())
        {
            return getPageNo() -1;
        }
        return getPageNo();
    }
}
