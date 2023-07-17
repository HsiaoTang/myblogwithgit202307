package test.myblog.kafka;

import org.apache.kafka.common.serialization.Serializer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import test.myblog.model.Article;

public class KafkaSerializer implements Serializer<Article> {

	@Override
	public byte[] serialize(String topic, Article a) {
		// TODO Auto-generated method stub
		byte[] byteArray = null;
		try (ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			 ObjectOutputStream objOutStream = new ObjectOutputStream(byteOutStream)){
			 objOutStream.writeObject(a);
			 byteArray = byteOutStream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return byteArray;
	}

}
