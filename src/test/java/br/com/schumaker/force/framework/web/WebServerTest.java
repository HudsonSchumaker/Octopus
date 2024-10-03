package br.com.schumaker.force.framework.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * WebServer test class.
 * This class is responsible for testing the WebServer class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class WebServerTest {
    private WebServer webServer;

    @BeforeEach
    public void setUp() throws Exception {
        int port = findAvailablePort();
        webServer = new WebServer(port, "/test");
    }

    @AfterEach
    public void tearDown() {
        webServer = null;
    }

    @Test
    public void testWebServerInitialization() {
        assertDoesNotThrow(() -> new WebServer(findAvailablePort(), "/test"));
    }

    @Test
    public void testWebServerStart() {
        assertDoesNotThrow(() -> webServer.start());
    }

    @Test
    public void testWebServerInitializationWithInvalidPort() {
        assertThrows(Exception.class, () -> new WebServer(-1, "/test"));
    }

    private int findAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("No available port found.", e);
        }
    }
}
