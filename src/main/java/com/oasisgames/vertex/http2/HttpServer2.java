package com.oasisgames.vertex.http2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;

public class HttpServer2 extends AbstractVerticle {
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
        // HttpServerOptions options = new HttpServerOptions().setUseAlpn(true).setSsl(true).setKeyStoreOptions(new JksOptions().setPath("/path/to/my/keystore"));




    }
}
