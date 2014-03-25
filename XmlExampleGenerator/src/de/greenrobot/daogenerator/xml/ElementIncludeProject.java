package de.greenrobot.daogenerator.xml;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.*;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sap.prd.mobile.ios.mios.xcodeprojreader.jaxb.JAXBPlistParser;
import junit.framework.Assert;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sap.prd.mobile.ios.mios.xcodeprojreader.Array;
import com.sap.prd.mobile.ios.mios.xcodeprojreader.Dict;
import com.sap.prd.mobile.ios.mios.xcodeprojreader.Plist;

public class ElementIncludeProject {

    private String[] ignorTags;

    public ElementIncludeProject(String[] ignorTags) {
        this.ignorTags = ignorTags;
    }

    public ElementInfo getXmlTags(String pathname) {
        ElementInfo elementInfo = null;

        JAXBPlistParser parser = new JAXBPlistParser();
        Plist plist = null;
        try {
            plist = parser.load(pathname);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        if (plist == null)
            return null;

        Dict dict = plist.getDict();


        return elementInfo;
    }


}
