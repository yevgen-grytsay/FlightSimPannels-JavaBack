package com.company;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
// NOTE: If you're using NanoHTTPD >= 3.0.0 the namespace is different,
//       instead of the above import use the following:
// import org.nanohttpd.NanoHTTPD;

public class WebServer extends NanoHTTPD {

    public WebServer() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    public static void main(String[] args) {
        try {
            new WebServer();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() != Method.GET) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT,
                    "The requested resource does not exist");
        }

        String uri = session.getUri();
        System.out.printf("Requested URI: %s\n", uri);
        String path;
        if (uri.equals("/")) {
            path = "/public/index.html";
        } else {
            path = "/public" + uri;
        }

        try {

            String resourcePath = path.substring(1);
            System.out.printf("Trying to read resource: %s\n", resourcePath);

            URL resource = getClass().getClassLoader().getResource(resourcePath);
            if (resource == null) {
                System.err.printf("Resource is null: %s\n", resourcePath);
            }

            String mimeType = URLConnection.guessContentTypeFromName(path);
            System.out.printf("Mime type of file '%s' is '%s'\n", path, mimeType);

            InputStream inputStream = getClass().getResourceAsStream(path);
            if (inputStream == null) {
                System.out.println("Input stream is null");
                throw new RuntimeException(String.format("Can not open resource: %s", path));
            }

            byte[] targetArray = getBytes(inputStream);

            return newFixedLengthResponse(Response.Status.OK, mimeType, new ByteInputStream(targetArray, targetArray.length), targetArray.length);

        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Unknown error";
            }
            System.err.println(message);

            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "");
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }
}
