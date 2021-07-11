package com.company;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoWSD;
import fi.iki.elonen.NanoWSD.WebSocketFrame.CloseCode;

public class WebSocketServer extends NanoWSD {

    /**
     * logger to log to.
     */
    private static final Logger LOG = Logger.getLogger(WebSocketServer.class.getName());

    private final boolean debug;
    private final Queue<String> bus;

    public WebSocketServer(int port, boolean debug, Queue<String> bus) {
        super(port);
        this.debug = debug;
        this.bus = bus;
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake) {
        return new DebugWebSocket(this, handshake, bus);
    }

    private static class DebugWebSocket extends WebSocket {

        private final WebSocketServer server;
        private final Queue<String> bus;

        public DebugWebSocket(WebSocketServer server, IHTTPSession handshakeRequest, Queue<String> bus) {
            super(handshakeRequest);
            this.server = server;
            this.bus = bus;
        }

        @Override
        protected void onOpen() {
            Runnable myRunnable = () -> {
                while (true) {
                    String message = bus.poll();
                    if (message == null) {
                        continue;
                    }

                    try {
                        send(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            Thread thread = new Thread(myRunnable);
            thread.start();
//            System.out.println("");
//            try {
//                send("sending");
//                send("something");
//                send("useful");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }

        @Override
        protected void onClose(CloseCode code, String reason, boolean initiatedByRemote) {
            if (server.debug) {
                System.out.println("C [" + (initiatedByRemote ? "Remote" : "Self") + "] " + (code != null ? code : "UnknownCloseCode[" + code + "]")
                        + (reason != null && !reason.isEmpty() ? ": " + reason : ""));
            }
        }

        @Override
        protected void onMessage(WebSocketFrame message) {
            try {
                message.setUnmasked();
                sendFrame(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPong(WebSocketFrame pong) {
            if (server.debug) {
                System.out.println("P " + pong);
            }
        }

        @Override
        protected void onException(IOException exception) {
            WebSocketServer.LOG.log(Level.SEVERE, "exception occured", exception);
        }

        @Override
        protected void debugFrameReceived(WebSocketFrame frame) {
            if (server.debug) {
                System.out.println("R " + frame);
            }
        }

        @Override
        protected void debugFrameSent(WebSocketFrame frame) {
            if (server.debug) {
                System.out.println("S " + frame);
            }
        }
    }
}
