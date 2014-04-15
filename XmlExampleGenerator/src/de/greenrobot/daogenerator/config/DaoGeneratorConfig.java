package de.greenrobot.daogenerator.config;

import de.greenrobot.daogenerator.utils.Tools;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Created by djzhang on 4/15/14.
 */
public class DaoGeneratorConfig extends IosConfig {

    private String xmlFold;

    /**
     * format:"getTravelApplicationById.data"
     * no contains xml
     */
    public String xmlName;

    public String xmlFile;

    public LinkedList<String> ignorTags = new LinkedList<String>();

    public java.lang.String outDir;

    public DaoGeneratorConfig(File pathname) {
        super(pathname);

        this.xmlFile = new File(this.xmlFold, String.format("%s.xml", this.xmlName)).getAbsolutePath();
        this.outDir = Tools.getDaoGeneratorFold(this.xmlName).getAbsolutePath();
    }


    public void listChildren(Element current, int depth) {
        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            if (name.equals("xmlFold")) {
                this.xmlFold = child.getAttribute("value").getValue();
            }
            if (name.equals("xmlName")) {
                getXmlName(child);
            }
            if (name.equals("ignorTages")) {
                this.getIgnorTages(child);
                break;
            }
            listChildren(child, depth + 1);
        }
    }

    private void getXmlName(Element child) {
        this.xmlName = child.getAttribute("value").getValue().replace(".xml", "");
    }

    private void getIgnorTages(Element element) {
        Iterator iterator = element.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            if (name.equals("tag")) {
                this.ignorTags.add(child.getValue());
            }
        }
    }


}
