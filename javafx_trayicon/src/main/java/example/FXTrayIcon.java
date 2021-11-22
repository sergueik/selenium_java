package example;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import example.annotations.API;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class FXTrayIcon {

	@API
	public FXTrayIcon(Stage parentStage, URL iconImagePath, int iconWidth,
			int iconHeight) {
		this(iconImagePath, iconWidth, iconHeight, parentStage);
	}

	@API
	public FXTrayIcon(Stage parentStage, URL iconImagePath) {
		this(iconImagePath, 16, 16, parentStage);
	}

	private FXTrayIcon(URL iconImagePath, int iconWidth, int iconHeight,
			Stage parentStage) {
		if (!SystemTray.isSupported()) {
			throw new UnsupportedOperationException("SystemTray icons are not "
					+ "supported by the current desktop environment.");
		} else {
			isMac = System.getProperty("os.name").toLowerCase(Locale.ENGLISH)
					.contains("mac");

			tray = SystemTray.getSystemTray();
			// Keeps the JVM running even if there are no
			// visible JavaFX Stages, otherwise JVM would
			// exit and we lose the TrayIcon
			Platform.setImplicitExit(false);

			// Set the SystemLookAndFeel as default,
			// let user override if needed
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException ignored) {
			}

			try {
				final Image iconImage = ImageIO.read(iconImagePath)
						// Some OSes do not behave well
						// if the icon is larger than 16x16
						.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
				this.parentStage = parentStage;
				this.trayIcon = new TrayIcon(iconImage, parentStage.getTitle(),
						popupMenu);
			} catch (IOException e) {
				throw new IllegalStateException(
						"Unable to read the Image at the provided path.");
			}
		}
	}

	@API
	public void show() {
		SwingUtilities.invokeLater(() -> {
			try {
				tray.add(this.trayIcon);

				// Add a MenuItem with the main Stage's title, this will
				// show the main JavaFX stage when clicked.
				if (addTitleMenuItem) {
					String miTitle = (this.appTitle != null) ? this.appTitle
							: (parentStage != null && parentStage.getTitle() != null
									&& !parentStage.getTitle().isEmpty()) ? parentStage.getTitle()
											: "Show Application";

					MenuItem miStage = new MenuItem(miTitle);
					miStage.setFont(Font.decode(null).deriveFont(Font.BOLD));
					miStage.addActionListener(e -> Platform.runLater(() -> {
						if (parentStage != null) {
							parentStage.show();
						}
					}));
					// Make sure it's always at the top
					this.popupMenu.insert(miStage, 0);
				}

				// If Platform.setImplicitExit(false) then the JVM will
				// continue to run after no more Stages remain,
				// thus we provide a way to terminate it by default.
				if (addExitMenuItem) {
					MenuItem miExit = new MenuItem("Exit Application");
					miExit.addActionListener(e -> {
						this.tray.remove(this.trayIcon);
						Platform.exit();
					});
					this.popupMenu.add(miExit);
				}

				// Show parent stage when user clicks the icon
				this.trayIcon.addActionListener(stageShowListener);
			} catch (AWTException e) {
				throw new IllegalStateException("Unable to add TrayIcon", e);
			}
		});
	}

	@API
	public void setOnAction(EventHandler<ActionEvent> e) {
		if (this.trayIcon.getMouseListeners().length >= 1) {
			this.trayIcon.removeMouseListener(this.trayIcon.getMouseListeners()[0]);
		}
		this.trayIcon.addMouseListener(getPrimaryClickListener(e));
	}

	@API
	public void addExitItem(boolean addExitMenuItem) {
		this.addExitMenuItem = addExitMenuItem;
	}

	@API
	public void addTitleItem(boolean addTitleMenuItem) {
		this.addTitleMenuItem = addTitleMenuItem;
	}

	@API
	public void removeMenuItem(int index) {
		EventQueue.invokeLater(() -> this.popupMenu.remove(index));
	}

	@API
	public void removeMenuItem(javafx.scene.control.MenuItem fxMenuItem) {
		EventQueue.invokeLater(() -> {
			MenuItem toBeRemoved = null;
			for (int i = 0; i < this.popupMenu.getItemCount(); i++) {
				MenuItem awtItem = this.popupMenu.getItem(i);
				if (awtItem.getLabel().equals(fxMenuItem.getText())
						|| awtItem.getName().equals(fxMenuItem.getText())) {
					toBeRemoved = awtItem;
				}
			}
			if (toBeRemoved != null) {
				this.popupMenu.remove(toBeRemoved);
			}
		});
	}

	@API
	public void addSeparator() {
		EventQueue.invokeLater(this.popupMenu::addSeparator);
	}

	@API
	public void insertSeparator(int index) {
		EventQueue.invokeLater(() -> this.popupMenu.insertSeparator(index));
	}

	@API
	public void addMenuItem(javafx.scene.control.MenuItem menuItem) {
		EventQueue.invokeLater(() -> {
			if (menuItem instanceof Menu) {
				addMenu((Menu) menuItem);
				return;
			}
			if (isNotUnique(menuItem)) {
				throw new UnsupportedOperationException(
						"Menu Item labels must be unique.");
			}
			this.popupMenu.add(AWTUtils.convertFromJavaFX(menuItem));
		});
	}

	@API
	public void insertMenuItem(javafx.scene.control.MenuItem menuItem,
			int index) {
		EventQueue.invokeLater(() -> {
			if (isNotUnique(menuItem)) {
				throw new UnsupportedOperationException(
						"Menu Item labels must be unique.");
			}
			this.popupMenu.insert(AWTUtils.convertFromJavaFX(menuItem), index);
		});
	}

	@API
	public MenuItem getMenuItem(int index) {
		return this.popupMenu.getItem(index);
	}

	@API
	public void setTrayIconTooltip(String tooltip) {
		EventQueue.invokeLater(() -> this.trayIcon.setToolTip(tooltip));
	}

	@API
	public void setApplicationTitle(String title) {
		this.appTitle = title;
	}

	@API
	public void hide() {
		EventQueue.invokeLater(() -> {
			tray.remove(trayIcon);
			Platform.setImplicitExit(true);
		});
	}

	@API
	public boolean isMenuShowing() {
		for (Iterator<TrayIcon> it = Arrays.stream(tray.getTrayIcons())
				.iterator(); it.hasNext();) {
			TrayIcon ti = it.next();
			if (ti.equals(trayIcon)) {
				return ti.getPopupMenu().isEnabled();
			}
		}
		return false;
	}

	@API
	public boolean isShowing() {
		return Arrays.stream(tray.getTrayIcons()).collect(Collectors.toList())
				.contains(trayIcon);
	}

	@API
	public void showInfoMessage(String title, String message) {
		if (isMac) {
			showMacAlert(title, message, "Information");
		} else {
			EventQueue.invokeLater(() -> this.trayIcon.displayMessage(title, message,
					TrayIcon.MessageType.INFO));
		}
	}

	@API
	public void showInfoMessage(String message) {
		this.showInfoMessage(null, message);
	}

	@API
	public void showWarningMessage(String title, String message) {
		if (isMac) {
			showMacAlert(title, message, "Warning");
		} else {
			EventQueue.invokeLater(() -> this.trayIcon.displayMessage(title, message,
					TrayIcon.MessageType.WARNING));
		}
	}

	@API
	public void showWarningMessage(String message) {
		this.showWarningMessage(null, message);
	}

	@API
	public void showErrorMessage(String title, String message) {
		if (isMac) {
			showMacAlert(title, message, "Error");
		} else {
			EventQueue.invokeLater(() -> this.trayIcon.displayMessage(title, message,
					TrayIcon.MessageType.ERROR));
		}
	}

	@API
	public void showErrorMessage(String message) {
		this.showErrorMessage(null, message);
	}

	@API
	public void showMessage(String title, String message) {
		if (isMac) {
			showMacAlert(title, message, "Message");
		} else {
			EventQueue.invokeLater(() -> this.trayIcon.displayMessage(title, message,
					TrayIcon.MessageType.NONE));
		}
	}

	@API
	public void showMessage(String message) {
		this.showMessage(null, message);
	}

	@API
	public void clear() {
		EventQueue.invokeLater(this.popupMenu::removeAll);
	}

	@API
	public static boolean isSupported() {
		return Desktop.isDesktopSupported() && SystemTray.isSupported();
	}

	@API
	public int getMenuItemCount() {
		return this.popupMenu.getItemCount();
	}

	private void showMacAlert(String subTitle, String message, String title) {
		String execute = String.format(
				"display notification \"%s\"" + " with title \"%s\""
						+ " subtitle \"%s\"",
				message != null ? message : "", title != null ? title : "",
				subTitle != null ? subTitle : "");

		try {
			Runtime.getRuntime().exec(new String[] { "osascript", "-e", execute });
		} catch (IOException e) {
			throw new UnsupportedOperationException(
					"Cannot run osascript with given parameters.");
		}
	}

	private void addMenu(Menu menu) {
		EventQueue.invokeLater(() -> {
			java.awt.Menu awtMenu = new java.awt.Menu(menu.getText());
			menu.getItems()
					.forEach(subItem -> awtMenu.add(AWTUtils.convertFromJavaFX(subItem)));
			this.popupMenu.add(awtMenu);
		});
	}

	private boolean isNotUnique(javafx.scene.control.MenuItem fxItem) {
		boolean result = true;
		for (int i = 0; i < popupMenu.getItemCount(); i++) {
			if (popupMenu.getItem(i).getLabel().equals(fxItem.getText())) {
				result = false;
				break;
			}
		}
		return !result;
	}

	private final ActionListener stageShowListener = e -> {
		if (this.parentStage != null) {
			Platform.runLater(this.parentStage::show);
		}
	};

	private MouseListener getPrimaryClickListener(EventHandler<ActionEvent> e) {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Platform.runLater(() -> e.handle(new ActionEvent()));
			}

			@Override
			public void mousePressed(MouseEvent ignored) {
			}

			@Override
			public void mouseReleased(MouseEvent ignored) {
			}

			@Override
			public void mouseEntered(MouseEvent ignored) {
			}

			@Override
			public void mouseExited(MouseEvent ignored) {
			}
		};
	}

	private final SystemTray tray;

	private Stage parentStage;

	private String appTitle;

	private final TrayIcon trayIcon;

	private final PopupMenu popupMenu = new PopupMenu();

	private boolean addExitMenuItem = false;

	private boolean addTitleMenuItem = false;

	private final boolean isMac;
}
