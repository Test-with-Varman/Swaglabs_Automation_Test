package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility to read common properties. Now loads properties from classpath
 * (src/main/resources/commdata.properties) so the project follows Maven
 * conventions and resources are available at runtime.
 */
public class readproperties {

	private String url;
    
	public readproperties() throws Throwable {
		Properties pr = new Properties();

		// Load from classpath (src/main/resources)
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("commdata.properties")) {
			if (is == null) {
				throw new IOException("commdata.properties not found on classpath");
			}
			pr.load(is);
			setUrl(pr.getProperty("url"));
		}
	}

	public String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}
}
