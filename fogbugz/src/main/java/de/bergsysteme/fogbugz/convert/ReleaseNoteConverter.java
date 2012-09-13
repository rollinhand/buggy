package de.bergsysteme.fogbugz.convert;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import de.bergsysteme.fogbugz.FogBugzException;
import de.bergsysteme.fogbugz.command.SearchCommand;
import de.bergsysteme.fogbugz.printer.IPrinter;
import de.bergsysteme.fogbugz.printer.LaTeXPrinter;
import de.bergsysteme.fogbugz.printer.PrinterFactory;
import de.bergsysteme.fogbugz.resolve.Processor;

/***
 * Attributes needed:
 * - E-Mail
 * - password
 * - URL
 * - target
 * <email>/<password>@<host> target
 * @author rollinhand
 *
 */
public class ReleaseNoteConverter 
extends Converter {
	private static final String PROPERTY_TARGET = "notes.target";
	private static final String PROPERTY_PROJECT = "notes.project";
	
	Properties properties;
	
	public ReleaseNoteConverter() {		
		setProperty(PROPERTY_PRINTER_NAME, "latex");
	}

	public void usage() {
		System.out.println("<email>/<password>@<host> <project> <target>");
	}
	
	private boolean splitArguments(String[] args) {
		boolean success = false;
		if (args.length == 3) {
			String authentication = args[0];
			String project = args[1];
			String target = args[2];
			setProperty(PROPERTY_TARGET, target);
			setProperty(PROPERTY_PROJECT, project);
			success = validateAuthentication(authentication);
		} else {
			logger.log(Level.SEVERE, "Not enough arguments.");
			usage();
			success = false;
		}
		return success;
	}
	
	public void execute() {
		try {
			SearchCommand cmd = prepareCommand();
			Processor.setConnection(connection);
			Processor.execute(cmd);	
		} catch (FogBugzException e) {
			logger.log(Level.SEVERE, "Internal Exception.", e);
			e.printStackTrace();
		}
	}
	
	private SearchCommand prepareCommand() {
		SearchCommand cmd = new SearchCommand();
		String target = getProperty(PROPERTY_TARGET);
		String project = getProperty(PROPERTY_PROJECT);
		String query = String.format("fixfor:%s AND project:%s", target, project);
		cmd.setQuery(query);
		cmd.addColumn("ixBug");
		cmd.addColumn("sCategory");
		cmd.addColumn("sTitle");
		cmd.addColumn("sReleaseNotes");
		cmd.addListener(this);
		return cmd;
	}
	
	@Override
	protected void print(List<Object> list) {
		try {
			String printerName = getProperty(PROPERTY_PRINTER_NAME, "latex");
			IPrinter printer = PrinterFactory.getInstance().findPrinterByName(printerName);
			printer.setProperty(LaTeXPrinter.CHAPTER_CONTENT, String.format("%s,%s", getProperty(PROPERTY_PROJECT), getProperty(PROPERTY_TARGET)));
			printer.setProperty(LaTeXPrinter.SECTION_FIELDS, "ixBug,sTitle");
			printer.setProperty(LaTeXPrinter.PARAGRAPH_FIELDS, "sReleaseNotes");
			Object[] data = list.toArray();
			printer.print(fields, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Printing Exception: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		ReleaseNoteConverter converter = new ReleaseNoteConverter();
		if (converter.splitArguments(args)) {
			converter.execute();
		} else {
			System.err.println("Exiting now.");
		}
	}
}
