package de.greenrobot.daogenerator.config;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class FoldReplaceConfig extends IosConfig {

    public final LinkedHashMap<String, String> replaceHashMap = new LinkedHashMap<String, String>();
    public String replaceFoldPath;

    public FoldReplaceConfig(String pathname) {
        super(pathname);
    }


    public void listChildren(Element current, int depth) {
        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            if (name.equals("match")) {
                this.parseConfig(child);
                break;
            }
            if (name.equals("path")) {
                this.replaceFoldPath = child.getValue();
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
