package kafkademo01;

import com.example.dto.TUser;
import com.example.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 消息的发送
 */
public class KafkaDemoProducer {
    private final static String TOPIC_NAME = "test01";
    public static void main(String[] args) throws JsonProcessingException, ExecutionException, InterruptedException {
        Properties props = new Properties();
        //导入kafka的连接
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//把发送的key从字符串序列化为字节数组
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//把发送消息value从字符串序列化为字节数组
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<String,String>(props);

        TUser user = new TUser(1,"majian","123456");
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_NAME, "2", JsonUtil.toJson(user));
        RecordMetadata metadata = producer.send(producerRecord).get();
//=====阻塞=======
        System.out.println("同步方式发送消息结果：" + "topic-" +metadata.topic() + "|partition-"+ metadata.partition() + "|offset-" +metadata.offset());
    }
}
