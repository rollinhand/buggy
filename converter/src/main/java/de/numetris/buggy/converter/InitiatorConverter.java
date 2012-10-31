package de.numetris.buggy.converter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import de.bergsysteme.buggy.Case;
import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.command.SearchCommand;
import de.bergsysteme.buggy.convert.Converter;
import de.bergsysteme.buggy.resolve.Processor;
import de.bergsysteme.buggy.resolve.ResolverListener;
import de.bergsysteme.buggy.writer.ExcelWriter;

public class InitiatorConverter 
extends Converter
implements ResolverListener {
	
	final String[] fieldnames = {"ixBug", "dtOpened", "sTitle", "sProject", "sStatus", "sPriority",
								 "sFixFor", "sCategory", "dtResolved", "initiator", "hrsCurrEst", "hrsElapsed"};
	
	public void execute() {
		logger.info("Converter starts.");
		validateAuthentication("bjoern.berg@numetris.de/20golem09@https://support-numetris.de");
		try {
			// Configure command
			logger.trace("Configuring Search Command.");
			SearchCommand cmd = new SearchCommand();
			
			
			String query = "(project:\"en|damo Entwicklung\") (resolved:\"1/1/2012..today\") (category:Feature)";
			
			logger.info("Active query: " + query);
			cmd.setQuery(query);
			cmd.addColumn("ixBug");
			cmd.addColumn("dtOpened");
			cmd.addColumn("sTitle");
			cmd.addColumn("sProject");
			cmd.addColumn("sStatus");
			cmd.addColumn("sPriority");
			cmd.addColumn("sFixFor");
			cmd.addColumn("sCategory");
			cmd.addColumn("dtResolved");
			cmd.addColumn("initiator");
			cmd.addColumn("hrsCurrEst");
			cmd.addColumn("hrsElapsed");
			cmd.addListener(this);
			
			// Send request
			logger.trace("Sending command to processor.");
			Processor.setConnection(connection);
			Processor.execute(cmd);
			logger.trace("Processing done.");
						
		} catch (FogBugzException e) {
			logger.fatal("Internal Exception.", e);
			e.printStackTrace();
		}
	}
	
	protected void print(List<Object> list) {
		try {
			OutputStream out = new FileOutputStream("feature_stunden.xls");
			ExcelWriter writer = new ExcelWriter(out);
			writer.write(fieldnames, list.toArray());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*for (Object obj : list) {
			Case mcase = (Case) obj;
			System.out.println(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
			mcase.getIxBug(), mcase.getDtOpened(), mcase.getsTitle(), mcase.getsProject(), mcase.getsStatus(),
			mcase.getsPriority(), mcase.getsFixFor(), mcase.getsCategory(), mcase.getDtResolved(),
			mcase.getUserdefinedField("initiator"), mcase.getHrsCurrEst(), mcase.getHrsElapsed()));			
		}*/
	}
	
	public void notify(List<Object> l) {
		if (l == null || l.size() == 0) {
			logger.warn("No results.");
		} else {
			logger.info("Returned results: " + l.size());
			print(l);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitiatorConverter converter = new InitiatorConverter();
		converter.execute();
	}
}
