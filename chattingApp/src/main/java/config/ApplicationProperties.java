package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
	
	public static String getProperty(String propertyName){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input= ApplicationProperties.class.getResourceAsStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			return prop.getProperty(propertyName);

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			return "";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
