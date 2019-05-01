package model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.HashMap;

public abstract class XmlParserRegistered extends XmlParser{
    private static final String ID = "ID";
    private static final String LAST = "lastname";
    private static final String FIRST = "firstname";
    private static final String PASS = "password";
    private static final String PHONE = "phone";
    private static final String CITY = "city";
    private static final String CP = "cp";
    private static final String STREET = "street";
    private static final String NUMBER = "number";
    private static final String BANK = "bankaccount";

    public HashMap<String, String> openFile(Node node) {
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
        data.put(BANK,getTagInfo(element, BANK));

        return data;
    }

    private String getTagInfo(Element element, String tag){
        String info = null;
        if (element.getElementsByTagName(tag).item(0) != null) {
            info = element.getElementsByTagName(tag).item(0).getTextContent();
        }
        return info;
    }
}
