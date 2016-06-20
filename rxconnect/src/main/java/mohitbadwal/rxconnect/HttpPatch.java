package mohitbadwal.rxconnect;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * Created by JohnConnor on 20-Jun-16.
 */
public class HttpPatch extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "PATCH";

    public HttpPatch() {
    }

    public HttpPatch(URI uri) {
        this.setURI(uri);
    }

    public HttpPatch(String uri) {
        this.setURI(URI.create(uri));
    }

    public String getMethod() {
        return "PATCH";
    }
}

