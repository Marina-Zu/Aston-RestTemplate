package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.db.HikariConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example")
//@PropertySource("classpath:db.properties")
//@EnableJpaRepositories(basePackages = "org.example.repository")
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(new JsonMapper());
        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(jsonConverter);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("db.driver-class-name");
        config.setJdbcUrl("db.url");
        config.setUsername("db.username");
        config.setPassword("db.password");
        return config;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }
    @Bean
    public HikariConnectionManager connectionManager(DataSource dataSource) {
        return new HikariConnectionManager(dataSource);
    }

//    @Bean
//    public DataSource dataSource(Environment env) {
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName(env.getRequiredProperty("db.driver-class-name"));
//        config.setJdbcUrl(env.getRequiredProperty("db.url"));
//        config.setUsername(env.getRequiredProperty("db.username"));
//        config.setPassword(env.getRequiredProperty("db.password"));
//
//        return new HikariDataSource(config);
//    }
}
