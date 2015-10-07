package de.bergsysteme.buggy.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.bergsysteme.buggy.Connection;
import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.command.ListCommand;
import de.bergsysteme.buggy.command.SearchCommand;
import de.bergsysteme.buggy.resolve.Processor;
import de.bergsysteme.buggy.resolve.ResolverListener;
import de.bergsysteme.buggy.writer.Writer;
import de.bergsysteme.buggy.writer.WriterFactory;

public class FogBugz implements ResolverListener {
	private Logger logger;
	private List<String> queries;
	private String[] fieldnames;
	private String filename;
	private Writer writer;
	private String writerName;
	private Connection connection;
	private ListCommand lcmd;
	
	// Command line parsing
	private Options options;
	
	public FogBugz() {
		this.queries = new LinkedList<String>();
		this.logger = Logger.getRootLogger();
		logger.setLevel(Level.TRACE);
		this.connection = new Connection();
		buildOptions();
	}

	public String getQuery(int index) {
		return queries.get(index);
	}

	public void addQuery(String query) {
		this.queries.add(query);
	}
	
	public void addQueries(String[] queries) {
		for (String q : queries) { this.addQuery(q); }
	}
	
	public String[] getQueries() {
		return this.queries.toArray(new String[0]);
	}

	public String[] getFieldnames() {
		return fieldnames;
	}

	public void setFieldnames(String[] fieldnames) {
		this.fieldnames = fieldnames;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	protected void init(String[] args) {
		CommandLineParser parser = new GnuParser();
	    try {
	        // parse the command line arguments
	        CommandLine line = parser.parse( options, args );
	        connection.setEmail(getOption("username", line));
	        connection.setPassword(getOption("password", line));
	        connection.setServerURL(getOption("host", line));
	        connection.setAuthentication(getOption("auth", line));
	        this.addQueries(getOptions("query", line));
	        this.setFieldnames(getOptions("fields", line));
	        writerName = getOption("writer", line);
        	this.setFilename(getOption("file", line));
	    } catch( ParseException exp ) {
	    	logger.fatal("Parsing failed.", exp);
	    }
	}
	
	private String getOption(final String option, final CommandLine commandLine) {
	    if (commandLine.hasOption(option)) {
	        return commandLine.getOptionValue(option);
	    }
	    return StringUtils.EMPTY;
	}
	
	private String[] getOptions(final String option, final CommandLine commandLine) {
	    if (commandLine.hasOption(option)) {
	        return commandLine.getOptionValues(option);
	    }
	    return null;
	}
	
	@SuppressWarnings("static-access")
	private void buildOptions() {
		options = new Options();
		
		Option username = OptionBuilder.withArgName("username")
				   					   .withLongOpt("username")
				   					   .hasArg()
				   					   .withDescription("Username for authentication.")
				   					   .create("u");
		
		Option password = OptionBuilder.withArgName("password")
				   					   .withLongOpt("password")
				   					   .hasArg()
				   					   .withDescription("Password for authentication.")
				   					   .create("p");
		
		Option host 	= OptionBuilder.withArgName("url")
				   					   .hasArg()
				   					   .withDescription("host for authentication.")
				   					   .create("host");
		
		Option login 	= OptionBuilder.withArgName("<email>/<password>@<host>")
									   .withLongOpt("auth")
				   					   .hasArg()
				   					   .withDescription("Use instead of single invocation of username, password, host.")
				   					   .create("a");
		
		Option query 	= OptionBuilder.withArgName("query1, query2, ...")
									   .withLongOpt("query")
									   .hasArgs()
									   .withValueSeparator(',')
									   .withDescription("Add one or more queries for searching FogBugz.")
									   .create("q");
		
		Option list 	= OptionBuilder.withArgName("name of list")
				   					   .withLongOpt("list")
				   					   .hasArg()
				   					   .withDescription("Call a specific list command.")
				   					   .create("l");
		
		Option fields 	= OptionBuilder.withArgName("field1, field2, ...")
				   					   .withLongOpt("fields")
				   					   .hasArgs()
				   					   .withValueSeparator(',')
				   					   .withDescription("Comma-separated list of fields.")
				   					   .create("f");
		
		Option writer 	= OptionBuilder.withArgName("console|excel|csv|ini")
				   					   .withLongOpt("writer")
				   					   .hasArg()
				   					   .withDescription("Call a specific list command.")
				   					   .create("w");
	
		Option outfile 	= OptionBuilder.withArgName( "filename" )
                					   .hasArg()
                					   .withDescription(  "name of output file" )
                					   .create( "file" );
		
		options.addOption("h", "help", false, "print this message");
		options.addOption("?", false, "print this message");
		options.addOption(username);
		options.addOption(password);
		options.addOption(host);
		options.addOption(login);
		options.addOption(query);
		options.addOption(list);
		options.addOption(fields);
		options.addOption(writer);
		options.addOption(outfile);
	}
	
	protected void usage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "fogbugz", options );
	}
	
	public void execute() {
		// Log Parameters
		/*logger.info("Username: " + connection.getEmail());
		logger.info("Host: " + connection.getServerURL());
		// Queries
		for (String query : getQueries()) {
			logger.info("Query: " + query);
		}
		// Fields
		for (String field : getFieldnames()) {
			logger.info("Field: " + field);
		}
		logger.info("Writer: " + writer);
		logger.info("Filename: " + getFilename());*/
		try {
			if (lcmd == null) {
				OutputStream out = new FileOutputStream(getFilename());
				writer = WriterFactory.getInstance().findWriterByName(writerName, out);
				for (String query : getQueries()) {
					SearchCommand cmd = new SearchCommand();
					cmd.addColumns(fieldnames);
					cmd.setQuery(query);
					cmd.addListener(this);
				
					// Send request
					Processor.setConnection(connection);
					Processor.execute(cmd);
				}
			} else {
				lcmd.addListener(this);
				Processor.setConnection(connection);
				Processor.execute(lcmd);
			}
			writer.close();
		} catch (FogBugzException e) {
			logger.fatal("Execution failed.", e);
		} catch (FileNotFoundException e) {
			logger.fatal("Execution failed.", e);
		} catch (IOException e) {
			logger.fatal("Execution failed.", e);
		} catch (Exception e) {
			logger.fatal("Execution failed.", e);
		}
	}
	
	public void notify(List<Object> l) {
		// Do the writing...
		try {
			writer.write(getFieldnames(), l.toArray());
		} catch (IOException e) {
			logger.fatal("Writing output failed.", e);
		} catch (NullPointerException e) {
			logger.fatal("No data found.", e);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FogBugz fb = new FogBugz();
		if (args.length <= 1) {
			fb.usage();
		} else {
			fb.init(args);
			fb.execute();
		}
	}
}
