package zzg.njnu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import sun.net.www.content.text.plain;

public class Temper {
	public static void main(String args[]) throws Exception{
		int max_1901= Integer.MIN_VALUE, max_1902 = Integer.MIN_VALUE;
		String f_01 = "";
		String f_02 = "";
		max_1901 = getFileMax(f_01);
		max_1902 = getFileMax(f_02);
	}
	private static int getFileMax(String s) throws IOException{
		File file = new File(s);
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		
		return 1;
	}
}
