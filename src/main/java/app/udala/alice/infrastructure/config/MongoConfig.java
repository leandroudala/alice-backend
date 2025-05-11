package app.udala.alice.infrastructure.config;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MongoConfig.class);
    
    @PostConstruct
    public void timeZoneSetup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        LOG.info("Default timezone set to UTC");
    }
}
