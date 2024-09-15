package br.com.schumaker.octopus.framework.web.http;

/**
 * The Http class provides constants for HTTP methods, content types, and status codes.
 * It also provides pairs of HTTP status codes and their corresponding messages.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class Http {
    // HTTP Methods
    public static final String HTTP_GET = HttpVerb.GET.name();
    public static final String HTTP_POST = HttpVerb.POST.name();
    public static final String HTTP_PUT = HttpVerb.PUT.name();
    public static final String HTTP_PATCH = HttpVerb.PATCH.name();
    public static final String HTTP_DELETE = HttpVerb.DELETE.name();
    public static final String HTTP_HEADER = HttpVerb.HEADER.name();
    public static final String HTTP_OPTIONS = HttpVerb.OPTIONS.name();

    // Content Types
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

    // HTTP Status Codes
    // 1xx Informational
    public static final int HTTP_100 = 100;
    public static final int HTTP_101 = 101;
    public static final int HTTP_102 = 102;

    // 2xx Success
    public static final int HTTP_200 = 200;
    public static final int HTTP_201 = 201;
    public static final int HTTP_202 = 202;
    public static final int HTTP_203 = 203;
    public static final int HTTP_204 = 204;

    // 3xx Redirection
    public static final int HTTP_300 = 300;
    public static final int HTTP_301 = 301;
    public static final int HTTP_302 = 302;
    public static final int HTTP_303 = 303;
    public static final int HTTP_304 = 304;
    public static final int HTTP_305 = 305;
    public static final int HTTP_306 = 306;
    public static final int HTTP_307 = 307;
    public static final int HTTP_308 = 308;

    // 4xx Client Error
    public static final int HTTP_400 = 400;
    public static final int HTTP_401 = 401;
    public static final int HTTP_403 = 403;
    public static final int HTTP_404 = 404;
    public static final int HTTP_405 = 405;
    public static final int HTTP_406 = 406;
    public static final int HTTP_407 = 407;
    public static final int HTTP_408 = 408;
    public static final int HTTP_409 = 409;
    public static final int HTTP_410 = 410;
    public static final int HTTP_411 = 411;
    public static final int HTTP_412 = 412;
    public static final int HTTP_413 = 413;
    public static final int HTTP_414 = 414;
    public static final int HTTP_415 = 415;
    public static final int HTTP_416 = 416;
    public static final int HTTP_417 = 417;
    public static final int HTTP_418 = 418;
    public static final int HTTP_422 = 422;

    // 5xx Server Error
    public static final int HTTP_500 = 500;
    public static final int HTTP_501 = 501;
    public static final int HTTP_502 = 502;
    public static final int HTTP_503 = 503;
    public static final int HTTP_504 = 504;
    public static final int HTTP_505 = 505;

    // HTTP Status Messages
    // 1xx Informational
    public static final String HTTP_100_CONTINUE = "100 Continue";
    public static final String HTTP_101_SWITCHING_PROTOCOLS = "101 Switching Protocols";
    public static final String HTTP_102_PROCESSING = "102 Processing";

    // 2xx Success
    public static final String HTTP_200_OK = "200 OK";
    public static final String HTTP_201_CREATED = "201 Created";
    public static final String HTTP_202_ACCEPTED = "202 Accepted";
    public static final String HTTP_203_NON_AUTHORITATIVE_INFORMATION = "203 Non-Authoritative Information";
    public static final String HTTP_204_NO_CONTENT = "204 No Content";
    public static final String HTTP_205_RESET_CONTENT = "205 Reset Content";
    public static final String HTTP_206_PARTIAL_CONTENT = "206 Partial Content";
    public static final String HTTP_207_MULTI_STATUS = "207 Multi-Status";
    public static final String HTTP_208_ALREADY_REPORTED = "208 Already Reported";
    public static final String HTTP_226_IM_USED = "226 IM Used";

    // 3xx Redirection
    public static final String HTTP_300_MULTIPLE_CHOICES = "300 Multiple Choices";
    public static final String HTTP_301_MOVED_PERMANENTLY = "301 Moved Permanently";
    public static final String HTTP_302_FOUND = "302 Found.";
    public static final String HTTP_303_SEE_OTHER = "303 See Other";
    public static final String HTTP_304_NOT_MODIFIED = "304 Not Modified";
    public static final String HTTP_305_USE_PROXY = "305 Use Proxy";
    public static final String HTTP_306_SWITCH_PROXY = "306 Switch Proxy'";
    public static final String HTTP_307_TEMPORARY_REDIRECT = "307 Temporary Redirect";
    public static final String HTTP_308_PERMANENT_REDIRECT = "308 Permanent Redirect";

    // 4xx Client Errors
    public static final String HTTP_400_BAD_REQUEST = "400 Bad Request";
    public static final String HTTP_401_UNAUTHORIZED = "401 Unauthorized";
    public static final String HTTP_402_PAYMENT_REQUIRED = "402 Payment Required";
    public static final String HTTP_403_FORBIDDEN = "403 Forbidden";
    public static final String HTTP_404_NOT_FOUND = "404 Not Found";
    public static final String HTTP_405_METHOD_NOT_ALLOWED = "405 Method Not Allowed";
    public static final String HTTP_406_NOT_ACCEPTABLE = "406 Not Acceptable";
    public static final String HTTP_407_PROXY_AUTHENTICATION_REQUIRED = "407 Proxy Authentication Required";
    public static final String HTTP_408_REQUEST_TIMEOUT = "408 Request Timeout";
    public static final String HTTP_409_CONFLICT = "409 Conflict";
    public static final String HTTP_410_GONE = "410 Gone";
    public static final String HTTP_411_LENGTH_REQUIRED = "411 Length Required";
    public static final String HTTP_412_PRECONDITION_FAILED = "412 Precondition Failed";
    public static final String HTTP_413_PAYLOAD_TOO_LARGE = "413 Payload Too Large";
    public static final String HTTP_414_URI_TOO_LONG = "414 URI Too Long";
    public static final String HTTP_415_UNSUPPORTED_MEDIA_TYPE = "415 Unsupported Media Type";
    public static final String HTTP_416_RANGE_NOT_SATISFIABLE = "416 Range Not Satisfiable";
    public static final String HTTP_417_EXPECTATION_FAILED = "417 Expectation Failed";
    public static final String HTTP_418_IM_A_TEAPOT = "418 I'm a teapot";
    public static final String HTTP_421_MISDIRECTED_REQUEST = "421 Misdirected Request";
    public static final String HTTP_422_UNPROCESSABLE_ENTITY = "422 Unprocessable Entity";
    public static final String HTTP_423_LOCKED = "423 Locked";
    public static final String HTTP_424_FAILED_DEPENDENCY = "424 Failed Dependency";
    public static final String HTTP_425_TOO_EARLY = "425 Too Early";
    public static final String HTTP_426_UPGRADE_REQUIRED = "426 Upgrade Required";
    public static final String HTTP_428_PRECONDITION_REQUIRED = "428 Precondition Required";
    public static final String HTTP_429_TOO_MANY_REQUESTS = "429 Too Many Requests";
    public static final String HTTP_431_REQUEST_HEADER_FIELDS_TOO_LARGE = "431 Request Header Fields Too Large";
    public static final String HTTP_451_UNAVAILABLE_FOR_LEGAL_REASONS = "451 Unavailable For Legal Reasons";

    // 5xx Server Errors
    public static final String HTTP_500_INTERNAL_SERVER_ERROR = "500 Internal Server Error";
    public static final String HTTP_501_NOT_IMPLEMENTED = "501 Not Implemented";
    public static final String HTTP_502_BAD_GATEWAY = "502 Bad Gateway";
    public static final String HTTP_503_SERVICE_UNAVAILABLE = "503 Service Unavailable";
    public static final String HTTP_504_GATEWAY_TIMEOUT = "504 Gateway Timeout";
    public static final String HTTP_505_HTTP_VERSION_NOT_SUPPORTED = "505 HTTP Version Not Supported";
    public static final String HTTP_506_VARIANT_ALSO_NEGOTIATES = "506 Variant Also Negotiates";
    public static final String HTTP_507_INSUFFICIENT_STORAGE = "507 Insufficient Storage";
    public static final String HTTP_508_LOOP_DETECTED = "508 Loop Detected";
    public static final String HTTP_510_NOT_EXTENDED = "510 Not Extended";
    public static final String HTTP_511_NETWORK_AUTHENTICATION_REQUIRED = "511 Network Authentication Required";
}
