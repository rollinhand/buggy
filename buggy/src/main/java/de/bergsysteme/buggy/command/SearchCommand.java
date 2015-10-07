package de.bergsysteme.buggy.command;

import java.util.LinkedList;
import java.util.List;

public class SearchCommand extends DefaultCommand {
	// Query
	private String query;
	// Wanted Columns
	private List<String> columns;

	public SearchCommand() {
		columns = new LinkedList<String>();
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public void addColumn(String column) {
		columns.add(column);
	}
	
	public void addColumns(String[] columns) {
		for (String col : columns) { this.addColumn(col); }
	}
	
	protected String getSeparatedColumns() {
		StringBuffer separated = new StringBuffer();
		String value = null;
		for (String col : columns) { separated.append(col + ','); }
		// remove last comma
		if (separated.length() > 0) {
			value = separated.toString();
			value = value.substring(0, value.length() - 1);
		}
		
		return value.toString();
	}

	@Override
	public String toString() {
		String value = String.format("search&q=%s&cols=%s", getQuery(), getSeparatedColumns());
		return value;
	}

	public String getCommand() {
		return toString();
	}
}
