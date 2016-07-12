package edu.cmu.andrew.yilongc;
/**
 * @author Yilong Chang
 */

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class PittsburghCrimeStats extends Configured implements Tool {

    public static class PittsburghCrimeStatsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        // One occurrence
        private final static IntWritable one = new IntWritable(1);
        // Offense type
        private Text offense = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            // Get line from input file. This was passed in by Hadoop as value.
            String line = value.toString();
            // Ignore the first line
            if (!line.startsWith("X")) {
                // Split the line by tab
                String[] keys = line.split("\t");
                // This offense field is at the fifth position of the line
                offense.set(keys[4]);
                // Record the offense and number 1
                context.write(offense, one);
            }
        }
    }

    public static class PittsburghCrimeStatsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            // Sum up each occurrence of an offense type
            for (IntWritable value : values) {
                sum += value.get();
            }
            // Write the result to output
            context.write(key, new IntWritable(sum));
        }
    }

    public int run(String[] args) throws Exception {
        Job job = new Job(getConf());
        job.setJarByClass(PittsburghCrimeStats.class);
        job.setJobName("Pittsburgh crime stats");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(PittsburghCrimeStatsMapper.class);
        job.setCombinerClass(PittsburghCrimeStatsReducer.class);
        job.setReducerClass(PittsburghCrimeStatsReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new PittsburghCrimeStats(), args);
        System.exit(result);
    }

}
