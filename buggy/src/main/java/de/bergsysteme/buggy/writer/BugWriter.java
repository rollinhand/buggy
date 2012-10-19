package de.bergsysteme.buggy.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.bergsysteme.buggy.resolve.ObjectInspector;

public abstract class BugWriter {
	protected OutputStream out;
	
	/***
	 * Create a new character-stream writer.
	 * @param out
	 */
	public BugWriter(OutputStream out) {
		this.out = out;
	}

	/***
	 * Close the stream.
	 * @throws IOException
	 */
	public void close() throws IOException {
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
			if (m != null) {
				Object result = m.invoke(data, (Object[])null);
				value = String.valueOf(result);
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
}
