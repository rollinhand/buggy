package de.bergsysteme.buggy.resolve;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Field {
	private static Properties properties = new Properties();
	
	public static void initialize() {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("messages.properties");
			properties.load(is);
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String translate(String fieldname) {
		if (fieldname == null) fieldname = "";
		String key = String.format("Field.%s", fieldname.toLowerCase());
		String name = properties.getProperty(key, fieldname);
		return name;
	}
	
	static {
		Field.initialize();
	}
	
	private Field() {
	}
}
