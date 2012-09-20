package de.bergsysteme.buggy.main;

import org.apache.log4j.Logger;

import de.bergsysteme.buggy.printer.IPrinter;
import de.bergsysteme.buggy.printer.PrinterFactory;

public class FogBugz {
	private Logger logger;
	private String query;
	private String[] columns;
	private IPrinter printer;
	private String filename;
	
	public FogBugz() {
		this.logger = Logger.getRootLogger();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public IPrinter getPrinter() {
		return printer;
	}

	public void setPrinter(IPrinter printer) {
		this.printer = printer;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/***
	 * Fügt die einzelnen Argumente zu einem String zusammen.
	 * @param args
	 * @return
	 */
	private String concatArguments(String[] args) {
		String chain = null;
		for (String s : args) {
			chain += s;
		}
		return chain;
	}
	
	/***
	 * Zerlegt nach Trennzeichen.
	 * @param args
	 */
	protected void splitArguments(String args) {
		String[] arguments = args.split("-");
		
		for (String arg : arguments) {
			if (arg.equalsIgnoreCase("q")) {
				String s = arg.substring(2);
				this.setQuery(s);
			} else if (arg.equalsIgnoreCase("-query")) {
				String s = arg.substring(7);
				this.setQuery(s);
			} else if (arg.equalsIgnoreCase("c") || arg.equalsIgnoreCase("-columns")) {
				String[] tmp = arg.split(",");
				for (String t : tmp) {
					t = t.trim();
				}
				this.setColumns(tmp);
			} else if (arg.equalsIgnoreCase("p") || arg.equalsIgnoreCase("-printer")) {
				try {
					String[] tmp = arg.split(" ");
					String printerName = tmp[1];
					logger.info("Detected Printer Name: " + printerName);
					IPrinter printer = PrinterFactory.getInstance().findPrinterByName(printerName);
					this.setPrinter(printer);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (arg.equalsIgnoreCase("s") || arg.equalsIgnoreCase("-setting")) {
				// FIXME not yet implemented
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FogBugz fb = new FogBugz();
		String chain = fb.concatArguments(args);
		fb.splitArguments(chain);

	}

}
