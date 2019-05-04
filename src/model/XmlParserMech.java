package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class XmlParserMech{
    private static final String ID = "mechanicID";
    private static final String LAST = "lastname";
    private static final String FIRST = "firstname";
    private static final String PASS = "password";
    private static final String PHONE = "phone";
    private static final String CITY = "city";
    private static final String CP = "cp";
    private static final String STREET = "street";
    private static final String NUMBER = "number";
    private static final String HIRE = "hireDate";
    private static final String BANK = "bankaccount";
    private static DocumentBuilder builder;
    public static ArrayList<HashMap<String,String>> parse(String xml) {
        ArrayList<HashMap<String,String>> allData = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(xml);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        NodeList items = doc.getElementsByTagName("mechanic"); // This object contains all the articles we need
        if (items.item(0) == null) {                //Two ways to structure xml files, item and entry are both possible
            items = doc.getElementsByTagName("mechanic");
        }
        int itemCount = items.getLength();
        for (int i = 0; i < itemCount; i++) {
            allData.add(openFile(items.item(i)));
        }

        return allData;
    }

    public static HashMap<String, String> openFile(Node node) {
        Element element = (Element) node;
        HashMap<String, String> data = new HashMap<>();
        data.put(ID, getTagInfo(element, ID));
        data.put(LAST,getTagInfo(element, LAST));
        data.put(FIRST,getTagInfo(element, FIRST));
        data.put(PASS,getTagInfo(element, PASS));
        data.put(PHONE,getTagInfo(element, PHONE));
        data.put(CITY,getTagInfo(element, CITY));
        data.put(CP,getTagInfo(element, CP));
        data.put(STREET,getTagInfo(element, STREET));
        data.put(NUMBER,getTagInfo(element, NUMBER));
        data.put(HIRE,getTagInfo(element, HIRE));
        data.put(BANK,getTagInfo(element, BANK));
        return data;

    }

    private static String getTagInfo(Element element, String tag){
        String info = null;
        if (element.getElementsByTagName(tag).item(0) != null) {
            info = element.getElementsByTagName(tag).item(0).getTextContent();
        }
        return info;
    }
}