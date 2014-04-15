package de.greenrobot.daogenerator.config;

import org.jdom.Element;

import java.io.File;
import java.util.Iterator;


/**
 * Created by djzhang on 4/15/14.
 */
public class AndroidToObjectConfig extends IosConfig {
    public String srcRoot;
    public String srcModel;
    public String srcParam;

    public File androidModelFold;
    public File androidRequestParamFold;

    public AndroidToObjectConfig(File pathname) {
        super(pathname);
        
        this.androidModelFold = this.checkPath(this.srcModel);
        this.androidRequestParamFold = this.checkPath(this.srcParam);
    }

    private File checkPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        return new File(this.srcRoot, path);
    }


    public void listChildren(Element current, int depth) {
        Iterator iterator = current.getChildren().iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            String name = child.getName();
            if (name.equals("androidSrcRoot")) {
                this.srcRoot = child.getAttribute("value").getValue();
            }
            if (name.equals("androidModel")) {
                this.srcModel = child.getAttribute("value").getValue();
            }
            if (name.equals("androidParam")) {
                this.srcParam = child.getAttribute("value").getValue();
            }
            listChildren(child, depth + 1);
        }
    }


}
