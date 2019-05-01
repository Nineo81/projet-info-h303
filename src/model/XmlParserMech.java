package model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.ArrayList;

public abstract class XmlParserMech extends XmlParser{
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

    public void openFile(Node node) {
        Element element = (Element) node;
        ArrayList<String> data = new ArrayList<>();
        data.add(getTagInfo(element, ID));
        data.add(getTagInfo(element, LAST));
        data.add(getTagInfo(element, FIRST));
        data.add(getTagInfo(element, PASS));
        data.add(getTagInfo(element, PHONE));
        data.add(getTagInfo(element, CITY));
        data.add(getTagInfo(element, CP));
        data.add(getTagInfo(element, STREET));
        data.add(getTagInfo(element, NUMBER));
        data.add(getTagInfo(element, HIRE));
        data.add(getTagInfo(element, BANK));

        Inject(data);

    }

    private String getTagInfo(Element element, String tag){
        String info = null;
        if (element.getElementsByTagName(tag).item(0) != null) {
            info = element.getElementsByTagName(tag).item(0).getTextContent();
        }
        return info;
    }
}
