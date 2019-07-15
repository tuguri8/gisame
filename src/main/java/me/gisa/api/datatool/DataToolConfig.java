package me.gisa.api.datatool;

import feign.Feign;
import feign.RequestInterceptor;
import feign.Retryer;
import me.gisa.api.datatool.daum.DaumSearchClient;
import me.gisa.api.datatool.naver.NaverClient;
import me.gisa.api.datatool.rss.RssClient;
import me.gisa.api.datatool.siseme.SisemeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@Configuration
public class DataToolConfig {
    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", clientId);
            requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }

    @Bean
    public SisemeClient sisemeClient() {
        return Feign.builder()
                    .contract(new SpringMvcContract())
                    .retryer(new Retryer.Default())
                    .target(SisemeClient.class, "siseme-client");
    }

    @Bean
    public NaverClient naverClient() {
        return Feign.builder()
                    .contract(new SpringMvcContract())
                    .retryer(new Retryer.Default())
                    .target(NaverClient.class, "naver-client");
    }

    @Bean
    public DaumSearchClient daumSearchClient() {
        return Feign.builder()
                    .contract(new SpringMvcContract())
                    .retryer(new Retryer.Default())
                    .target(DaumSearchClient.class, "daum-client");
    }

    @Bean
    public RssClient rssClient() {
        return Feign.builder()
                    .contract(new SpringMvcContract())
                    .retryer(new Retryer.Default())
                    .target(RssClient.class, "rss-client");
    }
}
