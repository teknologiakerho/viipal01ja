package fi.teknologiakerho.viipal01ja.hcode;

public class ResetCommand extends Command {

	@Override
	public char getCmd() {
		return 'R';
	}

	@Override
	public String stringifyParams() {
		return "";
	}

}
