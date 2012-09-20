package de.bergsysteme.buggy.convert;

import java.util.List;
import java.util.logging.Level;

import de.bergsysteme.buggy.Case;
import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.command.SearchCommand;
import de.bergsysteme.buggy.resolve.Processor;
import de.bergsysteme.buggy.resolve.ResolverListener;

public class InitiatorConverter 
extends Converter
implements ResolverListener {

	private String[] queries = {
		"(project:\"Support OGE\" OR project:\"Gas Entwicklung\") (resolved:\"last week\")",
		"(project:\"Support OGE\" OR project:\"Gas Entwicklung\") (opened:\"last week\")"
	};

	public void execute() {
		logger.info("Converter starts.");
		validateAuthentication("bjoern.berg@numetris.de/20golem09@https://support-numetris.de");
		try {
			// Configure command
			logger.trace("Configuring Search Command.");
			SearchCommand cmd = new SearchCommand();
			
			String query = null;
			//for (int i=0; i < queries.length; ++i) {
				query = "(project:\"Gas Entwicklung\") (resolved:\"1/1/2012..today\") (category:Feature)";
				
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
			//}			
		} catch (FogBugzException e) {
			logger.fatal("Internal Exception.", e);
			e.printStackTrace();
		}
	}
	
	protected void print(List<Object> list) {
		for (Object obj : list) {
			Case mcase = (Case) obj;
			System.out.println(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
			mcase.getIxBug(), mcase.getDtOpened(), mcase.getsTitle(), mcase.getsProject(), mcase.getsStatus(),
			mcase.getsPriority(), mcase.getsFixFor(), mcase.getsCategory(), mcase.getDtResolved(),
			mcase.getUserdefinedField("initiator"), mcase.getHrsCurrEst(), mcase.getHrsElapsed()));			
		}
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
