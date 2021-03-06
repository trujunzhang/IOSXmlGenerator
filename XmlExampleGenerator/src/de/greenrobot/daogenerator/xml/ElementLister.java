package de.greenrobot.daogenerator.xml;

import de.greenrobo.utils.StringUtils;
import org.jdom.*;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedList;

import de.greenrobot.daogenerator.PropertyType;


public class ElementLister {

    private LinkedList<String> ignorTags;

    public ElementLister(LinkedList<String> ignorTags) {
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
        // 1. list infomation.
        printSpaces(depth, getCurrentTagSize(current));
        System.out.println(current.getName());

        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            int size = getCurrentTagSize(child);
            LinkedHashMap<Object, PropertyType> propertyTypeObjectLinkedHashMap = XM_LELEMET_LINKEDHASHMAP.get(current);
            String childTagName = child.getName();
            if (size == 0) {// single
                if (isIgnorTages(childTagName) == false) {
                    String value = child.getValue();
                    LinkedHashMap<String, String> tagHashMap = new LinkedHashMap<String, String>();
                    tagHashMap.put(childTagName, value);
                    propertyTypeObjectLinkedHashMap.put(tagHashMap, PropertyType.String);
                    printSpaces(depth + 1, getCurrentTagSize(current));
                }
            } else if (isArrayClass(child)) {// multiple tags
                Element arrayChild = (Element) child.getChildren().get(0);

                String arrayTagName = getCustomTagArrayName(child);

                LinkedHashMap<Object, PropertyType> childLinkedHashMap = new LinkedHashMap<Object, PropertyType>();
                ElementInfo arrayElementInfo = new ElementInfo(arrayTagName, childLinkedHashMap);

                propertyTypeObjectLinkedHashMap.put(arrayElementInfo, PropertyType.ByteArray);
                XM_LELEMET_LINKEDHASHMAP.put(arrayChild, childLinkedHashMap);

                listChildren(arrayChild, depth + 1 + 1);

            } else {// sub class
                childTagName = this.getRealName(childTagName, child);
                LinkedHashMap<Object, PropertyType> childLinkedHashMap = new LinkedHashMap<Object, PropertyType>();
                ElementInfo elementInfo = new ElementInfo(childTagName, childLinkedHashMap);

                propertyTypeObjectLinkedHashMap.put(elementInfo, PropertyType.Class);

                XM_LELEMET_LINKEDHASHMAP.put(child, childLinkedHashMap);
                listChildren(child, depth + 1);
            }

        }
    }

    private String getRealName(String tagName, Element child) {
        if (tagName.equals("object")) {
            String aClass = child.getAttribute("class").getValue().replace(".", ",");
            String[] split = aClass.split(",");
            return split[split.length - 1];
        }
        return tagName;
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
        for (String tag : this.ignorTags) {
            if (tag.equals(key)) {
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
