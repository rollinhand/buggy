package de.bergsysteme.fogbugz.command;

import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import de.bergsysteme.fogbugz.FogBugzException;
import de.bergsysteme.fogbugz.resolve.ResolverListener;
import de.bergsysteme.fogbugz.resolve.ResponseResolver;

public abstract class DefaultCommand implements ICommand {
	ContentHandler ch;
	public DefaultCommand() {
		 ch = new ResponseResolver();
	}
	
	public void execute(String uri) throws FogBugzException {		
		InputSource is = new InputSource(uri);
		
		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			parser.setProperty("http://apache.org/xml/properties/input-buffer-size", new Integer(2048));

			// Configure Parser
			parser.setContentHandler(ch);
			parser.parse(is);
		} catch (SAXException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
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
