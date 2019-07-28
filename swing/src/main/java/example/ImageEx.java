package example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

// based on: http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Display_Image_with_Swing_GUI.htm
// see also: https://www.baeldung.com/java-images
public class ImageEx extends JFrame {

	public ImageEx() {

		this.setLayout(new FlowLayout());
		this.setSize(200, 200);
		JLabel jLabel = new JLabel();
		JImage jImage = new JImage();
		jImage.setImage("http://www.java2s.com/style/download.png");
		jLabel.setIcon(new ImageIcon(jImage.getImage()));
		this.add(jLabel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static class JImage extends JComponent {
		BufferedImage image;

		public JImage() {
		}

		@Override
		protected void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			graphics.drawImage(getImage(), getLocation().x, getLocation().y,
					getSize().width, getSize().height, null);
			// has no effect
			Graphics2D graphics2d = (Graphics2D) graphics;
			graphics2d.setStroke(new BasicStroke(10));
			graphics2d.setColor(Color.BLUE);
			graphics2d.drawRect(10, 10, getImage().getWidth() - 20,
					getImage().getHeight() - 20);

		}

		void setImage(String imageUrl) {
			try {
				// this.image = ImageIO.read(new File(imagePath));
				this.image = ImageIO.read(new URL(imageUrl));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		BufferedImage getImage() {
			return this.image;
		}
	}

	public static void main(String avg[]) throws Exception {
		ImageEx o = new ImageEx();
	}
}