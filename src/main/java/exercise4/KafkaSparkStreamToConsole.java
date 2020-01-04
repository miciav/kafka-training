package exercise4;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.spark.SparkConf;
import org.apache.spark.serializer.KryoSerializer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.Trigger;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.functions;

public class KafkaSparkStreamToConsole {
    public static void main(String[] args) throws Exception {

        SparkSession ss = SparkSession.builder()
                .appName("KafkaSparkStream")
                .master("local[2]")
                .getOrCreate();

        ss.sparkContext().setLogLevel("ERROR");

        String kafkaBrokers = "127.0.0.1:9092";

        Dataset<Row> df = ss.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", kafkaBrokers)
                .option("value.deserializer", StringDeserializer.class.getName())
                .option("value.serializer", StringSerializer.class.getName())
                .option("subscribe", "my_topic")
                .option("startingOffsets", "earliest")
                .load();

        df.printSchema();


        StructType mySchema = new StructType(new StructField[]{
                new StructField("name", DataTypes.StringType, true, Metadata.empty()),
                new StructField("id", DataTypes.StringType, true, Metadata.empty()),
                new StructField("firstname", DataTypes.StringType, true, Metadata.empty()),
                new StructField("lastname", DataTypes.StringType, true, Metadata.empty()),
                new StructField("address", DataTypes.StringType, true, Metadata.empty()),
                new StructField("timestamp", DataTypes.TimestampType, true, Metadata.empty()),
                new StructField("gender", DataTypes.StringType, true, Metadata.empty()),
                new StructField("arrested", DataTypes.BooleanType, true, Metadata.empty()),
                new StructField("age", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("race", DataTypes.StringType, true, Metadata.empty())
        });

        Dataset<Row> df1 = df.selectExpr("CAST(value AS STRING)", "CAST(timestamp AS TIMESTAMP)");
        df1.printSchema();

        Dataset<Row> df2 = df1.withColumn("data",functions.from_json(df1.col("value"), mySchema))
                .select("data.race",
                        "data.gender",
                        "data.lastname",
                        "data.firstname",
                        "data.arrested",
                        "data.age", "timestamp");

        df2.printSchema();

        df2.createOrReplaceTempView("mytable");
        Dataset<Row> df3 = ss.sql("SELECT race, count(race) from mytable group by race");


        StreamingQuery streamingQuery1 = df3.writeStream()
                .outputMode("update")
                .format("console")
                .trigger(Trigger.ProcessingTime(5000))
                .start();

        streamingQuery1.awaitTermination(60000);


    }


}
