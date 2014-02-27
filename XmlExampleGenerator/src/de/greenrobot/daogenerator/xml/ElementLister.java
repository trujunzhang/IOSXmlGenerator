package de.greenrobot.daogenerator.xml;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import de.greenrobot.daogenerator.PropertyType;


public class ElementLister {

    private static String pathname = "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen/example.xml";

    public static void main(String[] args1) {
        HashMap<PropertyType, Object> tagHashMap = getXmlTags(pathname);
    }

    private static HashMap<PropertyType, Object> getXmlTags(String pathname) {
        SAXBuilder builder = new SAXBuilder();

        HashMap<PropertyType, Object> rootHashMap = new LinkedHashMap<PropertyType, Object>();


        try {
            Document doc = builder.build("file://" + pathname);
            Element root = doc.getRootElement();

            HashMap<PropertyType, Object> childHashMap = new LinkedHashMap<PropertyType, Object>();
            ElementInfo elementInfo = new ElementInfo(root.getName(), childHashMap);
            rootHashMap.put(PropertyType.Class, elementInfo);

            listChildren(root, 0, childHashMap);
        } catch (JDOMException e) {
            System.out.println(pathname + " is not well-formed.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

        return rootHashMap;
    }


    public static void listChildren(Element current, int depth, HashMap<PropertyType, Object> parentHashMap) {
        String tagName = current.getName();


        printSpaces(depth, getCurrentTagSize(current));
        System.out.println(tagName);

        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            int size = getCurrentTagSize(child);
            if (size == 0) {
                parentHashMap.put(PropertyType.String, child.getName());
            } else {
                HashMap<PropertyType, Object> childHashMap = new LinkedHashMap<PropertyType, Object>();
                ElementInfo elementInfo = new ElementInfo(child.getName(), childHashMap);
                parentHashMap.put(PropertyType.Class, elementInfo);

                listChildren(child, depth + 1, childHashMap);
            }

        }

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
