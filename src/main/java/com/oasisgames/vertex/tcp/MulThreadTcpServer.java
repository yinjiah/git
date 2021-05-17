package com.oasisgames.vertex.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.net.NetServer;

public class MulThreadTcpServer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        /**
         * have more than one server listening on same host and port
         * will cause port conflicts?
         *
         * answers: No
         *  vert.x's solve method:
         *      When you deploy another server on the same host and port as an existing server it doesn’t
         *      actually try and create a new server listening on the same host/port.
         *      Instead it internally maintains just a single server, and, as incoming connections arrive
         *      it distributes them in a round-robin fashion to any of the connect handlers.
         *      Consequently Vert.x TCP servers can scale over available cores while each instance remains single threaded.
         */

        // there are two ways to implement this feature
        // this way can utilised our computer's multi core
        for(int i = 0;i<10;i++){
            NetServer server = vertx.createNetServer();
            server.connectHandler(socket -> {
                socket.handler(socket::write);
            });
            server.listen(8080,"localHost");
        }
        //this is the second way
        DeploymentOptions options = new DeploymentOptions().setInstances(10);
        vertx.deployVerticle("com.oasisgames.vertex.tcp.TcpServer",options);
    }
}
