package dk.sdu.cbse.core;

import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Configuration
public class ModuleConfig {

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return ServiceLoader
                .load(IGamePluginService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServices() {
        return ServiceLoader
                .load(IEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return ServiceLoader
                .load(IPostEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }
    @Bean
    public ScoringClient scoringClient() {
        return new ScoringClient();
    }

    @Bean
    public App app(
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessingService> entityProcessingServices,
            List<IPostEntityProcessingService> postEntityProcessingServices,
            ScoringClient scoringClient
    )
    {
        return new App(gamePluginServices, entityProcessingServices, postEntityProcessingServices,scoringClient);
    }
}
