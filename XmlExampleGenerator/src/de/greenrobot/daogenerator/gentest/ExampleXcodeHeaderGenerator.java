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

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.PropertyType;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.xml.ElementInfo;
import de.greenrobot.daogenerator.xml.ElementIncludeProject;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * <p/>
 * Run it as a Java application (not Android).
 *
 * @author Markus
 */
public class ExampleXcodeHeaderGenerator {

    private static String pathFold = "/Volumes/macshare/Home/djzhang/Desktop/androidUnit/" +
            //"project.pbxproj";
            "wanghao.txt";

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(3, "");

        String[] ignorTags = {"script"};
        ElementInfo elementInfo = new ElementIncludeProject(ignorTags).getXmlTags(
                new File(pathFold).getAbsolutePath());
        if (elementInfo != null) {
            generateNode(elementInfo, schema);
        }

//        new DaoGenerator().generateAll(schema, "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen");
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
