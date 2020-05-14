package es.fran.vertx.calculator.router.v1;


import es.fran.vertx.calculator.handler.v1.CalcHandler;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

public class CalcRouter {

    private Vertx vertx;
    private CalcHandler calcHandler;

    public CalcRouter(Vertx vertx, CalcHandler calcHandler) {
        this.vertx = vertx;
        this.calcHandler = calcHandler;
    }

    public Router getRouter() {
        final Router bookRouter = Router.router(vertx);

        bookRouter.route("/v1/calc*").handler(BodyHandler.create());
        bookRouter.get("/v1/calc/add/:op1/:op2").handler(calcHandler::add);
        bookRouter.get("/v1/calc/subtract/:op1/:op2").handler(calcHandler::subtract);
        bookRouter.post("/v1/calc").handler(calcHandler::calc);

        return bookRouter;
    }
}