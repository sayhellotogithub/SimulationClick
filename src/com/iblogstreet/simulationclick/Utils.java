package com.iblogstreet.simulationclick;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {
	public enum EventType {
		EVENT4, EVENT1, EVENT2, EVENT3
	}

	public static void callCMD() {
		try {
			Process process = Runtime.getRuntime().exec("cmd.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void callAdb() {
		String command = "adb devices";
		System.out.println(command);
		ArrayList devices = new ArrayList();
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = bufferedReader.readLine();
			while (line != null) {
				System.out.println(line);
				if (line.endsWith("device")) {
					String device = line.substring(0, line.length() - "device".length()).trim();
					devices.add(device);
				}
				line = bufferedReader.readLine();
			}
			// System.out.println(devices.toString());
		} catch (Exception ex) {

		}

	}

	/**
	 * �����¼� adb shell sendevent /dev/input/event4 %1 %2 %3
	 * 
	 * @param msg
	 * @param eventType
	 */
	public static void sendEvent(SendType sendType, EventType eventType) {
		// adb shell sendevent /dev/input/event4 %1 %2 %3
		String command = "";
		if (eventType == EventType.EVENT4) {
			command = sendEvent4(sendType);
		} else {
			System.out.println("no event type");
		}

		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���ʹ�ģ�¼� type: �����豸���ͣ����ֻ�ϵͳ�о���ʹ�õļ��̣�keyboard����С���̣�kaypad�����ڰ�����
	 * ��EV_KEY���켣����������豸EV_REL�����������ھ����豸EV_ABS code:
	 * ����ɨ���룬������ASCII���SDK��KeyEvent�ļ��� value: �����룬1/0 down/up
	 * 
	 * @param type
	 * @param code
	 * @param value
	 * @return
	 */
	private static String sendEvent4(SendType sendType) {
		return "adb shell sendevent /dev/input/event4 " + sendType.type + " " + sendType.code + " " + sendType.value;
	}

	// �������
	public static void getEvent4() {
		try {
			File file = new File("command.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			Process process = Runtime.getRuntime().exec(saveAdbDataToFileCommand());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = bufferedReader.readLine();
			while (line != null) {
				System.out.println(line);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��adb������ݱ��浽File��
	public static String saveAdbDataToFileCommand() {
		return "adb shell getevent /dev/input/event4 ";
	}

	public static String endAdbDataToFileCommand() {
		return "^C";
	}

}
