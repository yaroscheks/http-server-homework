import org.apache.hc.core5.net.URIBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Request {
    private final String path;
    private final Map<String, String> queryParams = new HashMap<>();

    public Request(String requestLine) throws IOException {
        try {
            URI uri = new URIBuilder(requestLine.split(" ")[1]).build();
            this.path = uri.getPath();
            List<NameValuePair> params = URLEncodedUtils.parse(uri, "UTF-8");
            for (var param : params) {
                queryParams.put(param.getName(), param.getValue());
            }
        } catch (Exception e) {
            throw new IOException("Failed to parse request", e);
        }
    }

    public String getPath() {
        return path;
    }

    public Optional<String> getQueryParam(String name) {
        return Optional.ofNullable(queryParams.get(name));
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
