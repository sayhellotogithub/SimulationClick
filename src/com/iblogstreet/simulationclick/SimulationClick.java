package com.iblogstreet.simulationclick;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.iblogstreet.simulationclick.Utils.EventType;

//这个工程主要用于模拟点击
public class SimulationClick {
	private static CaptureSimulationData captureSimulationData;

	public static void main(String[] args) {
		// String mString = "0003 0039 00000016 " + "0003 0035 00000235 " +
		// "0003 0036 00000255 " + "0003 003a 000000a8 "
		// + "0000 0000 00000000 " + "0003 0039 ffffffff " + "0000 0000 00000000
		// ";
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("请输入 1 捕捉点击事件 2 退出捕捉事件 3 开始摸拟点击 4 退出");
			String line = "";
			try {
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println("line:" + line);
					if (line.trim().equals("1")) {
						captureCommand();
						;
					} else if (line.trim().equals("2")) {
						endCaptureCommand();
						break;
					} else if (line.trim().equals("3")) {
						startSimulationClick();
					} else if (line.trim().equals("4")) {
						bufferedReader.close();
						exitProcess();
						return;
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 捕获命令
	public static void captureCommand() {
		System.out.println("captureCommand");
		if (captureSimulationData == null) {
			captureSimulationData = new CaptureSimulationData();
		}
		captureSimulationData.startCaptureSimulationData();
	}

	public static void endCaptureCommand() {
		System.out.println("endCaptureCommand");
		if (captureSimulationData != null)
			captureSimulationData.endCaptureSimulationData();
	}

	public static void startSimulationClick() {
		String mString = ReadFile.readFile();
		ReadFile.readConfigFile();
		String[] mList = mString.split(" ");

		List<SendType> mSendTypes = new ArrayList<>();
		SendType sendType = null;
		for (int i = 0; i < mList.length; i++) {
			if (i % 3 == 0) {
				sendType = new SendType();
				sendType.type = Integer.valueOf(mList[i], 16) + "";// 将16进制转成10进制

			} else if (i % 3 == 1) {
				sendType.code = Long.valueOf(mList[i], 16) + "";
			} else {
				sendType.value = Long.valueOf(mList[i], 16) + "";
				mSendTypes.add(sendType);
			}
		}
		int number = 0;
		while (number < ConstantField.sCount) {
			for (SendType sendType1 : mSendTypes) {
				System.out.println(sendType1.toString());
				try {
					if (sendType1.code.equals("0") && sendType1.value.equals("0"))
						Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Utils.sendEvent(sendType1, EventType.EVENT4);
			}
			number++;
		}
	}

	public static void exitProcess() {
		System.out.println("exit");
		System.exit(-1);
	}

}
