package com.iblogstreet.simulationclick;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class ReadFile {
	/**
	 * ¶ÁÈ¡ÃüÁîÎÄ¼þ
	 * 
	 * @return
	 */
	public static String readFile() {
		File file = new File("command.txt");
		FileReader fileReader = null;
		StringBuilder sBuilder = new StringBuilder();
		try {
			if (file.exists()) {
				fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null;

				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
					if (!line.isEmpty()) {
						sBuilder.append(line);
						sBuilder.append(" ");
					}
				}
				bufferedReader.close();
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sBuilder.toString();

	}

	public static void readConfigFile() {
		File file = new File("config");
		FileReader fileReader = null;
		StringBuilder sBuilder = new StringBuilder();
		try {
			if (file.exists()) {
				fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					ConstantField.sCount = Integer.parseInt(line);
					System.out.println("ConstantField.sCount:"+ConstantField.sCount);
				}
				bufferedReader.close();
				if (fileReader != null) {
					try {
						fileReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
