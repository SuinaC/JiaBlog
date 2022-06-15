package com.jia.job;

import com.jia.domain.entity.Article;
import com.jia.service.ArticleService;
import com.jia.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/150 * * * * ?")
    public void updateViewCount(){
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles = cacheMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
