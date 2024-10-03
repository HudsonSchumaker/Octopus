package br.com.schumaker.force.framework.web.health;

import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.controller.Get;
import br.com.schumaker.force.framework.web.view.ResponseView;

/**
 * Controller class to provide health information about the application.
 *
 * @see HealthService
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Controller("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    /**
     * Returns the health status of the application.
     *
     * @return a string representing the health status.
     */
    @Get
    public ResponseView<String> health() {
        return ResponseView.ok().body(healthService.getHealthStatus()).build();
    }

    /**
     * Returns detailed health information about the application.
     *
     * @return a HealthInfoDTO object containing health information.
     */
    @Get("/info")
    public ResponseView<HealthInfoDTO> healthInfo() {
        return ResponseView.ok().body(healthService.getHealthInfo()).build();
    }
}
