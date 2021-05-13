package com.oasisgames.vertex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.pointer.JsonPointer;
import io.vertx.ext.web.Router;

import java.nio.charset.StandardCharsets;

public class MainVerticle extends AbstractVerticle {
  //第一步声明router
  Router router;
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //初始化router
    router = Router.router(vertx);
    //为了将body统一加载进内存
    Buffer totalBuffer = Buffer.buffer();

    //第四部 配置解析url
    router.route("/").handler(req -> {
      //设置响应头
      //req代表的是请求客户端
      /**
       * 可以拿到version url params path headers
        req.request().version();
        req.request().uri();//相对uri absoluteURI()
        req.request().params();
        req.request().path();
        req.request().headers();-->返回一个multiMap 一个key可以对应多个value
        req.request().method();
       */
      //处理request的消息体
      req.request().handler(buffer -> {
        System.out.println("I have received a chunk of the body of length"+buffer.length());
        totalBuffer.appendBuffer(buffer);
      });
      JsonPointer pointer1 = JsonPointer.from("/hello/world");
      System.out.println(pointer1.toString());
      HttpServerResponse response = req.response()
        .putHeader("content-type", "text/plain").putHeader("content-length",String.valueOf("soutchenggong".getBytes(StandardCharsets.UTF_8).length));//表单为（content-type/multipart/form-dat)
        //设置输出内容
      response.write("Hello from Vert.x!",res->{
        if(res.succeeded()){
          System.out.println("soutchenggong");
        }else {
          System.out.println(res.cause());
        }
      });
      response.end();
      String jsonString = "[\"foo\",\"bar\"]";
      JsonArray objects = new JsonArray(jsonString);
      System.out.println(objects.toString());


      req.request().endHandler(v -> {
        System.out.println("Full body received, length = " + totalBuffer.length());
      });

      req.request().bodyHandler(sumBuffer -> {
        System.out.println("full body received,length = "+ sumBuffer.length());
      });
    });

    //将router与vertx.httpserver绑定
    //对server端进行配置
    HttpServerOptions options = new HttpServerOptions().setMaxWebSocketFrameSize(10000).setIdleTimeout(100000);
    //开始监听8888端口
    vertx.createHttpServer(options).requestHandler(router).listen(8888, http -> {
      //使用了eventLoop进行异步调用，当有连接请求进来，
      //处理顺序->请求头->handle->body->endHandle 可以从httpServerRequest得到（url, header,param,path）
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
