/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * This file is part of greenDAO Generator.
 * 
 * greenDAO Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * greenDAO Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with greenDAO Generator.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.greenrobot.daogenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The "root" model class to which you can add entities to.
 *
 * @see <a href="http://greendao-orm.com/documentation/modelling-entities/">Modelling Entities (Documentation page)</a>
 */
public class Schema {
    private final int version;
    private final String defaultJavaPackage;
    private String defaultJavaPackageDao;
    private String defaultJavaPackageTest;
    private final List<Entity> entities;

    private Map<PropertyType, String> propertyToDbType;
    private Map<PropertyType, String> propertyToJavaTypeNotNull;
    private Map<PropertyType, String> propertyToJavaTypeNullable;

    private Map<PropertyType, String> propertyTypeToDbType;
    private Map<PropertyType, String> propertyTypeToJavaTypeNotNull;
    private Map<PropertyType, String> propertyTypeToJavaTypeNullable;

    private boolean hasKeepSectionsByDefault;
    private boolean useActiveEntitiesByDefault;

    public Schema(int version, String defaultJavaPackage) {
        this.version = version;
        this.defaultJavaPackage = defaultJavaPackage;
        this.entities = new ArrayList<Entity>();
        initTypeMappings();
        initPrefixTypeMappings();
    }

    public void enableKeepSectionsByDefault() {
        hasKeepSectionsByDefault = true;
    }

    public void enableActiveEntitiesByDefault() {
        useActiveEntitiesByDefault = true;
    }

    private void initTypeMappings() {
        propertyToDbType = new HashMap<PropertyType, String>();
        propertyToDbType.put(PropertyType.Boolean, "INTEGER");
        propertyToDbType.put(PropertyType.Byte, "INTEGER");
        propertyToDbType.put(PropertyType.Short, "INTEGER");
        propertyToDbType.put(PropertyType.Int, "INTEGER");
        propertyToDbType.put(PropertyType.Long, "INTEGER");
        propertyToDbType.put(PropertyType.Float, "REAL");
        propertyToDbType.put(PropertyType.Double, "REAL");
        propertyToDbType.put(PropertyType.String, "TEXT");
        propertyToDbType.put(PropertyType.ByteArray, "BLOB");
        propertyToDbType.put(PropertyType.Date, "INTEGER");
        propertyToDbType.put(PropertyType.Class, "TEXT");

        propertyToJavaTypeNotNull = new HashMap<PropertyType, String>();
        propertyToJavaTypeNotNull.put(PropertyType.Boolean, "boolean");
        propertyToJavaTypeNotNull.put(PropertyType.Byte, "byte");
        propertyToJavaTypeNotNull.put(PropertyType.Short, "short");
        propertyToJavaTypeNotNull.put(PropertyType.Int, "int");
        propertyToJavaTypeNotNull.put(PropertyType.Long, "long");
        propertyToJavaTypeNotNull.put(PropertyType.Float, "float");
        propertyToJavaTypeNotNull.put(PropertyType.Double, "double");
        propertyToJavaTypeNotNull.put(PropertyType.ByteArray, "byte[]");
        propertyToJavaTypeNotNull.put(PropertyType.Date, "java.util.Date");

        propertyToJavaTypeNotNull.put(PropertyType.String, "NSString");// djzhang
        propertyToJavaTypeNotNull.put(PropertyType.Class, "NSObject");// djzhang

        propertyToJavaTypeNullable = new HashMap<PropertyType, String>();
        propertyToJavaTypeNullable.put(PropertyType.Boolean, "Boolean");
        propertyToJavaTypeNullable.put(PropertyType.Byte, "Byte");
        propertyToJavaTypeNullable.put(PropertyType.Short, "Short");
        propertyToJavaTypeNullable.put(PropertyType.Int, "Integer");
        propertyToJavaTypeNullable.put(PropertyType.Long, "Long");
        propertyToJavaTypeNullable.put(PropertyType.Float, "Float");
        propertyToJavaTypeNullable.put(PropertyType.Double, "Double");
        propertyToJavaTypeNullable.put(PropertyType.ByteArray, "byte[]");
        propertyToJavaTypeNullable.put(PropertyType.Date, "java.util.Date");

        propertyToJavaTypeNullable.put(PropertyType.String, "NSString");// djzhang
        propertyToJavaTypeNullable.put(PropertyType.Class, "NSObject");// djzhang
    }


    private void initPrefixTypeMappings() {
        propertyTypeToDbType = new HashMap<PropertyType, String>();
        propertyTypeToDbType.put(PropertyType.Boolean, "INTEGER");
        propertyTypeToDbType.put(PropertyType.Byte, "INTEGER");
        propertyTypeToDbType.put(PropertyType.Short, "INTEGER");
        propertyTypeToDbType.put(PropertyType.Int, "INTEGER");
        propertyTypeToDbType.put(PropertyType.Long, "INTEGER");
        propertyTypeToDbType.put(PropertyType.Float, "REAL");
        propertyTypeToDbType.put(PropertyType.Double, "REAL");
        propertyTypeToDbType.put(PropertyType.String, "TEXT");
        propertyTypeToDbType.put(PropertyType.ByteArray, "BLOB");
        propertyTypeToDbType.put(PropertyType.Date, "INTEGER");

        propertyTypeToJavaTypeNotNull = new HashMap<PropertyType, String>();
        propertyTypeToJavaTypeNotNull.put(PropertyType.Boolean, "boolean");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Byte, "byte");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Short, "short");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Int, "int");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Long, "long");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Float, "float");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Double, "double");
        propertyTypeToJavaTypeNotNull.put(PropertyType.ByteArray, "byte[]");
        propertyTypeToJavaTypeNotNull.put(PropertyType.Date, "java.util.Date");

        propertyTypeToJavaTypeNotNull.put(PropertyType.String, "@property (nonatomic, copy)");// djzhang
        propertyTypeToJavaTypeNotNull.put(PropertyType.Class, "@property (nonatomic, assign)");// djzhang

        propertyTypeToJavaTypeNullable = new HashMap<PropertyType, String>();
        propertyTypeToJavaTypeNullable.put(PropertyType.Boolean, "Boolean");
        propertyTypeToJavaTypeNullable.put(PropertyType.Byte, "Byte");
        propertyTypeToJavaTypeNullable.put(PropertyType.Short, "Short");
        propertyTypeToJavaTypeNullable.put(PropertyType.Int, "Integer");
        propertyTypeToJavaTypeNullable.put(PropertyType.Long, "Long");
        propertyTypeToJavaTypeNullable.put(PropertyType.Float, "Float");
        propertyTypeToJavaTypeNullable.put(PropertyType.Double, "Double");
        propertyTypeToJavaTypeNullable.put(PropertyType.ByteArray, "byte[]");
        propertyTypeToJavaTypeNullable.put(PropertyType.Date, "java.util.Date");

        propertyTypeToJavaTypeNullable.put(PropertyType.String, "@property (nonatomic, copy)");// djzhang
        propertyTypeToJavaTypeNullable.put(PropertyType.Class, "@property (nonatomic, assign)");// djzhang
    }

    /**
     * Adds a new entity to the schema. There can be multiple entities per table, but only one may be the primary entity
     * per table to create table scripts, etc.
     */
    public Entity addEntity(String className) {
        Entity entity = new Entity(this, className);
        entities.add(entity);
        return entity;
    }

    /**
     * Adds a new protocol buffers entity to the schema. There can be multiple entities per table, but only one may be
     * the primary entity per table to create table scripts, etc.
     */
    public Entity addProtobufEntity(String className) {
        Entity entity = addEntity(className);
        entity.useProtobuf();
        return entity;
    }

    public String mapToDbType(PropertyType propertyType) {
        return mapType(propertyToDbType, propertyType);
    }

    public String mapToJavaTypePrefixNullable(PropertyType propertyType) {
        return mapType(propertyTypeToJavaTypeNullable, propertyType);
    }

    public String mapToJavaTypePrefixNotNull(PropertyType propertyType) {
        return mapType(propertyTypeToJavaTypeNotNull, propertyType);
    }

    public String mapToJavaTypeNullable(PropertyType propertyType) {
        return mapType(propertyToJavaTypeNullable, propertyType);
    }

    public String mapToJavaTypeNotNull(PropertyType propertyType) {
        return mapType(propertyToJavaTypeNotNull, propertyType);
    }

    private String mapType(Map<PropertyType, String> map, PropertyType propertyType) {
        String dbType = map.get(propertyType);
        if (dbType == null) {
            throw new IllegalStateException("No mapping for " + propertyType);
        }
        return dbType;
    }

    public int getVersion() {
        return version;
    }

    public String getDefaultJavaPackage() {
        return defaultJavaPackage;
    }

    public String getDefaultJavaPackageDao() {
        return defaultJavaPackageDao;
    }

    public void setDefaultJavaPackageDao(String defaultJavaPackageDao) {
        this.defaultJavaPackageDao = defaultJavaPackageDao;
    }

    public String getDefaultJavaPackageTest() {
        return defaultJavaPackageTest;
    }

    public void setDefaultJavaPackageTest(String defaultJavaPackageTest) {
        this.defaultJavaPackageTest = defaultJavaPackageTest;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public boolean isHasKeepSectionsByDefault() {
        return hasKeepSectionsByDefault;
    }

    public boolean isUseActiveEntitiesByDefault() {
        return useActiveEntitiesByDefault;
    }

    void init2ndPass() {
        if (defaultJavaPackageDao == null) {
            defaultJavaPackageDao = defaultJavaPackage;
        }
        if (defaultJavaPackageTest == null) {
            defaultJavaPackageTest = defaultJavaPackageDao;
        }
        for (Entity entity : entities) {
            entity.init2ndPass();
        }
    }

    void init3ndPass() {
        for (Entity entity : entities) {
            entity.init3ndPass();
        }
    }

}
