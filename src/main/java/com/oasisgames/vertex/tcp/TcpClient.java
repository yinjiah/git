package com.oasisgames.vertex.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;


public class TcpClient extends AbstractVerticle {

    @Override
    public void start() throws Exception {
    // create a tcp client and set reconnectionInterval and times;
        NetClientOptions options = new NetClientOptions()
                .setConnectTimeout(10000)
                .setReconnectInterval(100)
                .setReconnectAttempts(10);
        NetClient client = vertx.createNetClient(options);

    // connect with server
        client.connect(5555, "localhost", conn -> {
            if (conn.succeeded()) {
                //get socket
                NetSocket s = conn.result();
                //write date to server
                s.write(Buffer.buffer("hello"));
                // read date from server
                s.handler(buffer -> {
                    System.out.println(buffer.length());
                });
            } else {
                System.out.println("occur some errors");
            }
        });
    }
}
