package com.oasisgames.vertex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class TcpServer extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //1.create a Tcp server
    // NetServer netServer = vertx.createNetServer();
    //2.configuring a Tcp server
    NetServerOptions options = new NetServerOptions().setPort(9999).setHost("localhost");
    NetServer server = vertx.createNetServer(options);
    //start the server listening and this is asy so
    server.listen(res ->{
      if(res.succeeded()){
        System.out.println("Listening");
      }else{
        System.out.println(res.cause().toString());
      }
    });

  }
}
