package test.myblog.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import test.myblog.model.Article;

public class KafkaDeserializer implements Deserializer<Article>{

	@Override
	public Article deserialize(String topic, byte[] data) {
		// TODO Auto-generated method stub
        Article article = null;
        try (ByteArrayInputStream byteInStream = new ByteArrayInputStream(data);
             ObjectInputStream objInStream = new ObjectInputStream(byteInStream)) {
            article = (Article) objInStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return article;
	}

}
