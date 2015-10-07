package de.bergsysteme.buggy.resolve;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ObjectCache {

	Map<Class<?>, TreeMap<String, Method>> internalCache;
	
	public ObjectCache() {
		internalCache = new HashMap<Class<?>, TreeMap<String,Method>>();
	}

	public Method findMethod(Class<?> clazz, String methodName) {
		Method m = null;
		if (!hasClazzMap(clazz)) { createClazzMap(clazz); }
		TreeMap<String, Method> map = internalCache.get(clazz);
		m = searchForMethod(map, methodName);
		return m;
	}
	
	public void updateCache(Class<?> clazz, String methodName, Method method) {
		if (!hasClazzMap(clazz)) { createClazzMap(clazz); }
		Map<String, Method> map = internalCache.get(clazz);
		map.put(methodName.toLowerCase(), method);
	}
	
	private boolean hasClazzMap(Class<?> clazz) {
		TreeMap<String, Method> map = internalCache.get(clazz);
		return map == null ? false : true;
	}
	
	private void createClazzMap(Class<?> clazz) {
		TreeMap<String, Method> classMap = new TreeMap<String, Method>();
		internalCache.put(clazz, classMap);
	}
	
	private Method searchForMethod(TreeMap<String, Method> map, String methodName) {
		Method m = null;
		if (map != null && methodName != null && !map.isEmpty()) {
			m = map.get(methodName.toLowerCase());
		}
		return m;
	}
}
