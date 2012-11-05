package de.numetris.buggy.converter;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.command.ReleaseNotesCommand;
import de.bergsysteme.buggy.command.SearchCommand;
import de.bergsysteme.buggy.convert.Converter;
import de.bergsysteme.buggy.printer.IPrinter;
import de.bergsysteme.buggy.printer.LaTeXPrinter;
import de.bergsysteme.buggy.printer.PrinterFactory;
import de.bergsysteme.buggy.resolve.Processor;

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
		if (args.length >= 2) {
			String authentication = args[0];
			String project = args[1];
			String target = args[2];
			setProperty(PROPERTY_TARGET, target);
			setProperty(PROPERTY_PROJECT, project);
			success = validateAuthentication(authentication);
		} else {
			logger.fatal("Not enough arguments.");
			usage();
			success = false;
		}
		return success;
	}
	
	public void execute() {
		try {
			String target = getProperty(PROPERTY_TARGET);
			String project = getProperty(PROPERTY_PROJECT);
			
			ReleaseNotesCommand cmd = new ReleaseNotesCommand();
			cmd.setProject(project);
			cmd.setFixFor(target);
			cmd.addListener(this);
			logger.info("Command is: " + cmd.toString());
			
			Processor.setConnection(connection);
			Processor.execute(cmd);	
		} catch (FogBugzException e) {
			logger.fatal("Internal Exception.", e);
			e.printStackTrace();
		}
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
			logger.fatal("Printing Exception: " + e.getMessage());
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