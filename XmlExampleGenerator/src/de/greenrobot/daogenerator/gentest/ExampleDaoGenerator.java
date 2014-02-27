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
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.Collection;
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

    private static String pathname = "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen/example.xml";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "");

        ElementInfo elementInfo = ElementLister.getXmlTags(pathname);
        if (elementInfo != null) {
            generateNode(elementInfo, schema);
        }

        new DaoGenerator().generateAll(schema, "/Volumes/macshare/Home/SHARE/developing/wanghaogithub720/android/IOSXmlGenerator/src-gen");
    }

    private static void generateNode(ElementInfo parentInfo, Schema schema) {
        Entity note = schema.addEntity(parentInfo.tagName);

        LinkedHashMap<Object, PropertyType> childHashMap = parentInfo.childHashMap;

        Set<Object> sets = childHashMap.keySet();
        Iterator iterator = sets.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            PropertyType value = childHashMap.get(key);
            if (value.equals(PropertyType.Class)) {
                ElementInfo elementInfo = (ElementInfo) key;
                note.addClassProperty(elementInfo.tagName);
                generateNode(elementInfo, schema);
            } else if (value.equals(PropertyType.String)) {
                note.addStringProperty((String) key);
            }
        }
    }


    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
//        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addStringProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }

}
