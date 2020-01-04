package exercise4;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.Trigger;

public class KafkaSparkStreamEvens {
    public static void main(String[] args) throws Exception {

        SparkSession ss = SparkSession.builder()
                .appName("KafkaSparkStream")
                .master("local[2]")
                .getOrCreate();

        ss.sparkContext().setLogLevel("WARN");
        String kafkaBrokers = "127.0.0.1:9092";

        Dataset<Row> df = ss.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", kafkaBrokers)
                .option("value.deserializer", IntegerDeserializer.class.getName())
                .option("subscribe", "evens")
                .load();

        df.printSchema();


        df.createOrReplaceTempView("table1");
        ss.sql("Select count(*) from table1").writeStream()
                .outputMode("update")
                .format("console")
                .trigger(Trigger.ProcessingTime(5000))
                .start().awaitTermination();



    }


}
