package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.hardware.Machine;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * The WebServer class is responsible for creating and starting an HTTP server.
 * It initializes the server with a specified port and context, and sets up a request handler.
 * The server uses a fixed thread pool executor based on the number of processors available on the machine.
 *
 * @author  Hudson Schumaker
 * @version 1.0.0
 */
public class WebServer {
    private final HttpServer server;

    /**
     * Constructs a new WebServer instance with the specified port and context.
     * Initializes the HTTP server and sets up the request handler.
     *
     * @param port the port number on which the server will listen
     * @param context the context path for the server
     * @throws Exception if an error occurs during server initialization
     */
    public WebServer(Integer port, String context) throws Exception {
        // TODO: check the backlog parameter for the HttpServer.create method
        server = HttpServer.create(new InetSocketAddress(port), 10);
        server.createContext(context, new Listener());
        server.setExecutor(Executors.newFixedThreadPool(Machine.getNumberProcessors()));
    }

    /**
     * Starts the HTTP server.
     */
    public void start() {
        server.start();
    }
}
