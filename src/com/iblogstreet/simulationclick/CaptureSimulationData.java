package com.iblogstreet.simulationclick;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 捕获数据
 * 
 * @author Administrator
 *
 */
public class CaptureSimulationData implements Runnable {
	private Process process;
	private boolean isEnd;

	public void startCaptureSimulationData() {
		try {
			File file = new File("command.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			if (process != null) {
				process.exitValue();
				process = null;
			}
			isEnd = false;
			process = Runtime.getRuntime().exec(saveAdbDataToFileCommand());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			String line = bufferedReader.readLine();
			while (line != null && !isEnd) {
				System.out.println(line);
				line = bufferedReader.readLine();
				bufferedWriter.write(line);
				bufferedWriter.newLine();// System.getProperty("line.separator")
				// bufferedWriter.write(System.getProperty("line.separator"));
				bufferedWriter.flush();
			}
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将adb后的数据保存到File中
	private String saveAdbDataToFileCommand() {
		return "adb shell getevent /dev/input/event4 ";
	}

	public void endCaptureSimulationData() {
		if (process != null)
			process.exitValue();
		process = null;
		isEnd = true;
	}

	@Override
	public void run() {
		startCaptureSimulationData();
	}

}
