package de.greenrobot.daogenerator.xml;

import java.util.HashMap;

import de.greenrobot.daogenerator.PropertyType;

/**
 * Created by djzhang on 2/27/14.
 */
public class ElementInfo {

    String tagName;

    public ElementInfo(String tagName, HashMap< Object,PropertyType> childHashMap) {
        this.tagName = tagName;
        this.childHashMap = childHashMap;
    }

    HashMap< Object,PropertyType> childHashMap;


}
