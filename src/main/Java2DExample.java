package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Java2DExample extends JFrame {
	private JMenu filterMenu = new JMenu("Image Filters");

	private ImagePanel imagePanel;

	private MyFilter colorFilter = new ColorFilter();

	public Java2DExample() {
	    super("Java 2D Image Processing Demo");
	    
	    try {
			imagePanel = new ImagePanel(Paths.get("src/CAPCHAimg/icixnvcdvcq.jpg").toUri().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    JMenuBar menuBar = new JMenuBar();
	    setJMenuBar(menuBar);
	    filterMenu.setMnemonic('I');

	    JMenuItem originalMenuItem = new JMenuItem("Display Original");
	    originalMenuItem.setMnemonic('O');

	    originalMenuItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent action) {
	        imagePanel.displayOriginalImage();
	      }

	    });


	    
	    JMenuItem changeColorsMenuItem = createMenuItem("Change Colors", 'C', colorFilter);

	    filterMenu.add(originalMenuItem);
	    filterMenu.add(changeColorsMenuItem);

	    menuBar.add(filterMenu);

	    getContentPane().add(imagePanel, BorderLayout.CENTER);

	  }

	  public JMenuItem createMenuItem(String menuItemName, char mnemonic, final MyFilter filter) {
	    JMenuItem menuItem = new JMenuItem(menuItemName);
	    menuItem.setMnemonic(mnemonic);
	    menuItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent action) {
	        imagePanel.applyFilter(filter);
	      }

	    });
	    return menuItem;
	  }

	  public static void main(String args[]) {
	    Java2DExample application = new Java2DExample();
	    application.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    application.pack();
	    application.setVisible(true);
	  }
	}

	interface MyFilter {
	  public abstract BufferedImage processImage(BufferedImage image);
	}

	class ImagePanel extends JPanel {

	  private BufferedImage displayImage;

	  private BufferedImage originalImage;

	  private Image image;

	  public ImagePanel(URL imageURL) {
	    image = Toolkit.getDefaultToolkit().createImage(imageURL);
	    MediaTracker mediaTracker = new MediaTracker(this);
	    mediaTracker.addImage(image, 0);

	    try {
	      mediaTracker.waitForAll();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
	        BufferedImage.TYPE_INT_RGB);
	    displayImage = originalImage;
	    Graphics2D graphics = displayImage.createGraphics();
	    graphics.drawImage(image, null, null);

	  }

	  public void applyFilter(MyFilter filter) {
	    displayImage = filter.processImage(displayImage);
	    repaint();
	  }

	  public void displayOriginalImage() {
	    displayImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
	        BufferedImage.TYPE_INT_RGB);

	    Graphics2D graphics = displayImage.createGraphics();
	    graphics.drawImage(originalImage, null, null);
	    repaint();
	  }
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D graphics = (Graphics2D) g;
	    graphics.drawImage(displayImage, 0, 0, null);
	  }
	  public Dimension getPreferredSize() {
	    return new Dimension(displayImage.getWidth(), displayImage.getHeight());
	  }
	  public Dimension getMinimumSize() {
	    return getPreferredSize();
	  }
	}

	

	

	class ColorFilter implements MyFilter {
	  public BufferedImage processImage(BufferedImage image) {
	    float[][] colorMatrix = { { 1f, 0f, 0f }, { 0.5f, 1.0f, 0.5f }, { 0.2f, 0.4f, 0.6f } };
	    BandCombineOp changeColors = new BandCombineOp(colorMatrix, null);
	    Raster sourceRaster = image.getRaster();
	    WritableRaster displayRaster = sourceRaster.createCompatibleWritableRaster();
	    changeColors.filter(sourceRaster, displayRaster);
	    return new BufferedImage(image.getColorModel(), displayRaster, true, null);

	  }
}
