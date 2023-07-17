package test.myblog.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import test.myblog.model.Article;
import test.myblog.model.Draft;
import test.myblog.service.ArticleService;
import test.myblog.util.PostUtils;
import test.myblog.kafka.KafkaArticleConsumer;
import test.myblog.kafka.KafkaArticleProducer;


@RestController
@RequestMapping("/api/v1/newPost")
@CrossOrigin(origins="http://localhost:5173")
public class NewPostController {
	
	@Autowired
	private KafkaArticleProducer kap;
	
	@Autowired
	private KafkaArticleConsumer kac;
	
	@Autowired
	private PostUtils pu;

	@PostMapping("/saveNewPost")
	public ResponseEntity<Article> saveNewPost(@RequestBody Map<String, Object> postInfo) throws Exception {
		Article a = pu.postInfoParser(postInfo);
		kap.sendMessageAsync("new_post", a.getT().getT_id(), a.getT().getT_name(), a);
		return ResponseEntity.ok(kac.getConsumedArticle());
	}

}
