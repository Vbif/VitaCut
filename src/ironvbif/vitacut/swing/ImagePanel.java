/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ironvbif.vitacut.swing;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author vbif
 */
public class ImagePanel extends JPanel {

    private Image image;

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null)
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
    
}
