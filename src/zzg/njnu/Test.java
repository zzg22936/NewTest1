package zzg.njnu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
	//	String string = "0029029070999991901010106004+64333+023450FM-12+000599999V0202701N015919999999N0000001N9-00781";
		File file = new File("/home/zzg/workspace/Temperature/result.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String stringlineString = null;
		int max_1 = Integer.MIN_VALUE;
		int max_2 = Integer.MIN_VALUE;
		String s1 =null,s2 =null;
		while ((stringlineString = br.readLine())!= null) {
			String subyearString = stringlineString.substring(7, 11);
			String subString = stringlineString.substring(12);
			int temp = Integer.parseInt(subString);
			if(subyearString.equals("1901")){
				if(temp > max_1)
					{max_1 = temp;s1= stringlineString;}
					
			}else {
				if(temp > max_2)
					{max_2 = temp;s2 = stringlineString;}
			}
		}
		System.out.println("1901:"+max_1+";1902:"+max_2);
		System.out.println(s1);
		System.out.println(s2);
	//	System.out.println(string.length());
		}

}
