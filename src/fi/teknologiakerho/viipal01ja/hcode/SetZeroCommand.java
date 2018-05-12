package fi.teknologiakerho.viipal01ja.hcode;

public class SetZeroCommand extends Command {

	@Override
	public char getCmd() {
		return 'z';
	}

	@Override
	public String stringifyParams() {
		return "";
	}

}
