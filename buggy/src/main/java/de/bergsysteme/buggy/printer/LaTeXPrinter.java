package de.bergsysteme.buggy.printer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import de.bergsysteme.buggy.resolve.ObjectInspector;

/***
 * {@link LaTeXPrinter} unterstützt die Erstellung von LaTeX-Dokumenten. Hierbei
 * handelt es sich um LaTeX-Artefakte, die in andere Dateien inkludiert werden können.
 * Der äußere Rahmen zum Kompilieren der Tex-Datei
 * @author rollinhand
 *
 */
public class LaTeXPrinter 
implements IPrinter {
	private static final String DEFAULT_SECTION_AGGREGATION = "\\section*{(%s) %s}";
	private static final String DEFAULT_CHAPTER_AGGREGATION = "\\chapter*{(%s) %s}";
	private static final String DEFAULT_PARAGRAPH_AGGREGATION = "%s";
	public static final String SECTION_AGGREGATION = "section.aggregation";
	public static final String CHAPTER_AGGREGATION = "chapter.aggregation";
	public static final String PARAGRAPH_AGGREGATION = "paragraph.aggregation";
	public static final String SECTION_FIELDS = "section.fields";
	public static final String CHAPTER_CONTENT = "chapter.content";
	public static final String PARAGRAPH_FIELDS = "paragraph.fields";
	public static final String FILE = "file";
	
	private Hashtable<String,String> table;
	
	private Properties properties;
	
	private BufferedWriter out;
	
	public LaTeXPrinter() {
		table = new Hashtable<String, String>();
		setProperty(SECTION_AGGREGATION, DEFAULT_SECTION_AGGREGATION);
		setProperty(CHAPTER_AGGREGATION, DEFAULT_CHAPTER_AGGREGATION);
		setProperty(PARAGRAPH_AGGREGATION, DEFAULT_PARAGRAPH_AGGREGATION);
		setProperty(SECTION_FIELDS, "");
		setProperty(CHAPTER_CONTENT, "");
		setProperty(PARAGRAPH_FIELDS, "");
		try {
			properties = new Properties();
			InputStream is = ClassLoader.getSystemResourceAsStream("charset.properties");			
			properties.load(is);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print(String[] columns, Object[] data) throws Exception {
		String filename = getProperty(FILE, "releasenote.tex");  
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename)),"UTF8"));
		printChapter();
		for (int i=0; i < data.length; ++i) {
			Object obj = data[i];
			printSection(obj);
			printParagraph(obj);
			out.flush();
		}
		out.close();
	}
	
	private Object[] fetchData(String[] fields, Object o) {
		int length = fields.length;
		Object[] result = new Object[length];
		try {
			for (int i=0; i < length; ++i) {
				String field = fields[i];
				Method m = ObjectInspector.getInstance().findGetter(o, field);
				if (m != null) {
					Object line = m.invoke(o, (Object[])null);
					result[i] = replaceCharacters(String.valueOf(line));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String replaceCharacters(String line) {
		String[] keys = (String[]) properties.keySet().toArray(new String[0]);
		for (String pattern : keys) {
			String replacement = properties.getProperty(pattern);
			line = line.replaceAll(pattern, replacement);
		}
		return line;
	}
	
	protected void printSection(Object o) throws IOException {
		String sectionAggregation = getProperty(SECTION_AGGREGATION, DEFAULT_SECTION_AGGREGATION);
		String fieldsInProperty = getProperty(SECTION_FIELDS);
		String[] fields = fieldsInProperty.split(",");
		Object[] data = fetchData(fields, o);
		String section = String.format(sectionAggregation, (Object[])data);
		out.write(section);
		out.write("\r\n");
	}
	
	protected void printParagraph(Object o) throws IOException {
		String paragraphAggregation = getProperty(PARAGRAPH_AGGREGATION, DEFAULT_PARAGRAPH_AGGREGATION);
		String fieldsInProperty = getProperty(PARAGRAPH_FIELDS);
		String[] fields = fieldsInProperty.split(",");
		Object[] data = fetchData(fields, o);
		String paragraph = String.format(paragraphAggregation, (Object[])data);
		out.write(paragraph);
		out.write("\r\n");
	}
	
	protected void printChapter() throws IOException {
		String chapterAggregation = getProperty(CHAPTER_AGGREGATION, DEFAULT_CHAPTER_AGGREGATION);
		String fieldsInProperty = getProperty(CHAPTER_CONTENT);
		String[] fields = fieldsInProperty.split(",");
		String chapter = String.format(chapterAggregation, (Object[])fields);
		out.write(chapter);
		out.write("\r\n");
	}

	public void setProperty(String key, String value) {
		this.table.put(key, value);
	}

	public String getProperty(String key) {
		return this.table.get(key);
	}

	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key); 
		if (value == null) value = defaultValue;
		return value;
	}

	public String[] propertyNames() {
		return this.table.keySet().toArray(new String[0]);
	}
}
