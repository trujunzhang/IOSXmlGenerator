package de.greenrobot.daogenerator.xml;

import de.greenrobot.daogenerator.PropertyType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;


public class ElementIncludeProject {

    private String[] ignorTags;

    public ElementIncludeProject(String[] ignorTags) {
        this.ignorTags = ignorTags;
    }

    private static String pathname = "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen/example.xml";

    static LinkedHashMap<Element, LinkedHashMap<Object, PropertyType>> XM_LELEMET_LINKEDHASHMAP;

    public ElementInfo getXmlTags(String pathname) {
        SAXBuilder builder = new SAXBuilder();

        XM_LELEMET_LINKEDHASHMAP = new LinkedHashMap<Element, LinkedHashMap<Object, PropertyType>>();

        try {
            Document doc = builder.build("file://" + pathname);
            Element root = doc.getRootElement();

            LinkedHashMap<Object, PropertyType> childLinkedHashMap = new LinkedHashMap<Object, PropertyType>();
            ElementInfo elementInfo = new ElementInfo(root.getName(), childLinkedHashMap);

            XM_LELEMET_LINKEDHASHMAP.put(root, childLinkedHashMap);

            listChildren(root, 0);

            return elementInfo;
        } catch (JDOMException e) {
            System.out.println(pathname + " is not well-formed.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

        return null;
    }


    private void listChildren(Element current, int depth) {

        String tagName = current.getName();

        printSpaces(depth, getCurrentTagSize(current));
        System.out.println(tagName);

        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            int size = getCurrentTagSize(child);
            LinkedHashMap<Object, PropertyType> propertyTypeObjectLinkedHashMap = XM_LELEMET_LINKEDHASHMAP.get(current);
            if (size == 0) {
                String key = child.getName();
                if (isIgnorTages(key) == false) {
                    String value = child.getValue();
                    LinkedHashMap<String, String> tagHashMap = new LinkedHashMap<String, String>();
                    tagHashMap.put(key, value);
                    propertyTypeObjectLinkedHashMap.put(tagHashMap, PropertyType.String);
                    printSpaces(depth + 1, getCurrentTagSize(current));
                }
            } else if (isArrayClass(child)) {
                Element arrayChild = (Element) child.getChildren().get(0);

                String arrayTagName = getCustomTagArrayName(child);

                LinkedHashMap<Object, PropertyType> childLinkedHashMap = new LinkedHashMap<Object, PropertyType>();
                ElementInfo arrayElementInfo = new ElementInfo(arrayTagName, childLinkedHashMap);

                propertyTypeObjectLinkedHashMap.put(arrayElementInfo, PropertyType.ByteArray);
                XM_LELEMET_LINKEDHASHMAP.put(arrayChild, childLinkedHashMap);

                listChildren(arrayChild, depth + 1 + 1);

            } else {// sub class
                LinkedHashMap<Object, PropertyType> childLinkedHashMap = new LinkedHashMap<Object, PropertyType>();
                ElementInfo elementInfo = new ElementInfo(child.getName(), childLinkedHashMap);

                propertyTypeObjectLinkedHashMap.put(elementInfo, PropertyType.Class);

                XM_LELEMET_LINKEDHASHMAP.put(child, childLinkedHashMap);
                listChildren(child, depth + 1);
            }

        }
    }

    /**
     * get Customize tag.
     * like:
     * <com.xinma.xmobile.travel.model.TravelPlan>
     *
     * @param parent
     * @return <TravelPlan>
     */
    private String getCustomTagArrayName(Element parent) {
        String key = ((Element) parent.getChildren().get(0)).getName();
        int last = key.lastIndexOf('.');
        if (last != -1) {
            key = key.substring(last + 1);
        }
        return key;
    }

    private boolean isArrayClass(Element parent) {
        LinkedList<String> keys = new LinkedList<String>();
        Iterator iterator = parent.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            keys.add(child.getName());
        }

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            for (int j = 0; j < keys.size(); j++) {
                String keyJ = keys.get(j);
                if (key.equals(keyJ) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isIgnorTages(String key) {
        for (int i = 0; i < ignorTags.length; i++) {
            if (ignorTags[i].equals(key)) {
                return true;
            }
        }
        return false;
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
