package utils.page;

public class CriteriaRecords
{
    private int pageNo;  //当前页号
    private String keyWords;  //搜索关键字(userName|bookName)
    private int lineSize;  //共有几行
    private int columnSize;  //每行有多少个

    /**
     * 构造器
     */
    public CriteriaRecords(int pageNo, String keyWords,
                         int lineSize, int columnSize)
    {
        this.pageNo = pageNo;
        this.keyWords = keyWords;
        this.lineSize = lineSize;
        this.columnSize = columnSize;
    }

    /**
     * setters&getters
     */
    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getKeyWords()
    {
        return keyWords;
    }

    public void setKeyWords(String keyWords)
    {
        this.keyWords = keyWords;
    }

    public int getLineSize()
    {
        return lineSize;
    }

    public void setLineSize(int lineSize)
    {
        this.lineSize = lineSize;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public void setColumnSize(int columnSize)
    {
        this.columnSize = columnSize;
    }
}
