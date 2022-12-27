package org.modak.challenge.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "db.datasource")
public class DataSourceConfig extends HikariConfig {

    private String url;
    private String username;
    private String password;

    @Bean
    public DataSource getDataSource() {
        super.setJdbcUrl(url);
        super.setUsername(username);
        super.setPassword(password);

        System.out.println("jdbcUrl: " + url);

        return new HikariDataSource(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
