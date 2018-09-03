package utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class XMLUtils
{

    public static String getUserPicAdress()
    {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new File("E:\\Codes\\JAVA\\JavaEE\\LAM\\src\\imgAdress.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Element userPictures = root.element("userPictures");
        return userPictures.getText();
    }

    public static String getBookPicAdress()
    {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new File("E:\\Codes\\JAVA\\JavaEE\\LAM\\src\\imgAdress.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Element bookPictures = root.element("bookPictures");
        return bookPictures.getText();
    }
}
