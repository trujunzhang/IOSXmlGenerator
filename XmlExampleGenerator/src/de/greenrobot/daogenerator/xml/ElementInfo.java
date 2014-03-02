package de.greenrobot.daogenerator.xml;

import java.util.LinkedHashMap;

import de.greenrobo.utils.StringUtils;
import de.greenrobot.daogenerator.PropertyType;

/**
 * Created by djzhang on 2/27/14.
 */
public class ElementInfo {

    public String tagName;
    public LinkedHashMap<Object, PropertyType> childHashMap;


    public ElementInfo(String tagName, LinkedHashMap<Object, PropertyType> childHashMap) {
        tagName = StringUtils.convertFirstChatToUpper(tagName);
        this.tagName = tagName;
        this.childHashMap = childHashMap;
    }


}
