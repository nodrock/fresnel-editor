package cz.muni.fi.fresneleditor.common;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowserUtils {
	public static void navigate(String url){
		Desktop desktop;
		if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            // Now enable buttons for actions that are supported.
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
            	URI uri = null;
                try {
                	//String dir = System.getProperty("user.dir");
                    uri = new URI(url);
                    desktop.browse(uri);
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }
                catch(URISyntaxException use) {
                    use.printStackTrace();

                }
            }
        } 
	}
}
