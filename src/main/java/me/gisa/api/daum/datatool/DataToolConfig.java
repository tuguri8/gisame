package me.gisa.api.daum.datatool;

import feign.Feign;
import feign.Retryer;
import me.gisa.api.daum.datatool.daum.DaumSearchClient;
import me.gisa.api.daum.datatool.siseme.SisemeClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@Configuration
public class DataToolConfig {
    @Bean
    public SisemeClient sisemeClient() {
        return Feign.builder()
                    .contract(new SpringMvcContract())
                    .retryer(new Retryer.Default())
                    .target(SisemeClient.class, "api-client");
    }

    @Bean
    public DaumSearchClient daumSearchClient() {
        return Feign.builder()
                    .contract(new SpringMvcContract())
                    .retryer(new Retryer.Default())
                    .target(DaumSearchClient.class, "daum-client");
    }
}
