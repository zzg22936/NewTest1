package zzg.njnu;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
//import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Temperature {
	
//	public static final Logger log = Logger.getLogger("Temperature.zzg.njnu");
	
	public static class TempMapper extends
			Mapper<Object, Text, Text, LongWritable> {
		private static int t = 0;
		private static final int MissingData = 9999;
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String linstring = value.toString();
			String year = linstring.substring(15, 19);
			
			int temp; 
			//87- 92is temperature ;93 is quality;
			if (linstring.charAt(87) == '+') {
				temp = Integer.parseInt(linstring.substring(88, 92));
			} else {
				temp = Integer.parseInt(linstring.substring(87, 92));
			}
			String qulity = linstring.substring(92,93);
			
			if(temp != MissingData && qulity.matches("[01459]")){
				context.write(new Text(year),new LongWritable(temp));
	//			log.info(year+":"+temp);
			t++;
			}
	//		System.out.println(t);
		}
		
	}
	
	public static class TempReducer extends
			Reducer<Text , LongWritable , Text, LongWritable>{
		public void reduce(Text key,Iterable<LongWritable> values,Context context)
				throws IOException, InterruptedException {
			long a = Integer.MIN_VALUE;
			for(LongWritable ite: values){
				if(ite.get() > a)
					a = ite.get();
			}
			context.write(key, new LongWritable(a));
		}
	}
	
	public static void main(String args[])throws Exception{
	
		Configuration conf = new Configuration();
		String otherArgs[] = new GenericOptionsParser(conf,args).getRemainingArgs();
	//	String s= "0029029070999991901010106004+64333+023450FM-12+000599999V0202701N015919999999N0000001N9-00781";
	//	System.out.println(s.length());
		SimpleLayout layout = new SimpleLayout(); 
		FileAppender fileAppender = null;
		try {
			fileAppender = new FileAppender(layout,"result.txt",false);
		} catch (Exception e) {
			throw (e);
		}
		
	//	log.addAppender(fileAppender);
	//	log.setLevel((Level)Level.INFO);
		
		Job job = new Job(conf,"MyTemp");
		job.setJarByClass(Temperature.class);
		job.setMapperClass(TempMapper.class);
		job.setReducerClass(TempReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileInputFormat.setInputPaths(job, args[0]);
		Path path = new Path(args[1]);
		//delete the already existed file path;
		FileSystem.get(conf).delete(path, true);
		FileOutputFormat.setOutputPath(job, path);
		long start = System.currentTimeMillis();
		System.out.println(start);
	//	System.exit(job.waitForCompletion(true) ? 1 : 0);
		job.waitForCompletion(true);
		long end = System.currentTimeMillis();
		System.out.println(end);
		long dur = end - start ;
		System.out.println("it takes "+ dur+"ms");
	//	log.info("运行需要:"+ dur+"毫秒");
	}
}
