package fi.teknologiakerho.viipal01ja.hcode;

public class MoveZCommand extends Command {
	
	public boolean up;
	
	public MoveZCommand(boolean up) {
		this.up = up;
	}

	@Override
	public char getCmd() {
		return 'Z';
	}

	@Override
	public String stringifyParams() {
		return up ? "1" : "0";
	}

}
