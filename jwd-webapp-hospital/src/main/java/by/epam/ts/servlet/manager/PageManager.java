package by.epam.ts.servlet.manager;

import java.util.ResourceBundle;

public class PageManager {
	
	private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");
	
	private PageManager() {}
	
	public static String getProperty(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}

}