package com.oasisgames.vertex.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class TcpServer extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //1.create a Tcp server
    // NetServer netServer = vertx.createNetServer();
    //2.configuring a Tcp server

    NetServer server = vertx.createNetServer();
    //start the server listening and this is asy so
    //you can enter 0 in listen port and will be choose a random port ,you can call server.actualPort()
    // to acquire port num; for instance :server.listen(0, "localhost", res -> {
    //  if (res.succeeded()) {
    //    System.out.println("Server is now listening on actual port: " + server.actualPort());
    //  }

    // handle the tcp connection
    server.connectHandler(socket ->{
      //read date from socket
      socket.handler(buffer -> {
        System.out.println("I received some bytes:" + buffer.toString());
      });
      //get remote ip
      System.out.println(socket.remoteAddress().toString());
      //localAddress
      System.out.println(socket.localAddress().toString());
      //write date to socket
      Buffer buffer = Buffer.buffer().appendFloat(12.34f).appendInt(123).appendString("some date");
      // you can write any type date what you like
      socket.write(buffer);

      //close handle -> you can notice when a socket is closed.
      socket.closeHandler(res -> {
        System.out.println("the socket has been closed");
      });
      //deal with exception
      socket.exceptionHandler(res ->{
        System.out.println(res.getCause().toString());});

      //asy manual call close the server;
      socket.close(res -> {
        if(res.succeeded()){
          System.out.println("server has closed");
        }else{
          System.out.println("close failed");
        }
      });
    });

    /**
     * notice:
     *    you must must deal with the call back handle before listen the port if not
     *    server will occur errors;
     */
    server.listen(5555,"localhost",res ->{
      if(res.succeeded()){
        System.out.println("Listening");
      }else{
        System.out.println(res.cause().toString());
      }
    });

  }
}
