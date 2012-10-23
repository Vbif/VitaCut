/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ironvbif.vitacut;

import ironvbif.vitacut.swing.MainFrame;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

/**
 *
 * @author vbif
 */
public class VitaCutApplet extends JApplet{
    @Override
    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
            public void run() {
                    //MainFrame.main(null);
                    new MainFrame().setVisible(true);
            }
        }); 
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(VitaCutApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
