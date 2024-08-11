package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.hardware.Machine;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {
    private final HttpServer server;

    public WebServer(Integer port, String context) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 10);
        server.createContext(context, new Handler());
        server.setExecutor(Executors.newFixedThreadPool(Machine.getNumberProcessors()));
    }

    public void start() {
        server.start();
    }
}
