package example;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// based on https://github.com/chabala/example-swing-gui, re-pusposed
// http://www.java2s.com/example/java/2d-graphics/resize-icon.html

public class SampleGui {

	private static JFrame frame;
	private Icon warnIcon;
	private Graphics2D g2;
	private BufferedImage resizedImg;
	private BufferedImage image;

	private int width;
	private int height;

	private int width2;
	private int height2;
	private final String text = "Click Me";
	private final String imagePath = "./src/main/resources/images/click_me.png"; // "./src/main/resources/images/stop.gif";

	public static void main(String[] args) {
		new SampleGui();
	}

	public SampleGui() {
		SwingUtilities.invokeLater(this::showFrame);
	}

	private void showFrame() {
		frame = new JFrame("Resizable");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.setLayout(new FlowLayout());
		try {

			final int newHeight = 30;
			final int newWidth = 76;
			resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
			g2 = resizedImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			image = ImageIO.read(new File(imagePath));
			g2.drawImage(image, 0, 0, newWidth, newHeight, null);
			warnIcon = new ImageIcon(resizedImg);
			g2.dispose();

		} catch (IOException e) {
			warnIcon = new ImageIcon(imagePath);
		}
		JButton button2 = new JButton(text, warnIcon);
		button2.setIcon(warnIcon);
		button2.setPreferredSize(new Dimension(160, 80));

		button2.addActionListener((e) -> {
			JPasswordModalDialog dialog2 = new JPasswordModalDialog(frame, "Modal Dialog", "Enter your credentials");
			System.err.println("Done");
		});
		button2.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				Dimension newDimension = event.getComponent().getSize();
				System.out.println("Button to resize to:" + newDimension);
				try {

					final int newWidth = (int) (Math.ceil(newDimension.getWidth() * .75));
					final int newHeight = (int) (Math.ceil(newWidth * 158 / 381));
					System.out.println(String.format("Icon to resize to: %d,%d", newWidth, newHeight));
					resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
					g2 = resizedImg.createGraphics();
					g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					image = ImageIO.read(new File(imagePath));
					g2.drawImage(image, 0, 0, newWidth, newHeight, null);
					warnIcon = new ImageIcon(resizedImg);
					g2.dispose();

				} catch (IOException e) {
					warnIcon = new ImageIcon(imagePath);
				}
				button2.setIcon(warnIcon);

			}

			@Override
			public void componentMoved(ComponentEvent event) {
				// System.out.println("button moved to: " +
				// event.getComponent().getLocation());
			}
		});
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				Dimension newDimension = event.getComponent().getSize();
				System.out.println("Frame resized to: " + newDimension);
				final Dimension newDimension2 = new Dimension(
						(int) Math.ceil(newDimension.getHeight() * height2 / height),
						(int) Math.ceil(newDimension.getWidth() * width2 / width));
				System.out.println("resize the button to: " + newDimension2);
				button2.setPreferredSize(newDimension2);
				button2.setSize(newDimension2);
			}

			@Override
			public void componentMoved(ComponentEvent event) {
				// System.out.println("frame moved to: " +
				// event.getComponent().getLocation());
			}
		});

		// http://www.java2s.com/Tutorial/Java/0240__Swing/ArrowButton.htm
		frame.add(button2);
		frame.pack();
		frame.setVisible(true);
		height2 = button2.getHeight();
		width2 = button2.getWidth();
		System.out.println("initial button size: " + width2 + "," + height2);
		height = frame.getHeight();
		width = frame.getWidth();
		System.out.println("initial frame size: " + width + "," + height);
	}
}
