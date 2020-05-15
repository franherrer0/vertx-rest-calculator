package es.fran.vertx.calculator.handler.v1;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import es.fran.vertx.calculator.model.v1.CalcInputData;
import es.fran.vertx.calculator.model.v1.CalcOutputData;
import es.fran.vertx.calculator.service.v1.CalcServiceWrapper;

@Slf4j
public class CalcHandler {

    private CalcServiceWrapper calcServiceWrapper;

    public CalcHandler(CalcServiceWrapper calcServiceWrapper) {
        this.calcServiceWrapper = calcServiceWrapper;
    }

    public void add(RoutingContext rc) {
    	final String op1 = rc.pathParam("op1");
    	final String op2 = rc.pathParam("op2");
    	try {
    		double dop1 = Double.valueOf(op1);
    		double dop2 = Double.valueOf(op2);
    		CalcOutputData output = calcServiceWrapper.add(new CalcInputData(dop1,"+",dop2));
    		onSuccessResponse(rc, 200, output);
    		log.debug(output.toString());
    	}catch(Exception e) {
    		onErrorResponse(rc, 400, e);
    	}
    }

    public void subtract(RoutingContext rc) {
    	final String op1 = rc.pathParam("op1");
    	final String op2 = rc.pathParam("op2");
    	try {
    		double dop1 = Double.valueOf(op1);
    		double dop2 = Double.valueOf(op2);

    		CalcOutputData output = calcServiceWrapper.subtract(new CalcInputData(dop1,"-",dop2));
    		onSuccessResponse(rc, 200, output);
    		log.debug(output.toString());
    	}catch(Exception e) {
    		onErrorResponse(rc, 400, e);
    	}
    }
    
    public void calc(RoutingContext rc) {
    	JsonObject json = rc.getBodyAsJson();
    	log.debug(json.toString());
    	try {
    		double dop1 = json.getDouble("op1");
    		double dop2 = json.getDouble("op2");
    		String operator = json.getString("operator");

    		CalcOutputData output = calcServiceWrapper.calc(new CalcInputData(dop1,operator,dop2));
    		onSuccessResponse(rc, 200, output);
    		log.debug(output.toString());
    	}catch(Exception e) {
    		onErrorResponse(rc, 400, e);
    	}
    }

    // Generic responses
    private void onSuccessResponse(RoutingContext rc, int status, Object object) {
        rc.response()
                .setStatusCode(status)
                .putHeader("Content-Type", "application/json")
                .end(Json.encodePrettily(object));
    }

    private void onErrorResponse(RoutingContext rc, int status, Throwable throwable) {
        final JsonObject error = new JsonObject().put("error", throwable.getMessage());

        rc.response()
                .setStatusCode(status)
                .putHeader("Content-Type", "application/json")
                .end(Json.encodePrettily(error));
    }

}
