package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.reflection.Pair;

public class Http {
    public static final String GET = HttpVerb.GET.name();
    public static final String POST = HttpVerb.POST.name();
    public static final String PUT = HttpVerb.PUT.name();
    public static final String PATCH = HttpVerb.PATCH.name();
    public static final String DELETE = HttpVerb.DELETE.name();
    public static final String HEADER = HttpVerb.HEADER.name();
    public static final String OPTIONS = HttpVerb.OPTIONS.name();

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM = "multipart/form-data";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_XML = "text/xml";
    public static final String TEXT_JSON = "text/json";
    public static final String TEXT_CSV = "text/csv";
    public static final String TEXT_YAML = "text/yaml";

    public static final Integer HTTP_200 = 200;
    public static final Integer HTTP_201 = 201;
    public static final Integer HTTP_202 = 202;
    public static final Integer HTTP_204 = 204;
    public static final Integer HTTP_400 = 400;
    public static final Integer HTTP_401 = 401;
    public static final Integer HTTP_403 = 403;
    public static final Integer HTTP_404 = 404;
    public static final Integer HTTP_405 = 405;
    public static final Integer HTTP_409 = 409;
    public static final Integer HTTP_500 = 500;
    public static final Integer HTTP_501 = 501;
    public static final Integer HTTP_502 = 502;
    public static final Integer HTTP_503 = 503;
    public static final Integer HTTP_504 = 504;

    public static final String HTTP_OK = "OK";
    public static final String HTTP_CREATED = "CREATED";
    public static final String HTTP_ACCEPTED = "ACCEPTED";
    public static final String HTTP_NO_CONTENT = "NO CONTENT";
    public static final String HTTP_BAD_REQUEST = "BAD REQUEST";
    public static final String HTTP_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String HTTP_FORBIDDEN = "FORBIDDEN";
    public static final String HTTP_NOT_FOUND = "NOT FOUND";
    public static final String HTTP_METHOD_NOT_ALLOWED = "METHOD NOT ALLOWED";
    public static final String HTTP_CONFLICT = "CONFLICT";
    public static final String HTTP_INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    public static final String HTTP_NOT_IMPLEMENTED = "NOT IMPLEMENTED";
    public static final String HTTP_BAD_GATEWAY = "BAD GATEWAY";
    public static final String HTTP_SERVICE_UNAVAILABLE = "SERVICE UNAVAILABLE";
    public static final String HTTP_GATEWAY_TIMEOUT = "GATEWAY TIMEOUT";

    public static final Pair<Integer, String> HTTP_200_OK = new Pair<>(HTTP_200, HTTP_OK);
    public static final Pair<Integer, String> HTTP_201_CREATED = new Pair<>(HTTP_201, HTTP_CREATED);
    public static final Pair<Integer, String> HTTP_202_ACCEPTED = new Pair<>(HTTP_202, HTTP_ACCEPTED);
    public static final Pair<Integer, String> HTTP_204_NO_CONTENT = new Pair<>(HTTP_204, HTTP_NO_CONTENT);
    public static final Pair<Integer, String> HTTP_400_BAD_REQUEST = new Pair<>(HTTP_400, HTTP_BAD_REQUEST);
    public static final Pair<Integer, String> HTTP_401_UNAUTHORIZED = new Pair<>(HTTP_401, HTTP_UNAUTHORIZED);
    public static final Pair<Integer, String> HTTP_403_FORBIDDEN = new Pair<>(HTTP_403, HTTP_FORBIDDEN);
    public static final Pair<Integer, String> HTTP_404_NOT_FOUND = new Pair<>(HTTP_404, HTTP_NOT_FOUND);
    public static final Pair<Integer, String> HTTP_405_METHOD_NOT_ALLOWED = new Pair<>(HTTP_405, HTTP_METHOD_NOT_ALLOWED);
    public static final Pair<Integer, String> HTTP_409_CONFLICT = new Pair<>(HTTP_409, HTTP_CONFLICT);
    public static final Pair<Integer, String> HTTP_500_INTERNAL_SERVER_ERROR = new Pair<>(HTTP_500, HTTP_INTERNAL_SERVER_ERROR);
    public static final Pair<Integer, String> HTTP_501_NOT_IMPLEMENTED = new Pair<>(HTTP_501, HTTP_NOT_IMPLEMENTED);
    public static final Pair<Integer, String> HTTP_502_BAD_GATEWAY = new Pair<>(HTTP_502, HTTP_BAD_GATEWAY);
    public static final Pair<Integer, String> HTTP_503_SERVICE_UNAVAILABLE = new Pair<>(HTTP_503, HTTP_SERVICE_UNAVAILABLE);
    public static final Pair<Integer, String> HTTP_504_GATEWAY_TIMEOUT = new Pair<>(HTTP_504, HTTP_GATEWAY_TIMEOUT);
}
