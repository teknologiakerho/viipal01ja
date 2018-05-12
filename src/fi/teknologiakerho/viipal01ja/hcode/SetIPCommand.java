package fi.teknologiakerho.viipal01ja.hcode;

public class SetIPCommand extends Command {
	
	public int ip;
	
	public SetIPCommand(int ip) {
		this.ip = ip;
	}

	@Override
	public char getCmd() {
		return 'I';
	}

	@Override
	public String stringifyParams() {
		return ""+ip;
	}

}
