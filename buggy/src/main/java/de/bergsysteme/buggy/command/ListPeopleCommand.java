package de.bergsysteme.buggy.command;

public class ListPeopleCommand extends ListCommand {
	@Override
	protected String getListCmd() {
		return "listPeople";
	}
}
