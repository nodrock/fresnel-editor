package cz.muni.fi.fresneleditor.gui.mod.format2.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TextFileLoader {
	private URL url = null;
	private File file = null;

	public TextFileLoader(String file) {
		try {
			url = new URL(file);
		} catch (MalformedURLException e) {
			// its a local file
			this.file = new File(file);
		}
	}

	public String getName() {
		if (url != null) {
			return url.getFile();
		} else if (file != null) {
			return file.getName();
		} else {
			return "";
		}
	}

	public String deserializeString() throws IOException {
		final StringBuffer buffer = new StringBuffer();
		if (url != null) {
			String line;
			URLConnection urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line).append("\n");
			}

			reader.close();
		} else if (file != null) {
			int len;
			char[] chr = new char[4096];
			final FileReader reader = new FileReader(file);
			try {
				while ((len = reader.read(chr)) > 0) {
					buffer.append(chr, 0, len);
				}
			} finally {
				reader.close();
			}
		}
		return buffer.toString();
	}
}
