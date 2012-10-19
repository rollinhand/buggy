package de.bergsysteme.buggy.command;

import org.apache.log4j.Logger;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.resolve.ResolverListener;
import de.bergsysteme.buggy.resolve.ResponseResolver;

public abstract class DefaultCommand implements ICommand {
	Logger logger = Logger.getRootLogger();
	ContentHandler ch;
	public DefaultCommand() {
		 ch = new ResponseResolver();
	}
	
	public void execute(String uri) throws FogBugzException {
		try {
			InputSource is = new InputSource(uri);
			XMLReader parser = XMLReaderFactory.createXMLReader();
			parser.setProperty("http://apache.org/xml/properties/input-buffer-size", new Integer(2048));

			// Configure Parser
			parser.setContentHandler(ch);
			parser.parse(is);
		} catch (Exception e) {		
			// Certificate Exception
			//e.printStackTrace();
			logger.warn("Executing command failed.", e);
		}
	}
	
	public void addListener(ResolverListener listener) {
		ResponseResolver r = (ResponseResolver) ch;
		r.addListener(listener);
	}
	
	public void removeListener(ResolverListener listener) {
		ResponseResolver r = (ResponseResolver) ch;
		r.removeListener(listener);
	}

	@Override
	public String toString() {
		return getCommand();
	}
}
