package de.bergsysteme.buggy.command;

import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.resolve.ResolverListener;


public interface ICommand {
	public void execute(String uri) throws FogBugzException;
	public String getCommand();
	public void addListener(ResolverListener listener);
	public void removeListener(ResolverListener listener);
}
