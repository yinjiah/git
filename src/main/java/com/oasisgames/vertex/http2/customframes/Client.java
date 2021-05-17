package com.oasisgames.vertex.http2.customframes;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;

public class Client extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    HttpClientOptions options = new HttpClientOptions().
      setSsl(true).
      setUseAlpn(true).
      setProtocolVersion(HttpVersion.HTTP_2).
      setTrustAll(true);

    HttpClient client = vertx.createHttpClient(options);

    client.request(HttpMethod.GET, 8443, "localhost", "/")
      .onSuccess(request -> {
        request.response().onSuccess(resp -> {

          // Print custom frames received from server
          resp.customFrameHandler(frame -> {
            System.out.println("Got frame from server " + frame.payload().toString("UTF-8"));
          });
        });
        request.sendHead().onSuccess(v -> {

          // Once head has been sent we can send custom frames
          vertx.setPeriodic(1000, timerID -> {

            System.out.println("Sending ping frame to server");
            //？type and flags
            request.writeCustomFrame(10, 0, Buffer.buffer("ping"));
          });
        });
      });
  }
}
