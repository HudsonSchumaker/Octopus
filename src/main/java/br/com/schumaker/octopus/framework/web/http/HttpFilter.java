package br.com.schumaker.octopus.framework.web.http;

public interface HttpFilter {
    void doFilter(HttpRequest request);
}
