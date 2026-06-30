package properties;

import java.io.FileInputStream;
import java.util.Properties;

public class readproperties {

	private String url;
	
	public readproperties() throws Throwable {

		Properties pr = new Properties();
		String proppath = System.getProperty("user.dir") + "/src/main/java/properties/commdata.properties";
		FileInputStream fle = new FileInputStream(proppath);
		pr.load(fle);
		setUrl(pr.getProperty("url"));
		
	}

	public String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}
}
