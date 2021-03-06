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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Once you have your model created, use this class to generate entities and DAOs.
 *
 * @author Markus
 */
public class DaoGenerator {

    private Pattern patternKeepIncludes;
    private Pattern patternKeepFields;
    private Pattern patternKeepMethods;

    private Template templateDao;
    private Template templateDaoMaster;
    private Template templateDaoSession;
    private Template templateEntityH;
    private Template templateEntityM;
    private Template templateEntityXML;
    private Template templateEntityUnitTest;
    private Template templateEntityDetail;
    private Template templateDaoUnitTest;
    private Template templateContentProvider;

    public DaoGenerator() throws IOException {
        System.out.println("greenDAO Generator");
        System.out.println("Copyright 2011-2013 Markus Junginger, greenrobot.de. Licensed under GPL V3.");
        System.out.println("This program comes with ABSOLUTELY NO WARRANTY");

        patternKeepIncludes = compilePattern("INCLUDES");
        patternKeepFields = compilePattern("FIELDS");
        patternKeepMethods = compilePattern("METHODS");

        Configuration config = new Configuration();
        config.setClassForTemplateLoading(this.getClass(), "/");
        config.setObjectWrapper(new DefaultObjectWrapper());

//        templateDao = config.getTemplate("dao.ftl");
//        templateDaoMaster = config.getTemplate("dao-master.ftl");
//        templateDaoSession = config.getTemplate("dao-session.ftl");

        templateEntityH = config.getTemplate("entityH.ftl");
        templateEntityM = config.getTemplate("entityM.ftl");
        templateEntityXML = config.getTemplate("entityXML.ftl");
        templateEntityUnitTest = config.getTemplate("entityUnitTest.ftl");
        templateEntityDetail = config.getTemplate("entityDetail.ftl");

//        templateDaoUnitTest = config.getTemplate("dao-unit-test.ftl");
//        templateContentProvider = config.getTemplate("content-provider.ftl");
    }

    private Pattern compilePattern(String sectionName) {
        int flags = Pattern.DOTALL | Pattern.MULTILINE;
        return Pattern.compile(".*^\\s*?//\\s*?KEEP " + sectionName + ".*?\n(.*?)^\\s*// KEEP " + sectionName
                + " END.*?\n", flags);
    }

    /**
     * Generates all entities and DAOs for the given schema.
     */
    public void generateAll(Schema schema, String outDir) throws Exception {
        if (new File(outDir).exists() == false) {
            new File(outDir).mkdir();
        }
        generateAll(schema, outDir, null);
    }

    public void generateDetailAll(Schema schema, String outDir) throws Exception {
        if (new File(outDir).exists() == false) {
            new File(outDir).mkdir();
        }
        generateDetailAll(schema, outDir, null);
    }

    /**
     * Generates all entities and DAOs for the given schema.
     */
    public void generateAll(Schema schema, String outDir, String outDirTest) throws Exception {
        long start = System.currentTimeMillis();

        File outDirFile = toFileForceExists(outDir);

        File outDirTestFile = null;
        if (outDirTest != null) {
            outDirTestFile = toFileForceExists(outDirTest);
        }

        schema.init2ndPass();
        schema.init3ndPass();

        System.out.println("Processing schema version " + schema.getVersion() + "...");

        List<Entity> entities = schema.getEntities();
        for (Entity entity : entities) {
//            generate(templateDao, outDirFile, entity.getJavaPackageDao(), entity.getClassNameDao(), schema, entity);
            if (!entity.isProtobuf() && !entity.isSkipGeneration()) {
                generate(templateEntityH, outDirFile, entity.getJavaPackage(), entity.getClassName(), schema, entity, "h");
                generate(templateEntityM, outDirFile, entity.getJavaPackage(), entity.getClassName(), schema, entity, "m");
                generate(templateEntityXML, outDirFile, entity.getJavaPackage(), entity.getClassName(), schema, entity, "parse");
                generate(templateEntityUnitTest, outDirFile, entity.getJavaPackage(), entity.getClassName(), schema, entity, "unittest");
            }
//            if (outDirTestFile != null && !entity.isSkipGenerationTest()) {
//                String javaPackageTest = entity.getJavaPackageTest();
//                String classNameTest = entity.getClassNameTest();
//                File javaFilename = toJavaFilename(outDirTestFile, javaPackageTest, classNameTest);
//                if (!javaFilename.exists()) {
//                    generate(templateDaoUnitTest, outDirTestFile, javaPackageTest, classNameTest, schema, entity);
//                } else {
//                    System.out.println("Skipped " + javaFilename.getCanonicalPath());
//                }
//            }
//            for (ContentProvider contentProvider : entity.getContentProviders()) {
//                Map<String, Object> additionalObjectsForTemplate = new HashMap<String, Object>();
//                additionalObjectsForTemplate.put("contentProvider", contentProvider);
//                generate(templateContentProvider, outDirFile, entity.getJavaPackage(), entity.getClassName()
//                        + "ContentProvider", schema, entity, additionalObjectsForTemplate);
//            }
        }
//        generate(templateDaoMaster, outDirFile, schema.getDefaultJavaPackageDao(), "DaoMaster", schema, null);
//        generate(templateDaoSession, outDirFile, schema.getDefaultJavaPackageDao(), "DaoSession", schema, null);

        long time = System.currentTimeMillis() - start;
        System.out.println("Processed " + entities.size() + " entities in " + time + "ms");
    }

    public void generateDetailAll(Schema schema, String outDir, String outDirTest) throws Exception {
        long start = System.currentTimeMillis();

        File outDirFile = toFileForceExists(outDir);

        File outDirTestFile = null;
        if (outDirTest != null) {
            outDirTestFile = toFileForceExists(outDirTest);
        }

        schema.init2ndPass();
        schema.init3ndPass();

        System.out.println("Processing schema version " + schema.getVersion() + "...");

        List<Entity> entities = schema.getEntities();
        for (Entity entity : entities) {
            if (!entity.isProtobuf() && !entity.isSkipGeneration()) {
                generate(templateEntityDetail, outDirFile, entity.getJavaPackage(), entity.getClassName(), schema, entity, "detail");
            }
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("Processed " + entities.size() + " entities in " + time + "ms");
    }

    protected File toFileForceExists(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IOException(filename
                    + " does not exist. This check is to prevent accidental file generation into a wrong path.");
        }
        return file;
    }

    private void generate(Template template, File outDirFile, String javaPackage, String javaClassName, Schema schema,
                          Entity entity, String extension) throws Exception {
        generate(template, outDirFile, javaPackage, javaClassName, schema, entity, null, extension);
    }

    private void generate(Template template, File outDirFile, String javaPackage, String javaClassName, Schema schema,
                          Entity entity, Map<String, Object> additionalObjectsForTemplate, String extension) throws Exception {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("schema", schema);
        root.put("entity", entity);
        if (additionalObjectsForTemplate != null) {
            root.putAll(additionalObjectsForTemplate);
        }
        try {
            File file = toJavaFilename(outDirFile, javaPackage, javaClassName, extension);
            file.getParentFile().mkdirs();

            if (entity != null && entity.getHasKeepSections()) {
                checkKeepSections(file, root);
            }

            Writer writer = new FileWriter(file);
            try {
                template.process(root, writer);
                writer.flush();
                System.out.println("Written " + file.getCanonicalPath());
            } finally {
                writer.close();
            }
        } catch (Exception ex) {
            System.err.println("Data map for template: " + root);
            System.err.println("Error while generating " + javaPackage + "." + javaClassName + " ("
                    + outDirFile.getCanonicalPath() + ")");
            throw ex;
        }
    }

    private void checkKeepSections(File file, Map<String, Object> root) {
        if (file.exists()) {
            try {
                String contents = new String(DaoUtil.readAllBytes(file));

                Matcher matcher;

                matcher = patternKeepIncludes.matcher(contents);
                if (matcher.matches()) {
                    root.put("keepIncludes", matcher.group(1));
                }

                matcher = patternKeepFields.matcher(contents);
                if (matcher.matches()) {
                    root.put("keepFields", matcher.group(1));
                }

                matcher = patternKeepMethods.matcher(contents);
                if (matcher.matches()) {
                    root.put("keepMethods", matcher.group(1));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected File toJavaFilename(File outDirFile, String javaPackage, String javaClassName, String extension) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + "." + extension);
        return file;
    }

}
