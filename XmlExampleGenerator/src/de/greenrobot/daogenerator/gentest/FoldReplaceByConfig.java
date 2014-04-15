package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.config.FoldReplaceConfig;
import de.greenrobot.daogenerator.utils.FileUtils;
import de.greenrobot.daogenerator.utils.FoldReplaceUtils;
import de.greenrobot.daogenerator.utils.Tools;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by djzhang on 4/14/14.
 */
public class FoldReplaceByConfig {

    public static void main(String[] args) {
        // 1. get config path
        File configPath = new File(Tools.getProfile(), "replace.xml");
        File outPath = new File(Tools.getProfile(), "replace_Out");
        check(outPath);

        // 2. get config data
        FoldReplaceConfig config = new FoldReplaceConfig(configPath.getAbsolutePath());

        // 3. replace all files in the fold.
        String fold = config.foldPath;
        LinkedHashMap<String, String> replaceHashMap = config.replaceHashMap;

        new FoldReplaceUtils().replaceAll(outPath, fold, replaceHashMap);
    }

    private static void check(File outPath) {
        if (outPath.exists()) {
            FileUtils.deleteFold(outPath);
        }
        createTemp(outPath);
    }

    private static void createTemp(File outPath) {
        if (outPath.exists() == false) {
            outPath.mkdir();
        }
    }

}
