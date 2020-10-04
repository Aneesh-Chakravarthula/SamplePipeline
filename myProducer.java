import org.apache.kafka.common.errors.SerializationException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

public class myProducer
{
  public void main(String[] args)
  {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("key.serializer",    "io.confluent.kafka.serializers.KafkaAvroSerializer");
    props.put("value.serializer",  "io.confluent.kafka.serializers.KafkaAvroSerializer");

    props.put("schema.registry.url", schemaUrl);
    String topic = "customerContacts";

    Producer<String, Customer> producer = new KafkaProducer<String,   Customer>(props);

    // We keep producing new events until someone ctrl-c

    while (true)
    {
      Customer customer = CustomerGenerator.getNext();
      System.out.println("Generated customer " + customer.toString());
      ProducerRecord<String, Customer> record =  new ProducerRecord<>(topic, customer.getName(), customer);
      producer.send(record);
    }
  }
}
