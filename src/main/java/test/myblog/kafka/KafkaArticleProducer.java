package test.myblog.kafka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import test.myblog.model.Article;

@Component
public class KafkaArticleProducer {
	
	private final KafkaTemplate<String, Article> kafkaTemplate;

    public KafkaArticleProducer(KafkaTemplate<String, Article> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageSync(String topic, Integer partId, String key, Article a) throws InterruptedException, ExecutionException, TimeoutException {
        ListenableFuture<SendResult<String, Article>> future = kafkaTemplate.send(topic, partId, key, a);
        SendResult<String, Article> result = future.get(3, TimeUnit.SECONDS);
        System.out.println("SentMsg: " + result.getProducerRecord().value());
    }
    
    public void sendMessageAsync(String topic, Integer partId, String key, Article a) {
        kafkaTemplate.send(topic, partId, key, a);
        System.out.println("Message was sent.");
    }
    
    @PostConstruct
    private void producerListener() {
        kafkaTemplate.setProducerListener(new ProducerListener<String, Article>() {
            @Override
            public void onSuccess(ProducerRecord<String, Article> producerRecord, RecordMetadata recordMetadata) {
                System.out.println("Suceeded. message=" + producerRecord.value());
            }
            
            @Override
            public void onError(ProducerRecord<String, Article> producerRecord, RecordMetadata recordMetadata, Exception exception) {
            	 System.out.println("Failed. message=" + producerRecord.value());
            }
        });
    }
}
