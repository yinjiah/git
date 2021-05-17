package com.oasisgames.vertex.http2.customframes;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.PemKeyCertOptions;


public class Server extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        /*
         * vert.x support Http/2 over Tls h2 and over Tcp h2c;
         * h2 identifies dentifies the HTTP/2 protocol when used over TLS negotiated
         * by Application-Layer Protocol Negotiation (ALPN)
         *
         * h2c:use in clear test over Tcp such connections are established either
         * with an HTTP/1.1 upgraded request or directly
         * */
        //config h2
        HttpServer httpServer = vertx.createHttpServer(new HttpServerOptions()
                .setUseAlpn(true)//enable tls
                .setSsl(true)
                .setPemKeyCertOptions(new PemKeyCertOptions().setKeyPath("/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http2/customframes/server-key.pem").setCertPath("/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http2/customframes/server-cert.pem")));

        httpServer.requestHandler(req -> {
            HttpServerResponse resp = req.response();

            req.customFrameHandler(httpFrame -> {
                //acquire frame
                System.out.println("res");
                System.out.println("Received client frame " + httpFrame.payload().toString("utf-8"));

                //write frame
                resp.writeCustomFrame(10,0, Buffer.buffer("pong"));
            });
        }).listen(8443);
    }

}
