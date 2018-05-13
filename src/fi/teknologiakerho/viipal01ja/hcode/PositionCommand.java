package fi.teknologiakerho.viipal01ja.hcode;

public abstract class PositionCommand extends Command {
	
	public double x, y;
	
	public PositionCommand(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String stringifyParams() {
		return x + " " + y;
	}

}
