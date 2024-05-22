package sdp.prac2task2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserHandler extends DefaultHandler {
    private List<JSONObject> jsonObjects = new ArrayList<>();
    private JSONObject currentObject;
    private List<String> fields;
    private boolean isElement;
    private String currentElement;

    public UserHandler(List<String> fields) {
        this.fields = fields;
    }

    public List<JSONObject> getJsonObjects() {
        return jsonObjects;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("yourElementTag".equals(qName)) {
            currentObject = new JSONObject();
        } else if (currentObject != null && fields.contains(qName)) {
            isElement = true;
            currentElement = qName;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (isElement && currentObject != null) {
            currentObject.put(currentElement, new String(ch, start, length));
            isElement = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("yourElementTag".equals(qName) && currentObject != null) {
            jsonObjects.add(currentObject);
            currentObject = null;
        }
    }
}
