/**
 * @author Yilong Chang
 */

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.apache.hadoop.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TotalFirstLastMatch extends Configured implements Tool {

    public static class TotalFirstLastMatchMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        // One occurrence
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // Get line from input file. This was passed in by Hadoop as value.
            String line = value.toString();
            // Tokenize the line
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                // Get the next token
                String t = tokenizer.nextToken();
                // Check if first and last matches
                if (t.length() == 1 || t.charAt(0) == t.charAt(t.length() - 1)) {
                    word.set("FirstLastMatch");
                    // Record the word and occurrence 1
                    context.write(word, one);
                }
            }
        }
    }

    public static class TotalFirstLastMatchReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            /* Sum all the occurrence of first last match*/
            for (IntWritable value : values) {
                sum += value.get();
            }
            key.set("TotalFirstLastMatch");
            // Write to output
            context.write(key, new IntWritable(sum));

        }
    }

    public int run(String[] args) throws Exception {

        Job job = new Job(getConf());
        job.setJarByClass(TotalFirstLastMatch.class);
        job.setJobName("totalfirstlastmatch");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(TotalFirstLastMatchMap.class);
        job.setCombinerClass(TotalFirstLastMatchReducer.class);
        job.setReducerClass(TotalFirstLastMatchReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new TotalFirstLastMatch(), args);
        System.exit(result);
    }

}