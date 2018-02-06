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
	 * 发送事件 adb shell sendevent /dev/input/event4 %1 %2 %3
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
	 * 发送触模事件 type: 输入设备类型，在手机系统中经常使用的键盘（keyboard）和小键盘（kaypad）属于按键设
	 * 备EV_KEY，轨迹球属于相对设备EV_REL，触摸屏属于绝对设备EV_ABS code:
	 * 按键扫描码，区别于ASCII码和SDK中KeyEvent的键码 value: 附加码，1/0 down/up
	 * 
	 * @param type
	 * @param code
	 * @param value
	 * @return
	 */
	private static String sendEvent4(SendType sendType) {
		return "adb shell sendevent /dev/input/event4 " + sendType.type + " " + sendType.code + " " + sendType.value;
	}

	// 获得数据
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

	// 将adb后的数据保存到File中
	public static String saveAdbDataToFileCommand() {
		return "adb shell getevent /dev/input/event4 ";
	}

	public static String endAdbDataToFileCommand() {
		return "^C";
	}

}
