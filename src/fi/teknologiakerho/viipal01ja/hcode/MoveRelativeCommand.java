package fi.teknologiakerho.viipal01ja.hcode;

public class MoveRelativeCommand extends PositionCommand {

	public MoveRelativeCommand(int x, int y) {
		super(x, y);
	}

	@Override
	public char getCmd() {
		return 'm';
	}

}
