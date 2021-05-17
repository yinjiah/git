package com.oasisgames.vertex.http.simple;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.RequestOptions;

import java.nio.charset.StandardCharsets;

public class HttpServer extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    //vert.x support HTTP/1.X HTTP/2
    //create httpserver and configuring an httpserver
    HttpServerOptions options = new HttpServerOptions().setMaxWebSocketFrameSize(10000);
    io.vertx.core.http.HttpServer server = vertx.createHttpServer(options);
    // when request income the handle of request （request header rather than request body）
    server.requestHandler(req ->{
      System.out.println("there is request income");
      HttpServerResponse response = req.response();
      //if don't use chunk must set content-length.
      response.putHeader("content-length","10000")
              .putHeader("content-type","text/html");
      response.write("hello word");
      response.end();
      // there are many methods that we can get request's information
      System.out.println(req.version());//http's version
      System.out.println(req.method());//http's method
      System.out.println(req.uri());//http's relative uri
      System.out.println(req.absoluteURI());//http's absolute uri
      System.out.println(req.path());// path
      System.out.println(req.query());//return param in url
      MultiMap header = req.headers();//http request header
      System.out.println(header.get("user-Agent"));//get useragent in header
      System.out.println(req.host());//return host
      MultiMap params = req.params();//return param such as /page.html?param1=abc&param2=xyz return abc xyz
      System.out.println(params.get("abc"));
      System.out.println(req.remoteAddress());//get remote ip

      //this will get called every time a chunk of the request body arrives.
      req.handler(buffer -> {
        System.out.println("I have received a chunk of the body of length" + buffer.length());
      });

      // this handle is called once when all body has been received;
      req.bodyHandler(totalBuffer ->{
        System.out.println("full body received,length = " + totalBuffer.length());
    });

      //first handle http handle then body,final endHandler

    });





    //listen the port
    server.listen(5555,"localhost",res->{
      if(res.succeeded()){
        System.out.println("listening!");
      }else{
        System.out.println("Failed to bind");
      }
    });
  }
}
