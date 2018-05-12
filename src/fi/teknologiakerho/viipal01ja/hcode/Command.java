package fi.teknologiakerho.viipal01ja.hcode;

public abstract class Command {
	
	public abstract char getCmd();
	
	public abstract String stringifyParams();
	
	@Override
	public String toString() {
		return getCmd() + " " + stringifyParams();
	}

}
