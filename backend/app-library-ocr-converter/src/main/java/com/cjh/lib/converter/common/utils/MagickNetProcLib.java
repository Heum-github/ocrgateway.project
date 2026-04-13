package com.cjh.lib.converter.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagickNetProcLib {
	
	public static int getPdfPageCount(String exeFile, String inputPath) {
		
		StringBuilder outTxt = new StringBuilder();
		StringBuilder errTxt = new StringBuilder();
		
		List<String> switches = new ArrayList<>();
		
		if (!inputPath.startsWith("\"")) {
			inputPath = "\"" + inputPath + "\"";
		}
		
		String osTypeName = System.getProperty("os.name").toLowerCase().trim();
        switch (osTypeName) {
            case "windows":
            case "windows 10":
            	switches.add("identify");
                switches.add(inputPath);
                break;
            case "linux":
            case "osx":
                switches.add("-format %n\n " + inputPath + " | head -n1");
                break;
        }

        executeCommand(exeFile, switches, outTxt, errTxt);
        
        int lastLineNumber = -1;
        
        if (outTxt != null && outTxt.length() != 0) {
        	Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
        	
        	Matcher matcher = pattern.matcher(outTxt);
        	
            while (matcher.find()) {
                String lineNumberString = matcher.group(1);
                lastLineNumber = Integer.parseInt(lineNumberString);
            }
        }
        
        return lastLineNumber;
	}
	
    public static int[] getWidthHeight(String exeFile, String inputPath) {
        int width = 0;
        int height = 0;
        
		StringBuilder outTxt = new StringBuilder();
		StringBuilder errTxt = new StringBuilder();
		
		if (!inputPath.startsWith("\"")) {
			inputPath = "\"" + inputPath + "\"";
		}
		
        List<String> switches = new ArrayList<>();
        switches.add("identify");
        switches.add("-format");
        switches.add("%wx%h");
        switches.add(inputPath);

        executeCommand(exeFile, switches, outTxt, errTxt);
        
        if (outTxt != null && outTxt.length() != 0) {
        	String[] strArr = outTxt.toString().split("x");
        	width = Integer.parseInt(strArr[0]);
        	height = Integer.parseInt(strArr[1].replace("\n", ""));
        }

        return new int[]{width, height};
    }
	
	public static boolean executeCommand(String exeFile, List<String> switches, StringBuilder outTxt, StringBuilder errTxt) {
		boolean rValue = false;
		outTxt.setLength(0);
		errTxt.setLength(0);
		
		StringJoiner params = new StringJoiner(" ");
		for (String switche : switches) {
			params.add(switche);
		}
		
		try {
			ProcessBuilder procBuilder = new ProcessBuilder("cmd","/c", exeFile, params.toString());
			procBuilder.redirectErrorStream(true);
			
			//Process process = procBuilder.start();
			Process process = Runtime.getRuntime().exec("cmd /c " + exeFile + " " + params.toString());
			
			//read stdout
			BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while((line=outputReader.readLine()) != null) {
				outTxt.append(line).append("\n");
			}
			
			BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while((line=errReader.readLine()) != null) {
				errTxt.append(line).append("\n");
			}
			
			process.waitFor();
			
			System.gc();
			System.runFinalization();
			
			rValue = process.exitValue() == 0;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return rValue;
	}
}
