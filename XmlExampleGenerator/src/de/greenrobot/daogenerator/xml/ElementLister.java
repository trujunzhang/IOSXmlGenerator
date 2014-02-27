package de.greenrobot.daogenerator.xml;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class ElementLister {

    private static String pathname = "/Volumes/macshare/Home/djzhang/Downloads/freemarker-example-master/src/main/java/com/redhat/consulting/freemarker/in.xml";

    public static void main(String[] args1) {

        SAXBuilder builder = new SAXBuilder();

        String arg = "file://" + pathname;

        try {
            Document doc = builder.build(arg);
            Element root = doc.getRootElement();
            listChildren(root, 0);
        }
        // indicates a well-formedness error
        catch (JDOMException e) {
            System.out.println(arg + " is not well-formed.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

    }


    public static void listChildren(Element current, int depth) {

        printSpaces(depth);
        System.out.println(current.getName());
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            listChildren(child, depth + 1);
        }

    }

    private static void printSpaces(int n) {

        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }

    }

}
