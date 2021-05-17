package com.oasisgames.vertex.http2.h2c;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public class Server extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        /* h2c:use in clear test over Tcp such connections are established either
         * with an HTTP/1.1 upgraded request or directly
         * */
        HttpServer server =
                vertx.createHttpServer(new HttpServerOptions());
        server.requestHandler(req -> {
            req.response().putHeader("content-type", "text/html").end("<html><body>" +
                    "<h1>Hello from vert.x!</h1>" +
                    "<p>version = " + req.version() + "</p>" +
                    "</body></html>");
        }).listen(8080);
    }

}
