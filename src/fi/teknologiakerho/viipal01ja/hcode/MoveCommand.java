package fi.teknologiakerho.viipal01ja.hcode;

public class MoveCommand extends PositionCommand {
	
	public MoveCommand(int x, int y) {
		super(x, y);
	}

	@Override
	public char getCmd() {
		return 'M';
	}

}
