package utils.page;

/**
 * 封装页号和搜索条件
 * 用来实例化Page
 */
public class CriteriaBooks
{
    private int pageNo;  //当前页号
    private String keyWords;  //搜索关键字(bookName|content)
    private String bookType;  //图书类别
    private int lineSize;  //共有几行
    private int columnSize;  //每行有多少个

    /**
     * 构造器
     */
    public CriteriaBooks(int pageNo, String keyWords,
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

    public String getBookType()
    {
        return bookType;
    }

    public void setBookType(String bookType)
    {
        this.bookType = bookType;
    }
}
