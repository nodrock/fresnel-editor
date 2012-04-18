package cz.muni.fi.fresneleditor.common.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import cz.muni.fi.fresneleditor.common.FresnelApplication;

/**
 * Helper class providing static methods for GUI support.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class GuiUtils {

	/**
	 * Returns the owner frame of given component.
	 * 
	 * @param component
	 *            a component which owner we are looking for
	 * @return the owner of the given component
	 */
	public static Frame getOwnerFrame(Component component) {
		while ((component != null) && !(component instanceof Frame)) {
			component = component.getParent();
		}
		return ((Frame) component);
	}

	/**
	 * Utility method that puts the component to the centre of the screen.
	 * 
	 * @param component
	 *            to be centred
	 */
	public static void centerOnScreen(Component component) {
		Toolkit toolkit = component.getToolkit();
		Dimension size = toolkit.getScreenSize();
		component.setLocation(size.width / 2 - component.getWidth() / 2,
				size.height / 2 - component.getHeight() / 2);
	}

	/**
	 * Returns the application main frame.
	 * 
	 * @return the application mane frame
	 */
	public static Component getTopComponent() {
		return FresnelApplication.getApp().getMainFrame();
	}
}
