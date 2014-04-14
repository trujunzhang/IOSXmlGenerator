package de.greenrobot.daogenerator.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by djzhang on 4/14/14.
 */
public class FoldReplaceUtils {

    public void replaceAll(File outPath, String fold, LinkedHashMap<String, String> replaceHashMap) {
        File[] list = new File(fold).listFiles();
        for (File file : list) {
            File out = new File(outPath, replaceByHashMap(file.getName(), replaceHashMap));
            replaceAndSave(out, file, replaceHashMap);
        }
    }

    private String replaceByHashMap(String data, LinkedHashMap<String, String> replaceHashMap) {
        Set<String> strings = replaceHashMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = replaceHashMap.get(key);
            data = data.replace(key, value);
        }
        return data;
    }

    private void replaceAndSave(File out, File file, LinkedHashMap<String, String> replaceHashMap) {
        String data = null;
        try {
            data = FileUtils.readFile(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data == null) {
            return;
        }

        data = replaceByHashMap(data, replaceHashMap);

        try {
//            FileUtils.writeFile(out.getAbsolutePath(),data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
