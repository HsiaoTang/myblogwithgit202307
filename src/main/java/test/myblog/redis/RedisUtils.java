package test.myblog.redis;

import java.util.Collection;
import java.util.List;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;

import org.springframework.stereotype.Component;

import test.myblog.service.ArticleService;
import test.myblog.config.RedisConfig;
import test.myblog.model.Tag;

@Component
public class RedisUtils {
	
	@Autowired
	private ArticleService as;
	
	@Autowired
	private RedisTemplate<String, Object> rt;
	
	public void putArticleInRedis(Map<String, Object> articleInfo) {
		rt.opsForHash().putAll("article" + articleInfo.get("a_id"), articleInfo);
	}
	
	public Map<Object, Object> getArticleFromRedis(String articleSeq) {
		System.out.println(rt.opsForHash().entries(articleSeq));
		return rt.opsForHash().entries(articleSeq);
	}
	
	public Boolean removeArticleFromRedis(String articleSeq) {
		System.out.println(rt.delete(articleSeq));
		return rt.delete(articleSeq);
	}
	
	public void batchPutPipeline(List<Map<String, Object>> articleList) {
	    List<Object> pipeResult = rt.executePipelined(new SessionCallback<Object>() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				// TODO Auto-generated method stub
				for(int i = 0; i < articleList.size(); i++) {
					Integer aId = (Integer)articleList.get(i).get("a_id");
					operations.opsForHash().putAll("article" + aId, articleList.get(i));
				}
				return null;
			}
	    });
	}
	
	public List<Object> batchGetPipeline(List<Map<String, Object>> articleList) {
	    List<Map<Object, Object>> results = new ArrayList<>();

	    List<Object> pipeResult = rt.executePipelined(new SessionCallback<Object>() {
	    	
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				// TODO Auto-generated method stub
				for(int i = 0; i < articleList.size(); i++) {
					Map<Object, Object> draftInfo = operations.opsForHash().entries("article" + articleList.get(i).get("a_id"));
					results.add(draftInfo);
				}
				return null;
			}
	    });
	    System.out.println(results);
	    System.out.println(pipeResult);
	    return pipeResult;
	}
	
	public List<Object> batchRemovePipeline(List<Map<String, Object>> draftList) {
	    List<Long> results = new ArrayList<>();

	    List<Object> pipeResult = rt.executePipelined(new SessionCallback<Object>() {
	    	
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				// TODO Auto-generated method stub
				for(int i = 0; i < draftList.size(); i++) {
					Long removeCounts = operations.opsForHash().delete("article" + draftList.get(i).get("d_id"));
					results.add(removeCounts);
				}
				return null;
			}
	    });
	    System.out.println(results);
	    System.out.println(pipeResult);
	    return pipeResult;
	}
	
	public void putArticleListByTidInRedis(List<List<Map<String, Object>>> aLBT) {
		for(int i = 0; i < aLBT.size(); i++) {
			final int idx = i;
			RedisConfig.setDatabase(i);
			List<Object> pipeResult = rt.executePipelined(new SessionCallback<Object>() {
				@Override
				public Object execute(RedisOperations operations) throws DataAccessException {
					// TODO Auto-generated method stub
					for(Integer j = 0; j < aLBT.get(idx).size(); j++) {
						Integer mId = (Integer)aLBT.get(idx).get(j).get("m_id");
						Integer aId = (Integer)aLBT.get(idx).get(j).get("a_id");
						operations.opsForHash().putAll(mId + "_" + aId, aLBT.get(idx).get(j));
					}
					return null;
				}
		    });
		}
	}
	
	public List<List<Map<String, Object>>> getALByTagFromRedis(List<Tag> tL) {
		List<List<Map<String, Object>>> alByTag = new ArrayList<>();
		for(int i = 0; i < tL.size(); i++) {
			RedisConfig.setDatabase(i);
			List<Map<String, Object>> al = new ArrayList<>();
			rt.execute((RedisCallback<Void>) connection -> {
				ScanOptions options = ScanOptions.scanOptions().match("*").count(100).build();
				try(Cursor<byte[]> keysCursor = connection.scan(options)){
		            while (keysCursor.hasNext()) {
		                byte[] keyBytes = keysCursor.next();
		                String key = new String(keyBytes, StandardCharsets.UTF_8);
		                if (connection.type(keyBytes) == DataType.HASH) {
		                    Map<byte[], byte[]> entries = connection.hGetAll(keyBytes);
		                    Map<String, Object> a = new HashMap<>();
		                    for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
		                        String field = new String(entry.getKey(), StandardCharsets.UTF_8);
		                        String value = new String(entry.getValue(), StandardCharsets.UTF_8).replaceAll("^\"|\"$", "");
		                        a.put(field, value);
		                    }
		                    al.add(a);
		                }
		            }
				} catch (Exception e) {
					e.printStackTrace();
				}
		        return null;
		    });
			alByTag.add(al);
		}
		return alByTag;
	}

	
}
