package model;

import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public class XmlParser {
    public static final String LAST = "lastname";
    public static final String FIRST = "firstname";
    public static final String PASS = "password";
    public static final String PHONE = "phone";
    public static final String CITY = "city";
    public static final String CP = "cp";
    public static final String STREET = "street";
    public static final String NUMBER = "number";
    public static final String BANK = "bankaccount";
    private DocumentBuilder builder;

    /**
     * DOM Parser Initialisation
     */
    public XmlParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
        }
    }

    public ArrayList<RegisteredUser> parseRegisteredUsers(String xml) {
        ArrayList<RegisteredUser> registeredUsers = new ArrayList<>();
        try {
            Document doc = this.builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList items = doc.getElementsByTagName("item"); // This object contains all the articles we need
            if (items.item(0) == null) {                //Two ways to structure xml files, item and entry are both possible
                items = doc.getElementsByTagName("entry");
            }
            int itemCount = items.getLength();
            for (int i = 0; i < itemCount; i++) {
                registeredUsers.add(openFile(items.item(i)));
            }
        } catch (Exception  e ) {

        }
        return registeredUsers;
    }
    /**
     * Creation of a article object from the DOM parsing
     *
     * @param node
     * @return the filled article
     */
    public RegisteredUser openFile(Node node) {
        Element element = (Element) node;
        String lastname = getTagInfo(element, LAST);
        String firstname = getTagInfo(element, FIRST);
        String password = getTagInfo(element, PASS);
        String phone = getTagInfo(element, PHONE);
        String city = getTagInfo(element, CITY);
        String cp = getTagInfo(element, CP);
        String street = getTagInfo(element, STREET);
        String number = getTagInfo(element, NUMBER);
        String bankAccount = getTagInfo(element, BANK);

        RegisteredUser user = new RegisteredUser(lastname, firstname,password,phone,city,cp,street,number,bankAccount);
        return user;

    }

    /**
     *Extract image's info by tag
     *
     * @param element (the node casted as an Element)
     * @param tag
     * @return the info
     */
    private String getTagInfo(Element element, String tag){
        String info = null;
        if (element.getElementsByTagName(tag).item(0) != null) {
            info = element.getElementsByTagName(tag).item(0).getTextContent();
        }
        return info;
    }
}
