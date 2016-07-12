package edu.cmu.andrew.yilongc;


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

public class OaklandCrimeStats extends Configured implements Tool {

    public static class OaklandCrimeStatsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        // One occurrence
        private static final IntWritable one = new IntWritable(1);
        // X coordinate
        private static final float xCor = 1354326.897f;
        // Y coordinate
        private static final float yCor = 411447.7828f;
        // Max distance
        private static final int distance = 2000;
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
                String type = keys[4];

                // Type robbery
                if (type.equals("ROBBERY")) {
                    float x = Float.parseFloat(keys[0]);
                    float y = Float.parseFloat(keys[1]);
                    // Distance < 2000ft
                    if (Math.pow((double) (x - xCor), 2) + Math.pow((double) (y - yCor), 2) <= distance * distance) {
                        offense.set(type);
                        // Record the ROBBERY and number 1
                        context.write(offense, one);
                    }
                }
            }
        }
    }

    public static class OaklandCrimeStatsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            // Sum up each occurrence of an ROBBERY
            for (IntWritable value : values) {
                sum += value.get();
            }
            // Write the result to output
            context.write(key, new IntWritable(sum));
        }
    }

    public int run(String[] args) throws Exception {
        Job job = new Job(getConf());
        job.setJarByClass(OaklandCrimeStats.class);
        job.setJobName("Oakland crime stats");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(OaklandCrimeStatsMapper.class);
        job.setCombinerClass(OaklandCrimeStatsReducer.class);
        job.setReducerClass(OaklandCrimeStatsReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new OaklandCrimeStats(), args);
        System.exit(result);
    }

}

