package test.myblog.kafka;

import java.util.Date;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import test.myblog.model.Article;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.ArticleDAO;

@Component
public class KafkaArticleConsumer {
	
	@Autowired
	private ArticleDAO ad;
	
	private Article consumedArticle;
	
	public Article getConsumedArticle() {
		return consumedArticle;
	}

	public void setConsumedArticle(Article consumedArticle) {
		this.consumedArticle = consumedArticle;
	}
	
	@KafkaListener(topics = "new_post")
	public Article consumeMessageId(ConsumerRecord<String, Article> consumerRecord) throws NumberFormatException, DAOException, ParseException {
		Article a = null;
        Optional<Article> optional = Optional.ofNullable(consumerRecord.value());
        if (optional.isPresent()) {
        	a = optional.get();
        }
        Article newPost = ad.create(a);
        setConsumedArticle(newPost);
        return newPost;
    }
}
