package model.domain;

public class BookType
{
    private String bookType;  //主键 用字母表示 eg:w
    private String typeName;  //类型名称 eg:文学类

    /**
     * setters&getters
     */
    public String getBookType()
    {
        return bookType;
    }

    public void setBookType(String bookType)
    {
        this.bookType = bookType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
}
