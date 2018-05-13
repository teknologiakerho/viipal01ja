package fi.teknologiakerho.viipal01ja.hcode;

public class MoveCommand extends PositionCommand {
	
	public MoveCommand(double x, double y) {
		super(x, y);
	}

	@Override
	public char getCmd() {
		return 'M';
	}

}
