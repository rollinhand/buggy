package de.bergsysteme.fogbugz.command;

import de.bergsysteme.fogbugz.FogBugzException;
import de.bergsysteme.fogbugz.resolve.ResolverListener;


public interface ICommand {
	public void execute(String uri) throws FogBugzException;
	public String getCommand();
	public void addListener(ResolverListener listener);
	public void removeListener(ResolverListener listener);
}
