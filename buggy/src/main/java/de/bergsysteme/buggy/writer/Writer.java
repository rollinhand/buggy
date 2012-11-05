package de.bergsysteme.buggy.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.bergsysteme.buggy.resolve.ObjectInspector;
import de.bergsysteme.buggy.resolve.ObjectInspector.TYPE;

public abstract class Writer {
	protected OutputStream out;
	SimpleDateFormat sf = new SimpleDateFormat();
	
	/***
	 * Create a new character-stream writer.
	 * @param out
	 */
	public Writer(OutputStream out) {
		this.out = out;
	}

	/***
	 * Close the stream.
	 * @throws IOException
	 */
	public void close() throws IOException {
		out.flush();
		out.close();
	}
	
	/***
	 * Write fields and their appropriate values.
	 * @param fieldnames
	 * @param values
	 * @throws IOException
	 */
	public abstract void write(String[] fieldnames, Object[] values) throws IOException;
	
	@SuppressWarnings("finally")
	protected String getValue(String fieldname, Object data) {
		String value = null;
		try {
			Method m = ObjectInspector.getInstance().findGetter(data, fieldname);
			Object result = null;
			if (m == null) {
				m = ObjectInspector.getInstance().findFallBackMethod(data.getClass(), fieldname, TYPE.GET); 
				Object[] params = {fieldname};
				result = m.invoke(data, params);
			} else {
				result = m.invoke(data, (Object[])null);
			}
			if (result != null) {
				// Conversion operations depending on the current Locale.
				value = formatResult(result);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			return value;
		}
	}

	/***
	 * Formats the result depending on the current used Locale by the
	 * operating system. Formats floats and Dates.
	 * @param result returned result by ObjectInspector.
	 * @return transformed String.
	 */
	private String formatResult(Object result) {
		String value = null;
		if (result instanceof Date) {
			value = sf.format((Date)result);
		} else if (result instanceof Float) {
			value = String.format("%f", result);
		} else {
			value = String.valueOf(result);
		}
		return value;
	}
}
