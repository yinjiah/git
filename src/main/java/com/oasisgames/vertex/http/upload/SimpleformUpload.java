package com.oasisgames.vertex.http.upload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class SimpleformUpload extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            if (req.uri().equals("/")) {
                // Serve the index page
                req.response().sendFile("/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/index.html");
            } else if (req.uri().startsWith("/form")) {
                req.setExpectMultipart(true);
                req.uploadHandler(upload -> {
                    //save uploaded file to disk
                    upload.streamToFileSystem(upload.filename());
                    // 4.0 api .onSuccess(v-> req.response().end("Successful" + upload.filename()));
                    //4.0 api .onFailure(err-> req.response().end("Upload failure"));
                });
            } else {
                req.response()
                        .setStatusCode(404)
                        .end();
            }
        }).listen(8081);

    }
}
