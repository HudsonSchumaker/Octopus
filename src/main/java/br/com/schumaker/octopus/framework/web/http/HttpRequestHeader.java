package br.com.schumaker.octopus.framework.web.http;

import java.util.Map;

public record HttpRequestHeader(Map<String, String> headers) {}
