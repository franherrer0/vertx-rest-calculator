package es.fran.vertx.calculator;

import es.fran.vertx.calculator.verticle.MainVerticle;
import io.vertx.reactivex.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();

        vertx.rxDeployVerticle(MainVerticle.class.getName())
                .subscribe(
                        verticle -> log.debug("New calculator verticle started!"),
                        throwable -> {
                            log.error("Error occurred before deploying calculator verticle: " + throwable.getMessage());
                            System.exit(1);
                        });
    }

}
