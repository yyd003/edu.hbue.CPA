package edu.hbue.CPA.common;

import java.io.File;
import java.io.IOException;

public class EXEC{
	public static void main(String[] args) {

		Runtime rn = Runtime.getRuntime();
		Process p = null;		
		try {
			//			rn.getRuntime().exec("cmd \"D:\\CPA\\Single File\\cmd\\cmd.exe\" /t:0F");			
			System.out.println("cmd /c  cmd D:\\CPA\\1.exe -n");
			rn.getRuntime().exec("cmd /c  cmd D:\\CPA\\1.exe -n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}