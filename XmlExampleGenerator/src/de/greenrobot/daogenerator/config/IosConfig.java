package de.greenrobot.daogenerator.config;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


/**
 * Created by djzhang on 4/15/14.
 */
public abstract class IosConfig {

    protected IosConfig(File pathname) {
        this(pathname.getAbsolutePath());
    }

    protected IosConfig(String pathname) {
        this.getConfig(pathname);
    }

    private void getConfig(String pathname) {
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
    }

    public abstract void listChildren(Element root, int i);

}
