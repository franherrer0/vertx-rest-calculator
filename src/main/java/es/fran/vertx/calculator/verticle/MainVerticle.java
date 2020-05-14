package es.fran.vertx.calculator.verticle;


import es.fran.vertx.calculator.handler.v1.CalcHandler;
import es.fran.vertx.calculator.router.v1.CalcRouter;
import es.fran.vertx.calculator.service.v1.CalcServiceWrapper;
import io.reactivex.Single;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;


public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        final ConfigStoreOptions store = new ConfigStoreOptions().setType("env");
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(store);
    	
        ConfigStoreOptions envJson = new ConfigStoreOptions()
    			.setType("file")
    			.setFormat("json")
    			.setConfig(new JsonObject().put("path", "conf/config.json"));
    	options = options.addStore(envJson);
        
        final ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        retriever.rxGetConfig()
                .flatMap(configurations -> {

                    final CalcServiceWrapper service = new CalcServiceWrapper();
                	final CalcHandler calcHandler = new CalcHandler(service);		
                    final CalcRouter calcRouter = new CalcRouter(vertx, calcHandler);

                    return createHttpServer(calcRouter.getRouter(), configurations);
                })
                .subscribe(
                        server -> System.out.println("HTTP Server listening on port " + server.actualPort()),
                        throwable -> {
                            System.out.println("Error occurred before creating a new HTTP server: " + throwable.getMessage());
                            System.exit(1);
                        });
    }

    private Single<HttpServer> createHttpServer(Router router, JsonObject configurations) {
        return vertx
                .createHttpServer()
                .requestHandler(router)
                .rxListen(config().getInteger("HTTP_PORT_TEST")!=null?
                		config().getInteger("HTTP_PORT_TEST"):
                		configurations.getInteger("HTTP_PORT"));
       
    }

}
