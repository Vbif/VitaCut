/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ironvbif.vitacut.swing;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.GrayFilter;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Администратор
 */
public class ImageCutter extends JPanel implements ComponentListener, ChangeListener {
    private Image image;
    private Image grayImage;
    private Dimension windowsSize;
    private float windowsAspect;
    
    private int width;
    private int spanWidth;
    private int height;
    private int spanHeight;
    private float needHeight;
        
    private int imgH;
    private int imgW;
           
    private JSlider verticalLine1;
    private JSlider verticalLine2;

    private JSlider horizontalLine1;
    private JSlider horizontalLine2;

    private int windowCount;

    private Rectangle[] windows;

    private double scale;

    public void setWindowCount(int windowCount) {
        if (windowCount == this.windowCount)
            return;
        this.windowCount = windowCount;
        windows = new Rectangle[windowCount];
                recalcWindows();
    }

    public int getWindowCount() {
        return windowCount;
    }
        
    public ImageCutter() {
        this.addComponentListener(this);
        
    }
    
    public Dimension getWindowsSize() {
        return windowsSize;
    }
    
    public void setDimensionSize(Dimension val) {
        windowsSize = val;
        windowsAspect = (float)val.width / (float)val.height;
    }
    
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
        this.grayImage = GrayFilter.createDisabledImage(image);
        recalc();
    }
    
    public void setSliders(JSlider ver1, JSlider ver2, JSlider hor1, JSlider hor2) {
        verticalLine1 = ver1;
        verticalLine2 = ver2;
        horizontalLine1 = hor1;
        horizontalLine2 = hor2;
        
        ver1.addChangeListener(this);
        ver2.addChangeListener(this);
        hor1.addChangeListener(this);
        hor2.addChangeListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(image != null){
            Graphics2D g2d = (Graphics2D) g;
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            //g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            
            g.drawImage(grayImage, 
                    spanWidth, spanHeight, spanWidth + width, spanHeight + height,
                    0, 0, imgW, imgH,null);
            
            Rectangle tmp = g.getClipBounds();
            
            g.setClip(spanWidth + verticalLine1.getValue(),
                    spanHeight + height - horizontalLine1.getValue(),
                    verticalLine2.getValue() - verticalLine1.getValue(),
                    horizontalLine1.getValue() - horizontalLine2.getValue());
            
            g.drawImage(image, 
                    spanWidth, spanHeight, spanWidth + width, spanHeight + height,
                    0, 0, imgW, imgH,null);
            
            g.setClip(tmp.x, tmp.y, tmp.width, tmp.height);
            
            g.drawLine(spanWidth + verticalLine1.getValue(), 0, spanWidth + verticalLine1.getValue(), height);
            g.drawLine(spanWidth + verticalLine2.getValue(), 0, spanWidth + verticalLine2.getValue(), height);
            
            g.drawLine(0, spanHeight + height - horizontalLine1.getValue(),
                    spanWidth * 2 + width, spanHeight + height - horizontalLine1.getValue());
            g.drawLine(0, spanHeight + height - horizontalLine2.getValue(), 
                    spanWidth * 2 + width, spanHeight + height - horizontalLine2.getValue());
            
            g.setColor(new Color(10, 20, 100, 80));
            g2d.setComposite(AlphaComposite.SrcOver);
            
            if (windows != null)
            for (Rectangle r : windows) 
                g.fillRoundRect(r.x, r.y, r.width, r.height, 20, 20);
                //g.fillRectRo(r.x, r.y, r.width, r.height);
                //g.drawRoundRect(r.x, r.y, r.width, r.height, 5, 5);
            
            g.setColor(Color.black);
            
            for (Rectangle r : windows) 
                g.drawRoundRect(r.x, r.y, r.width, r.height, 20, 20);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        } 
    }
    
    @Override
    public void setSize(Dimension val) {
        super.setSize(val);
        recalc();
        recalcWindows();
    }
    
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        recalc();
                recalcWindows();
    }
    
    private void recalc() {
        if (image == null)
            return;
        //System.out.println("recalc called");
        
        imgH = image.getHeight(null);
        imgW = image.getWidth(null);
        
        //System.out.println("pane width = " + getWidth());
        //System.out.println("pane height = " + getHeight());
        
        //System.out.println("image width = " + imgW);
        //System.out.println("image height = " + imgH);
        
        float panelAspect = (float)getWidth()/ getHeight();
        //System.out.println("panelAspect " + panelAspect);
        float imageAspect = (float)imgW / imgH;
       // System.out.println("imageAspect " + imageAspect);
     
        
        if (panelAspect > imageAspect) {
//            width = getWidth();
//            height = (int)(width / imageAspect);
            scale = (double)getHeight() / imgH;
            
        } else {
//            height = getHeight();
//            width = (int) (height * imageAspect);
            scale = (double)getWidth() / imgW;
        }
        
        width = (int) (imgW * scale);
        height = (int) (imgH * scale);
        
        //System.out.println("result width " + (width ));
        //System.out.println("result height " + ( height));

        //System.out.println("result ratio " + ((float)width / height));
        
        spanWidth = (getWidth() - width) / 2;
        spanHeight = (getHeight() - height) / 2;
        
        //System.out.println("result spanWidth " + (spanWidth ));
        //System.out.println("result spanHeight " + ( spanHeight));
        
        //
        verticalLine1.setMinimum(0);
        verticalLine1.setMaximum(width);
        verticalLine2.setMinimum(0);
        verticalLine2.setMaximum(width);
        
        verticalLine1.setValue(0);
        verticalLine2.setValue(width);
        
        horizontalLine1.setMinimum(0);
        horizontalLine1.setMaximum(height);
        horizontalLine2.setMinimum(0);
        horizontalLine2.setMaximum(height);
        
        horizontalLine1.setValue(height);
        horizontalLine2.setValue(0);
        
        
        this.repaint();
    }
    
    public void recalcWindows() {
        if (horizontalLine1 == null)
            return;
        int allHeight = horizontalLine1.getValue() - horizontalLine2.getValue();
        int allWidth = verticalLine2.getValue() - verticalLine1.getValue();
        
        needHeight = allWidth / windowsAspect;
        
        float sumHeight = windowCount * needHeight;
        
        float dx = Math.max((sumHeight - allHeight) / (windowCount - 1), 0);
        
        
        float currX = spanWidth + verticalLine1.getValue();
        float currY = spanHeight + height - horizontalLine1.getValue();
        
        for (int i = 0; i < windowCount; ++i)
        {
            windows[i] = new Rectangle((int)currX, (int)currY,
                    (int)(allWidth), (int)(needHeight) + 1);
            currY += (needHeight - dx);
       }
       //System.out.println("windfows recalc " + windowCount);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        recalc();
        recalcWindows();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        
        if (horizontalLine1.getValue() < needHeight)
            horizontalLine1.setValue((int)needHeight);
        if (horizontalLine1.getValue() - horizontalLine2.getValue() <= needHeight )
            horizontalLine2.setValue(horizontalLine1.getValue() - (int)needHeight);
        if (verticalLine1.getValue() >= verticalLine2.getValue())
            verticalLine2.setValue(verticalLine1.getValue() + 1);
        recalcWindows();
        repaint();
    }
    
    public BufferedImage[] cutImage() {
        BufferedImage[] ans = new BufferedImage[windowCount];
        
        double scale = 1 / this.scale;
        
        double allHeight = (horizontalLine1.getValue() - horizontalLine2.getValue()) * scale;
        double allWidth = (verticalLine2.getValue() - verticalLine1.getValue()) * scale;
        
        double needHeight = allWidth / windowsAspect;
        
        double sumHeight = windowCount * needHeight;
        
        double dx = Math.max((sumHeight - allHeight) / (windowCount - 1), 0);
        
        double currX = (verticalLine1.getValue() * scale);
        double currY = ((height - horizontalLine1.getValue()) * scale);
        
        //int width = (int)();
        
        //System.out.println("need ration = " + windowsAspect);
        //System.out.println("need ration = " + allWidth / needHeight);
        
        for (int i = 0; i < windowCount; ++i) {
            BufferedImage t = new BufferedImage(windowsSize.width, windowsSize.height, BufferedImage.TYPE_INT_RGB);
            
            Graphics g = t.getGraphics();

            g.drawImage(image, 0, 0, windowsSize.width, windowsSize.height, 
                    (int)currX, (int)currY, (int)(allWidth + currX), (int)(needHeight + currY), null);
            
            ans[i] = t;
            currY += (needHeight - dx);
        }
        return ans;
    }
}
