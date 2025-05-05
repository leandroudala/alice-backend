package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.udala.alice.application.port.CreateDataBaseUseCase;
import app.udala.alice.application.port.DataBaseRepository;
import app.udala.alice.application.port.GetDataBaseUseCase;
import app.udala.alice.application.usecase.CreateDataBaseMongo;
import app.udala.alice.application.usecase.GetDataBaseMongo;

@Configuration
public class DataBaseConfig {

    @Autowired
    private DataBaseRepository dataBaseRepository;

    @Bean
    public CreateDataBaseUseCase createDataBaseUseCase() {
        return new CreateDataBaseMongo(this.dataBaseRepository);
    }

    @Bean
    public GetDataBaseUseCase getDataBaseUseCase() {
        return new GetDataBaseMongo(this.dataBaseRepository);
    }
}
