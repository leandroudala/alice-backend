package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.udala.alice.application.port.BaseUseCase;
import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.application.port.GetBaseUseCase;
import app.udala.alice.application.port.ManageBaseUseCase;
import app.udala.alice.application.usecase.CreateBaseMongoUseCase;
import app.udala.alice.application.usecase.GetBaseMongoUseCase;
import app.udala.alice.application.usecase.ManageBaseMongoUseCase;

@Configuration
public class BaseConfig {

    @Autowired
    private BaseRepository baseRepository;

    @Bean
    public BaseUseCase createBaseUseCase() {
        return new CreateBaseMongoUseCase(this.baseRepository);
    }

    @Bean
    public GetBaseUseCase getBaseUseCase() {
        return new GetBaseMongoUseCase(this.baseRepository);
    }

    @Bean
    public ManageBaseUseCase getManageBaseUseCase() {
        return new ManageBaseMongoUseCase(this.baseRepository);
    }
}
