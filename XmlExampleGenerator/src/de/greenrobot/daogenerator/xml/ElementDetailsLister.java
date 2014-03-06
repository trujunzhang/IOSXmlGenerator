package de.greenrobot.daogenerator.xml;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ElementDetailsLister {

    private final LinkedList<String> textViewTitle;

    public ElementDetailsLister() {
        this.textViewTitle = new LinkedList<String>();
    }

    public void getXmlTags(String pathname) {
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
    }


    private void listChildren(Element current, int depth, boolean b) {

        String tagName = current.getName();

//        printSpaces(depth, getCurrentTagSize(current));
//        System.out.println(tagName);

        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            boolean isTextView = name.equals("TableRow");
            if (b && name.equals("TextView")) {
                getTitleByAttribute(child);
//                Attribute attribute = child.getAttribute("android:text");
//                System.out.println("attribute = " + attribute);
                break;
            }
            listChildren(child, depth + 1, isTextView);
        }
    }

    private void getTitleByAttribute(Element child) {
        List attributes = child.getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = (Attribute) attributes.get(i);
            if (attribute.getName().equals("text")) {
                String value = attribute.getValue();
                System.out.println("value = " + value);
                this.textViewTitle.add(value);
            }
        }
        //((Attribute)((AttributeList)child.getAttributes()).)
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
