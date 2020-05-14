package es.fran.vertx.calculator;

import es.fran.vertx.calculator.verticle.MainVerticle;
import io.vertx.reactivex.core.Vertx;


public class Main {
    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();

        vertx.rxDeployVerticle(MainVerticle.class.getName())
                .subscribe(
                        verticle -> System.out.println("New calculator verticle started!"),
                        throwable -> {
                            System.out.println("Error occurred before deploying calculator verticle: " + throwable.getMessage());
                            System.exit(1);
                        });
    }

}
