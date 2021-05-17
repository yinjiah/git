package com.oasisgames.vertex.http.sharing;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

public class Initialize extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.deployVerticle(
                "com.oasisgames.vertex.http.sharing.HttpSharingVerticle",
                new DeploymentOptions().setInstances(2));
    }
}
