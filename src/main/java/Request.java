import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private final String method;
    private final String path;
    private final Map<String, String> queryParams;

    public Request(String requestLine) throws UnsupportedEncodingException {
        String[] parts = requestLine.split(" ");
        this.method = parts[0];

        // Разделяем путь и Query String
        String[] urlParts = parts[1].split("\\?", 2);
        this.path = urlParts[0];  // Путь без параметров

        // Если есть параметры, парсим их
        if (urlParts.length > 1) {
            this.queryParams = parseQueryParams(urlParts[1]);
        } else {
            this.queryParams = new HashMap<>();
        }
    }

    // Метод для получения параметра по имени
    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    // Метод для получения всех параметров
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    // Метод для получения пути запроса без параметров
    public String getPath() {
        return path;
    }

    // Парсинг параметров через URLEncodedUtils
    private Map<String, String> parseQueryParams(String query) throws UnsupportedEncodingException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(query, StandardCharsets.UTF_8);
        Map<String, String> params = new HashMap<>();
        for (NameValuePair pair : pairs) {
            params.put(pair.getName(), pair.getValue());
        }
        return params;
    }
}
