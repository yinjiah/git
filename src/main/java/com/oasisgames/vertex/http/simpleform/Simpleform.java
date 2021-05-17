package com.oasisgames.vertex.http.simpleform;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;

public class Simpleform extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        HttpServerOptions options = new HttpServerOptions().setHost("localhost");
        vertx.createHttpServer(options).requestHandler(req -> {
            if (req.uri().equals("/")) {
                // Serve the index page
                req.response().sendFile("/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/index.html");
            } else if (req.uri().startsWith("/form")) {
                req.response().putHeader("content-type","multipart/form-data");
                req.response().setChunked(true);
                req.setExpectMultipart(true);
                req.endHandler((v) -> {
                    //get attributes of table and write in form
                    for (String attr : req.formAttributes().names()) {
                        req.response().write("Got attr " + attr + " : " + req.formAttributes().get(attr) + "\n");
                    }
                    req.response().end();
                });
            } else {
                req.response().setStatusCode(404).end();
            }
        }).listen(8081);
    }
}

