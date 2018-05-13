package fi.teknologiakerho.viipal01ja.hcode;

public class MoveRelativeCommand extends PositionCommand {

	public MoveRelativeCommand(double x, double y) {
		super(x, y);
	}

	@Override
	public char getCmd() {
		return 'm';
	}

}
