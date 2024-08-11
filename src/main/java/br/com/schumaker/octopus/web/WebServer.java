package br.com.schumaker.octopus.web;

import br.com.schumaker.octopus.hardware.Machine;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {
    private final HttpServer server;

    public WebServer(Integer port, String context) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 10);
        server.createContext(context, new Handler());
        server.setExecutor(Executors.newFixedThreadPool(Machine.getNumberProcessors()));
    }

    public void start() {
        server.start();
    }
}
