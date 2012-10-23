/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ironvbif.vitacut.swing.imagechooser;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author vbif
 */
public class ImageFilter extends FileFilter {
    
    private static final String filterName = "Images";
    private static final String emptyString = "";
    
    private static final String[] supportedExtArr = {"jpg", "png", "jpeg", "bmp", "tiff", "tif"};
    private static final HashSet<String> supportedExt = new HashSet<>(Arrays.asList(supportedExtArr));

    private static final String getExt(String str) {
        int pos = str.lastIndexOf('.');
        if (pos > 0)
            return str.substring(pos + 1).toLowerCase();
        else return emptyString;
        
    }
    
    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        String ext = getExt(f.getName());
        if (ext == emptyString)
            return false;
        return supportedExt.contains(ext);
    }

    @Override
    public String getDescription() {
        return filterName;
    }
    
}
