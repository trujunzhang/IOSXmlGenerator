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
import de.greenrobot.daogenerator.xml.ElementDetailsLister;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * <p/>
 * Run it as a Java application (not Android).
 *
 * @author Markus
 */
public class ExampleDaoDetailsGenerator {

    private static String pathFold = "/Volumes/macshare/Home/SHARE/developing/svn/xmobile_sa/jscs/app/src/main/res/layout";
    private static String pathnames[] = {
            "repair_detail.xml",
    };

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(3, "");

        LinkedHashMap<String, String> xmlTags = new ElementDetailsLister().getXmlTags(new File(pathFold, pathnames[0]).getAbsolutePath());
        generateNode(xmlTags, schema);

        new DaoGenerator().generateDetailAll(schema, "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen");
    }

    private static Entity generateNode(LinkedHashMap<String, String> parentInfo, Schema schema) {
        Entity note = schema.addEntity("Details");
        for (int i = 0; i < parentInfo.size(); i++) {
//            note.addStringProperty(i + "", parentInfo.get(i));
        }
        return note;
    }


}
