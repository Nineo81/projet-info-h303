package model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.HashMap;

public abstract class XmlParserAnonymous extends XmlParser{

    private static final String ID = "ID";
    private static final String PASS = "password";
    private static final String BANK = "bankaccount";


    public HashMap<String, String> openFile(Node node) {
        Element element = (Element) node;
        HashMap<String, String> data = new HashMap<>();
        data.put(ID, getTagInfo(element, ID));
        data.put(PASS, getTagInfo(element, PASS));
        data.put(BANK, getTagInfo(element, BANK));
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
