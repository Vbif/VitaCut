/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ironvbif.vitacut;

import java.util.prefs.Preferences;

/**
 *
 * @author vbif
 */
public class DirectoryCache {
    private String openDir;
    private String saveDir;
    private Preferences pref;
    private static final String def = "";
    
    private DirectoryCache() {
        pref = Preferences.userRoot().node("VitaCut");
        openDir = pref.get("openDir", def);
        saveDir = pref.get("saveDir", def);
    }
    
    public String getOpenDir() {
        return openDir;
    }
    
    public String getSaveDir() {
        return saveDir;
    }
    
    public void setOpenDir(String str) {
        pref.put("openDir", openDir = str);
    }
    
    public void setSaveDir(String str) {
        pref.put("saveDir", saveDir = str);
    }
    
    private static DirectoryCache instance = new DirectoryCache();
    
    public static DirectoryCache getInstance() {
        return instance;
    }
}
