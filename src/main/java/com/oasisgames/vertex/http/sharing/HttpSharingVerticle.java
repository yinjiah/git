package com.oasisgames.vertex.http.sharing;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
/**
 * An example illustrating the server sharing and round robin. The servers are identified using an id.
 * The HTTP Server Verticle is instantiated twice in the deployment options.
 */
public class HttpSharingVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/html")
                    .end("<html><body><h1>Hello from " +  this + "</h1></body></html>");
        }).listen(8080);
    }
}
