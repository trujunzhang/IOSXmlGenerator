package de.greenrobot.daogenerator.xml;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


public class ElementDetailsLister {

    private final LinkedHashMap<String, String> textViewTitle;

    public ElementDetailsLister() {
        this.textViewTitle = new LinkedHashMap<String, String>();
    }

    public LinkedHashMap<String, String> getXmlTags(String pathname) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document doc = builder.build("file://" + pathname);
            Element root = doc.getRootElement();

            listChildren(root, 0, false);
        } catch (JDOMException e) {
            System.out.println(pathname + " is not well-formed.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

        return this.textViewTitle;
    }


    private void listChildren(Element current, int depth, boolean b) {
        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            boolean isTextView = name.equals("TableRow");
            if (b && name.equals("TextView")) {
                String titleID = null;
                try {
                    String value = getTitleByAttribute(child, "text");
                    titleID = getTitleID(current);
                    String key = titleID.replace("@+id/", "");
                    this.textViewTitle.put(key, value);
                } catch (Exception e) {
                    System.out.println("titleID = " + titleID);
                    e.printStackTrace();
                }
                break;
            }
            listChildren(child, depth + 1, isTextView);
        }
    }

    private String getTitleID(Element current) {
        List children = current.getChildren();
        Element idElement = (Element) children.get(1);
        String id = getTitleByAttribute(idElement, "id");
        if (id == null) {
            System.out.println("id = " + id);
        }
        return id;
    }

    private String getTitleByAttribute(Element child, String text) {
        List attributes = child.getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = (Attribute) attributes.get(i);
            if (attribute.getName().equals(text)) {
                return attribute.getValue();
            }
        }
        return null;
    }

    private static int getCurrentTagSize(Element children) {
        Iterator iterator = children.getChildren().iterator();
        int size = 0;
        while (iterator.hasNext()) {
            iterator.next();
            size++;
        }
        return size;
    }

    private static void printSpaces(int n, int size) {

        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }

    }

}
