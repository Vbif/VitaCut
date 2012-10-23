/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ironvbif.vitacut.swing.imagechooser;

import javax.swing.JFileChooser;

/**
 *
 * @author vbif
 */
public class ImageChooser extends JFileChooser {
    
    private static final ImageFilter staticFilter = new ImageFilter();
    
    
    public ImageChooser() {
        this.addChoosableFileFilter(staticFilter);
        this.setAcceptAllFileFilterUsed(false);
        this.setAccessory(new ImagePreview(this));
    }
}
