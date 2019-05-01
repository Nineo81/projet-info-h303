package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public abstract class XmlParser {

    private DocumentBuilder builder;

    public void parse(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
        }
        try {
            Document doc = this.builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList items = doc.getElementsByTagName("item"); // This object contains all the articles we need
            if (items.item(0) == null) {                //Two ways to structure xml files, item and entry are both possible
                items = doc.getElementsByTagName("entry");
            }
            int itemCount = items.getLength();
            for (int i = 0; i < itemCount; i++) {
                openFile(items.item(i));
            }
        } catch (Exception  e ) {
        }
    }

    public void Inject(ArrayList<String> data){
        
    }

    public abstract void openFile(Node node);
}
