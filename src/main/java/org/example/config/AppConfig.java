package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(basePackages = "org.example.repository")
public class AppConfig {

//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }
//
//    @Bean
//    public HikariConnectionManager connectionManager(DataSource dataSource) {
//        return new HikariConnectionManager(dataSource);
//    }

}
