package kafkademo01;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class KafkaDemoConsumer {
    private final static String TOPIC_NAME = "test01";
    private final static String CONSUMER_GROUP_NAME = "testGroup02";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        // 消费分组名
        props.put(ConsumerConfig.GROUP_ID_CONFIG, args[0]);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        //可以设置手动提交，默认是true，好处在于可以实现在提交之前的操作
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//创建一个消费者的客户端
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
// 消费者订阅主题列表
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        while (true) {
            /*
             * poll() API 是拉取消息的⻓轮询
             */
            ConsumerRecords<String, String> records =consumer.poll(Duration.ofMillis( 1000 ));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("收到消息：partition = %d,offset = %d, key =%s, value = %s%n", record.partition(),record.offset(), record.key(), record.value());
            }
            if (records.count() > 0 ) {
        // 手动异步提交offset，当前线程提交offset不会阻塞，可以继续处理后面的程序逻辑---对应了上面      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if (exception != null) {
                            System.err.println("Commit failed for " + offsets);
                            System.err.println("Commit failed exception: " +exception.getStackTrace());
                        }else{
                            System.out.println("接受消息成功");
                        }
                    }
                });
            }
        }
    }
}
