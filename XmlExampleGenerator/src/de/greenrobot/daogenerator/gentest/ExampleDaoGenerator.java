/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobo.utils.StringUtils;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;

import de.greenrobot.daogenerator.PropertyType;
import de.greenrobot.daogenerator.xml.ElementLister;
import de.greenrobot.daogenerator.xml.ElementInfo;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * <p/>
 * Run it as a Java application (not Android).
 *
 * @author Markus
 */
public class ExampleDaoGenerator {

    //    private static String pathFold = "/Volumes/macshare/Home/SHARE/developing/svn/MobileWorkEnterprise/mobile/ios/MobileWorkGHUnit/trunk/MobileWorkProject/Tests/Resources/";
    private static String pathFold =
//            "/Volumes/macshare/Home/SHARE/developing/svn/IOSMobileWorkApps/cocoapodsApps/MobileWorkApps/trunk/MobileWorkApps/lib";
            "/Volumes/macshare/Home/djzhang/Desktop/buddy/xmobileXml";

    private static String libPath =
//            "mobilework_qingjiaguanli/Assets/test";
            "";

    private static String pathnames[] = {
            "LoginHelper.xml",
            "TravelList.xml", "TravelDetail.xml",
            "RepairList.xml", "RepairDetail.xml",
            "customerRepairList.data.xml",
            // 2014-04-10
            // qingjia(6)
            "getLeaveApplication.data.xml", "getLeaveApplicationById.data.xml",
            // jiaban(8)
            "", "",
    };
    private static final int xmlIndex = 7;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "");

        String[] ignorTags = {"script"};
        File xmlFold = new File(pathFold, libPath);

        ElementInfo elementInfo = new ElementLister(ignorTags).getXmlTags(
                new File(xmlFold, pathnames[xmlIndex]).getAbsolutePath());
        if (elementInfo != null) {
            generateNode(elementInfo, schema);
        }

        new DaoGenerator().generateAll(schema, "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen");
    }

    private static Entity generateNode(ElementInfo parentInfo, Schema schema) {
        Entity note = schema.addEntity(parentInfo.tagName);

        LinkedHashMap<Object, PropertyType> childHashMap = parentInfo.childHashMap;

        Set<Object> sets = childHashMap.keySet();
        Iterator iterator = sets.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            PropertyType value = childHashMap.get(key);
            if (value.equals(PropertyType.ByteArray)) {// to many as Array
                ElementInfo elementInfo = (ElementInfo) key;
                Entity childNote = generateNode(elementInfo, schema);
                note.addToMany(childNote, null);
            } else if (value.equals(PropertyType.Class)) {// to one as Class

                ElementInfo elementInfo = (ElementInfo) key;
                Entity childNote = generateNode(elementInfo, schema);

                note.addToOne(childNote, null);
            } else if (value.equals(PropertyType.String)) {  // to property as Variable
                LinkedHashMap<String, String> tagHashMap = (LinkedHashMap<String, String>) key;
                if (tagHashMap.size() == 1) {
                    Set<String> tagSets = tagHashMap.keySet();
                    Iterator keyIterator = tagSets.iterator();
                    while (keyIterator.hasNext()) {
                        String tagKey = (String) keyIterator.next();
                        String tagValue = (String) tagHashMap.get(tagKey);
                        note.addStringProperty(tagKey, tagValue);
                    }
                }

            }
        }
        return note;
    }


}
