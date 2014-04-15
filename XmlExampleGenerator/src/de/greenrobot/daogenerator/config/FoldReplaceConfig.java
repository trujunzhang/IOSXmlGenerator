package de.greenrobot.daogenerator.config;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class FoldReplaceConfig {

    public final LinkedHashMap<String, String> replaceHashMap;
    public String foldPath;

    public FoldReplaceConfig() {
        this.replaceHashMap = new LinkedHashMap<String, String>();
    }

    public LinkedHashMap<String, String> getConfig(String pathname) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document doc = builder.build("file://" + pathname);
            Element root = doc.getRootElement();

            listChildren(root, 0);
        } catch (JDOMException e) {
            System.out.println(pathname + " is not well-formed.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

        return this.replaceHashMap;
    }


    private void listChildren(Element current, int depth) {
        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            if (name.equals("match")) {
                this.parseConfig(child);
                break;
            }
            if (name.equals("path")) {
                this.foldPath = child.getValue();
            }
            listChildren(child, depth + 1);
        }
    }

    private void parseConfig(Element element) {
        Iterator iterator = element.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            if (name.equals("replace")) {
                String source = child.getAttribute("source").getValue();
                String dest = child.getAttribute("dest").getValue();
                this.replaceHashMap.put(source, dest);
            }
        }
    }
}
