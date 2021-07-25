package com.company;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

import fi.iki.elonen.NanoHTTPD;
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
        String path;
        if (uri.equals("/")) {
            path = "/public/index.html";
        } else {
            path = "/public" + uri;
        }

        try {

            URL resource = getClass().getClassLoader().getResource(path.substring(1));
            File file = new File(resource.getFile());
            String mimeType = Files.probeContentType(file.toPath());

            InputStream inputStream = getClass().getResourceAsStream(path);
            return newFixedLengthResponse(Response.Status.OK, mimeType, inputStream, file.length());

        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Unknown error";
            }

            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, message);
        }
    }
}
