package de.bergsysteme.buggy.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class ListCommand extends DefaultCommand {
	Map<String,String> arguments = new HashMap<String, String>();

	public void addArgument(String argument, String value) {
		if (!arguments.containsKey(argument)) {
			arguments.put(argument, value);
		}
	}
	
	protected abstract String getListCmd();
	
	public void removeArgument(String argument) {
		if (arguments.containsKey(argument)) {
			arguments.remove(argument);
		}
	}
	
	protected String getArgument(String argument) {
		return arguments.get(argument);
	}
	
	protected String[] listArguments() {
		Set<String> set = arguments.keySet();
		return (String[]) set.toArray(new String[0]);
	}
	
	protected String buildCommand() {
		String args = "";
		for (String arg : listArguments()) {
			 args += String.format("&%s=%s", arg, getArgument(arg));
		}
		String cmd = String.format("%s%s", getListCmd(), args);
		return cmd;
	}
	
	@Override
	public String getCommand() {
		return buildCommand();
	}
}
