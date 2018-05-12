package fi.teknologiakerho.viipal01ja.hcode;

public abstract class PositionCommand extends Command {
	
	public int x, y;
	
	public PositionCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String stringifyParams() {
		return x + " " + y;
	}

}
