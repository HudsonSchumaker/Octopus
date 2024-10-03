package br.com.schumaker.force.framework.web.health;

/**
 * Data Transfer Object (DTO) for health information.
 *
 * @see HealthController
 * @see HealthService
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record HealthInfoDTO(String hostName, String ip, String jvmName, String javaVendor, String javaVersion, String osName,
                            String osVersion, String osArch, int cpus, double totalMemory, double usedMemory) {}
