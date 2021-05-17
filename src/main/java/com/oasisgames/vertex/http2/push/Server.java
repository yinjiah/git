package com.oasisgames.vertex.http2.push;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.PemKeyCertOptions;

//Server push is a new feature of HTTP/2 that enables
// sending multiple responses in parallel for a single
// client request.
public class Server extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        //config h2
        HttpServer server =
                vertx.createHttpServer(new HttpServerOptions().
                        setUseAlpn(true).
                        setSsl(true)
                        .setPemKeyCertOptions(new PemKeyCertOptions().setKeyPath("/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http2/customframes/server-key.pem").setCertPath("/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http2/customframes/server-cert.pem")
                        ));

        server.requestHandler(req -> {
            String path = req.path();
            HttpServerResponse resp = req.response();
            if ("/".equals(path)) {
                // Push main.js to the client
                resp.push(HttpMethod.GET, "/script.js", ar -> {
                    if (ar.succeeded()) {
                        System.out.println("sending push");
                        // The server is ready to push the response
                        //get response
                        HttpServerResponse pushedResp = ar.result();
                        pushedResp.sendFile("/Users/yinjiahao/Documents/user/code/vertx-examples/core-examples/src/main/java/io/vertx/example/core/http2/push/script.js");
                    } else {
                        // Sometimes Safari forbids push : "Server push not allowed to opposite endpoint."
                    }
                });
                // Send the requested resource
                resp.sendFile("/Users/yinjiahao/Documents/user/code/vertx-examples/core-examples/src/main/java/io/vertx/example/core/http2/push/index.html");
            }
        else if ("/script.js".equals(path)) {
                resp.sendFile("/Users/yinjiahao/Documents/user/code/vertx-examples/core-examples/src/main/java/io/vertx/example/core/http2/push/script.js");
            } else {
                System.out.println("Not found " + path);
                resp.setStatusCode(404).end();
            }
        });

        //listen the port
        server.listen(8443, "localhost", ar -> {
            if (ar.succeeded()) {
                System.out.println("Server started");
            } else {
                ar.cause().printStackTrace();
            }
        });

    }
}
