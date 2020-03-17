package de.bergsysteme.buggy.resolve;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.resolve.ObjectInspector.TYPE;

public class ResponseResolver extends DefaultHandler {
	private static final String PACKAGE = "de.bergsysteme.buggy";
	
	private boolean proceed;
	
	private String setterName;
	
	private List<Object> buildObjects;
	
	protected List<ResolverListener> listeners;
	
	private Logger logger;
	
	private StringBuffer content = new StringBuffer();
	
	// Trigger to build a new object
	private static final String[] TRIGGERS = {"error", "token", "case", "event", "area", 
											  "project", "area", "fixfor", "priority", 
											  "category", "person", "interval"};
	
	public ResponseResolver() {
		proceed = false;
		buildObjects = new LinkedList<Object>();		
		listeners = new LinkedList<ResolverListener>();
		setterName = null;
		
		// instantiate Logger
		logger = LogManager.getRootLogger();
		logger.info("Current log level: " + logger.getLevel());
	}
	
	public void addListener(ResolverListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void removeListener(ResolverListener listener) {
		listeners.remove(listener);
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = String.copyValueOf(ch, start, length);
		if (isContent(value)) {
			content.append(value);
			content.trimToSize();
		}
	}
	
	/***
	 * Checks if the value contains only whitespaces. Because there is no official DTD
	 * for FogBugz this is a workaround to ignore whitespaces.
	 * 
	 * @param value received value to check.
	 * @return true if it is valid content and not only whitespaces.
	 */
	private boolean isContent(String value) {
		boolean content = false;
		char[] ch = value.toCharArray();
		for (int c=0; c < ch.length; ++c) {
			if (ch[c] > 32) { content = true; break; }
		}
		return content;
	}

	public void endElement(String uri, String localName, String name) throws SAXException {
		logger.trace( "Found end tag: " + localName);
		if ("response".equalsIgnoreCase(localName)) {
			proceed = false;
			setterName = null;
			notifyListeners();
		} else {
			if (content.length() > 0) {
				assignContent();
			}
			
			for (String trigger : TRIGGERS) {
				if (!trigger.equalsIgnoreCase(localName)) { this.setterName = null; }
			}
		}
	}

	// Wegschreiben des Inhalts aus dem ContentBuffer
	private void assignContent() throws SAXException {
		try {
			int lastObject = buildObjects.size() - 1;
			if (lastObject >= 0) { 
				Object obj = buildObjects.get(lastObject);
				this.setAttribute(obj, this.setterName, content.toString());
			} else {				
				logger.trace( "No objects in build queue.");
			}
		} catch (FogBugzException e) {
			logger.trace("Throwing exception upwards.", e);
			throw new SAXException(e);
		}
		content.delete(0, content.length());
		content.setLength(0);
	}

	public void startElement(String URI, String localname, String arg2, Attributes attributes) throws SAXException {
		boolean build_new = false;
		if ("response".equalsIgnoreCase(localname) && !proceed) { 
			clean();
			proceed = true; 
		}
		if (!proceed) { throw new SAXException("This is not an answer from FogBugz."); }
		logger.trace( "Found starting tag: " + localname);
		
		// Is this an indicator to build a new Object?
		for (String trigger : TRIGGERS) {
			logger.trace( "Checking for trigger: " + localname);
			if (trigger.equalsIgnoreCase(localname)) { build_new = true; }
		}
		
		try {
			// These are called by API Check command
			if ("version".equalsIgnoreCase(localname)) {
				Object obj = createObject("api");
				buildObjects.add(obj);
			}
			
			if (build_new) {
				Object obj = createObject(localname);
				buildObjects.add(obj);
				// Sonderfall token
				if ("token".equalsIgnoreCase(localname)) { this.setterName = localname; }
				// Sonderfall error
				if ("error".equalsIgnoreCase(localname)) {
					workWithAttributes(attributes);
					this.setterName = localname;
				}
			} else {
				this.setterName = localname;
			}
		} catch (FogBugzException e) {
			throw new SAXException(e);
		}	
	}
	
	/***
	 * Clean the internal object container.
	 * Otherwise we get results in a new call from a former response.
	 */
	private void clean() {
		if (buildObjects.size() > 0) { buildObjects.clear(); }
	}

	private void workWithAttributes(Attributes attributes) throws FogBugzException {
		if (attributes != null) {
			int size = attributes.getLength();
			for (int i = 0; i < size; ++i) {
				String setterName = attributes.getLocalName(i);
				String value = attributes.getValue(i);
				
				int lastObject = buildObjects.size() - 1;
				if (lastObject >= 0) {					
					Object obj = buildObjects.get(lastObject);
					this.setAttribute(obj, setterName, value);
				} else {
					logger.trace( "No objects in build queue.");
				}
			}
		}
	}
	
	private Object createObject(String className) throws FogBugzException {
		Object obj = null;
		
		// Create  the Java Class name
		char[] name = className.toCharArray();
		name[0] = (char) (name[0] - 32);
		className = String.copyValueOf(name);
		
		// add package
		className = String.format("%s.%s", PACKAGE, className);
		logger.trace( "Created classname from localname: " + className);
		
		// Create the instance and register in list
		try {
			Class<?> cls = Class.forName(className);
			obj = cls.newInstance();
		} catch (ClassNotFoundException e) {
			logger.trace( "Cannot create an object for " + className);
			throw new FogBugzException("Cannot create an object for " + className);
		} catch (Exception e) {
			throw new FogBugzException(e);
		} 
		return obj;
	}
	
	private void setAttribute(Object obj, String setterName, String value) throws FogBugzException {
		if (setterName == null) return;
		// Call the Method
		try {
			Method thisMethod = ObjectInspector.getInstance().findSetter(obj, setterName);
			if (thisMethod != null) {
				thisMethod.invoke(obj, value);
			} else {
				logger.warn("Setter unknown. Using fallback strategy.");
				useFallback(obj, setterName, value);
			}
		} catch (Exception e) {
			throw new FogBugzException(e);
		} 
	}
	
	private void useFallback(Object obj, String fieldName, String value) throws FogBugzException {
		Object[] valueArray = {fieldName, value};
		try {
			Class<?> clazz = obj.getClass();
			Method method = ObjectInspector.getInstance().findFallBackMethod(clazz, fieldName, TYPE.SET);
			if (method != null) {
				logger.info(String.format("Setting userdefined field: %s[value=%s]", fieldName, value));
				method.invoke(obj, valueArray);
			} else {
				logger.fatal("Fallback strategy failed. Cannot use userdefined fields.");
			}
		} catch (Exception e) {
			throw new FogBugzException(e);
		}
	}
	
	private void notifyListeners() {		
		for (ResolverListener l : listeners) { 
			logger.trace( "Work done. Notifying: " + l.toString());
			l.notify(buildObjects); 
		}
	}
}
