package model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.ArrayList;

public abstract class XmlParserAnonymous extends XmlParser{

    public static final String ID = "ID";
    public static final String PASS = "password";
    public static final String BANK = "bankaccount";


    public void openFile(Node node) {
        Element element = (Element) node;
        ArrayList<String> data = new ArrayList<>();
        data.add(getTagInfo(element, ID));
        data.add(getTagInfo(element, PASS));
        data.add(getTagInfo(element, BANK));

        Inject(data);

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
