package de.numetris.buggy.converter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.command.SearchCommand;
import de.bergsysteme.buggy.convert.Converter;
import de.bergsysteme.buggy.resolve.Processor;
import de.bergsysteme.buggy.writer.ExcelWriter;

public class ProviderSessionConverter
extends Converter {
			
	private final String[] queries = {
		"(project:\"Support OGE\" OR project:\"en|damo Entwicklung\") (resolved:\"last week\")",
		"(project:\"Support OGE\" OR project:\"en|damo Entwicklung\") (opened:\"last week\")"
	};
	
	private String[] fields = { "ixBug", "dtOpened", "sTitle", "sProject", "sStatus", 
							    "sPriority", "sFixFor", "sCategory", "dtResolved" };
	
	private final String[] prefix = { "Gelöst", "Geöffnet" };
	
	private ExcelWriter writer;
	
	private int run = 0;
	
	public ProviderSessionConverter() {
		super();
	}
	
	@Override
	public void execute() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("w");
			int weekInYear = Integer.parseInt(df.format(new Date()));
			// FIXME das funktioniert noch nicht beim Jahreswechsel
			String filename = String.format("Wochenbericht_%d.xls", weekInYear - 1);
			logger.trace("Executing ProviderSessionConverter.");
			writer = new ExcelWriter(new FileOutputStream(filename));
			validateAuthentication(getProperty(PROPERTY_AUTHENTICATION));
			SearchCommand cmd = prepareCommand();
			for (; run < queries.length; ++run) {
				String query = queries[run];
				cmd.setQuery(query);
				Processor.setConnection(connection);
				Processor.execute(cmd);
			}
			writer.close();
		} catch (FogBugzException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private SearchCommand prepareCommand() {
		SearchCommand cmd = new SearchCommand();
		cmd.addListener(this);
		cmd.addColumns(fields);
		return cmd;
	}

	@Override
	protected void print(List<Object> list) {
		try {
			Object[] data = list.toArray();
			String sheetname = prefix[run];
			writer.write(sheetname, fields, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		ProviderSessionConverter psc = new ProviderSessionConverter();
		psc.setProperty(PROPERTY_AUTHENTICATION, "bjoern.berg@numetris.de/20golem09@https://support-numetris.de");
		psc.execute();
	}
}
