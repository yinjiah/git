package com.oasisgames.vertex.http.sendfile;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class SendFile extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            String filename = null;
            if (req.path().equals("/")) {
                filename = "/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http/sendfile/index.html";
            } else if (req.path().equals("/page1.html")) {
                filename = "/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http/sendfile/page1.html";
            } else if (req.path().equals("/page2.html")) {
                filename = "/Users/yinjiahao/Documents/user/git/src/main/java/com/oasisgames/vertex/http/sendfile/page2.html";
            } else {
                req.response().setStatusCode(404).end();
            }
            if (filename != null) {
                //show view page
                req.response().sendFile(filename);
            }
        }).listen(8081);
    }
}
