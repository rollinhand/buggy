package de.bergsysteme.buggy.convert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.command.SearchCommand;
import de.bergsysteme.buggy.printer.IPrinter;
import de.bergsysteme.buggy.printer.PrinterFactory;
import de.bergsysteme.buggy.resolve.Processor;
import de.bergsysteme.buggy.writer.BugWriter;
import de.bergsysteme.buggy.writer.ExcelBugWriter;

public class ProviderSessionConverter
extends Converter {
			
	private final String[] queries = {
		"(project:\"Support OGE\" OR project:\"en|damo Entwicklung\") (resolved:\"last week\")",
		//"(project:\"Support OGE\" OR project:\"en|damo Entwicklung\") (opened:\"last week\")"
	};
	
	private String[] fields = { "ixBug", "dtOpened", "sTitle", "sProject", "sStatus", 
							    "sPriority", "sFixFor", "sCategory", "dtResolved" };
	
	private final String[] prefix = { "resolved", "opened" };
	
	private int run = 0;
	
	public ProviderSessionConverter() {
		super();
		this.setProperty(PROPERTY_PRINTER_NAME, "csv");
	}
	
	@Override
	public void execute() {
		logger.trace("Executing ProviderSessionConverter.");
		validateAuthentication(getProperty(PROPERTY_AUTHENTICATION));
		try {
			SearchCommand cmd = prepareCommand();
			for (; run < queries.length; ++run) {
				String query = queries[run];
				cmd.setQuery(query);
				Processor.setConnection(connection);
				Processor.execute(cmd);
			}
		} catch (FogBugzException e) {
			e.printStackTrace();
		}
	}

	private SearchCommand prepareCommand() {
		SearchCommand cmd = new SearchCommand();
		cmd.addListener(this);
		for (String field : fields) { cmd.addColumn(field); }
		return cmd;
	}

	@Override
	protected void print(List<Object> list) {
		/*try {
			String printerName = getProperty(PROPERTY_PRINTER_NAME, "csv");
			IPrinter printer = PrinterFactory.getInstance().findPrinterByName(printerName);
			printer.setProperty("file", String.format("%s.csv", prefix[run]));
			Object[] data = list.toArray();
			printer.print(fields, data);
		} catch (Exception e) {
			logger.fatal("Printing Exception: " + e.getMessage());
		}*/
		
		try {
			BugWriter writer = new ExcelBugWriter(new FileOutputStream("workbook.xls"));
			Object[] data = list.toArray();
			writer.write(fields, data);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		ProviderSessionConverter psc = new ProviderSessionConverter();
		psc.setProperty(PROPERTY_AUTHENTICATION, "bjoern.berg@numetris.de/20golem09@https://support-numetris.de");
		psc.setProperty(PROPERTY_PRINTER_NAME, "csv");
		psc.execute();
	}
}
