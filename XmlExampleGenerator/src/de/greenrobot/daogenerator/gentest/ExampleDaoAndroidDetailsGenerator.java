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
public class ExampleDaoAndroidDetailsGenerator {

    private static String pathFold =
//            "/Volumes/macshare/Home/SHARE/developing/svn/xmobile_sa/myandroid/jscs/res/layout";
            //            "/Volumes/macshare/Home/SHARE/developing/svn/xmobile_sa/jscs/app/src/main/res/layout";
            "/Volumes/macshare/Home/SHARE/developing/svn/xmobile_sa/android/移动外勤/xinmabaseall/res/layout";

    private static String pathnames[] = {
            "repair_detail.xml", "chucha_deltail.xml", "chuchashenqing.xml",
            // 2014-04-10(3)
            "qingjia_deltail.xml",
    };

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(3, "");

        final int xmlIndex = 3;
        LinkedHashMap<String, String> xmlTags =
                new ElementDetailsLister().getXmlTags(new File(pathFold, pathnames[xmlIndex]).getAbsolutePath());
        generateNode(xmlTags, schema);

        new DaoGenerator().generateDetailAll(schema, "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen");
    }

    private static Entity generateNode(LinkedHashMap<String, String> parentInfo, Schema schema) {
        Entity note = schema.addEntity("Details");
        Set<String> strings = parentInfo.keySet();
        Iterator iterator = strings.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = parentInfo.get(key);
            note.addStringProperty(key, value);
        }
        return note;
    }

}
