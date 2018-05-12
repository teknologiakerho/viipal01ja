package fi.teknologiakerho.viipal01ja;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import fi.teknologiakerho.viipal01ja.hcode.Command;
import fi.teknologiakerho.viipal01ja.hcode.HCode;

public class IoUtil {
	
	public static void saveHCode(File f, HCode hcode) {
		try(BufferedWriter out = new BufferedWriter(new FileWriter(f))){
			for(Command cmd : hcode.commands) {
				out.write(cmd.toString());
				out.write('\n');
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
