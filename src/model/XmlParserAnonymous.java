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

class XmlParserAnonymous{

    private static final String ID = "ID";
    private static final String PASS = "password";
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
        NodeList items = doc.getElementsByTagName("user"); // This object contains all the articles we need
        if (items.item(0) == null) {                //Two ways to structure xml files, item and entry are both possible
            items = doc.getElementsByTagName("user");
        }
        int itemCount = items.getLength();
        for (int i = 0; i < itemCount; i++) {
            allData.add(openFile(items.item(i)));
        }

        return allData;
    }

    private static HashMap<String, String> openFile(Node node) {
        Element element = (Element) node;
        HashMap<String, String> data = new HashMap<>();
        data.put(ID, getTagInfo(element, ID));
        data.put(PASS, getTagInfo(element, PASS));
        data.put(BANK, getTagInfo(element, BANK));
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
