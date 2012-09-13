package de.bergsysteme.fogbugz.resolve;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ObjectInspector {
	private Logger logger;
	private ObjectCache cache;
	public enum TYPE {SET, GET};
	
	private static ObjectInspector _instance;
	
	private ObjectInspector() {
		this.cache = new ObjectCache();
		logger = Logger.getLogger(Processor.PROJECT);
	}

	public static ObjectInspector getInstance() {
		if (_instance == null) {
			_instance = new ObjectInspector();
		}
		return _instance;
	}
	
	public Method findSetter(Object obj, String fieldName) {
		String methodName = MethodFormatter.setMethod(fieldName);
		logger.finer("Converted '" + fieldName + "' to '" + methodName + "'.");
		Class<?> clazz = obj.getClass();
		Method m = findMethod(clazz, methodName);
		return m;
	}
	
	public Method findGetter(Object obj, String fieldName) {
		String methodName = MethodFormatter.getMethod(fieldName);
		logger.finer("Converted '" + fieldName + "' to '" + methodName + "'.");
		Class<?> clazz = obj.getClass();
		Method m = findMethod(clazz, methodName);
		return m;
	}
	
	private Method findMethod(Class<?> clazz, String methodName) {
		logger.finer("Trying to resolve Method: " + methodName);
		Method m = askCache(clazz, methodName);
		if (m == null) {
			if (inspectClazz(clazz)) {
				// Ask Cache again. It is now filled with all associated
				// getters and setters for this special class.
				m = askCache(clazz, methodName);
			} else {
				logger.severe("Inspecting class failed.");
			}
		}
		return m;
	}
	
	public Method findFallBackMethod(Class<?> clazz, String fieldName, TYPE t) {
		final String USER_DEFINED = "UserdefinedField";
		String fallbackName = null;
		String prefix = t == TYPE.SET ? "set" : "get";
		fallbackName = String.format("%s%s", prefix, USER_DEFINED);
		logger.info("Asking Cache for Fallback Routine: " + fallbackName);
		Method m = findMethod(clazz, fallbackName);
		return m;
	}
	
	private Method askCache(Class<?> clazz, String methodName) {
		Method m = null;
		if (cache != null) {
			logger.finer("Asking Cache for Method with name: " + methodName);
			m = cache.findMethod(clazz, methodName);
		} else {
			logger.warning("Cache is not active.");
		}
		return m;
	}
	
	private boolean inspectClazz(Class<?> clazz) {
		boolean success = false;
		logger.info("Inspecting Class: " + clazz.getCanonicalName());
		
		Method[] declMethods = clazz.getDeclaredMethods();
		if (declMethods != null && declMethods.length > 0) {
			for (Method m : declMethods) {
				String methodName = m.getName();
				if (methodName.startsWith("set")) {
					// We only accept Strings as parameter
					Class<?>[] params = m.getParameterTypes();
					if (params.length == 1 && params[0] == String.class) {
						logger.fine("Updating Cache. Setter / Getter: " + methodName);
						cache.updateCache(clazz, methodName, m);
					} else {
						logger.info("Rejecting method " + methodName);
					}
				} else if (methodName.startsWith("get")) {
					logger.fine("Updating Cache. Setter / Getter: " + methodName);
					cache.updateCache(clazz, methodName, m);
				} else {
					boolean accepted = true;
					// For Fallback. Must be all Strings
					Class<?>[] params = m.getParameterTypes();
					for (Class<?> c : params) {
						if (c != String.class) { accepted = false; }
					}
					if (accepted) {
						logger.fine("Updating Cache. Setter / Getter: " + methodName);
						cache.updateCache(clazz, methodName, m);
					}
				}
			}
			success = true;
		} else {
			logger.warning("No declared methods found.");
		}
		return success;
	}
}
