package es.fran.vertx.calculator.service.v1;

import es.fran.vertx.calculator.exception.CalcException;
import es.fran.vertx.calculator.model.v1.CalcInputData;

/**
 * @author fran
 * Service with calc logic + "generic method"
 */


public class CalcService {
	
    public double add(CalcInputData data){
    	if(null==data.getOperator() || data.getOperator().isEmpty()) data.setOperator("+");
        return calculate(data);
    }

    public double subtract(CalcInputData data){
    	if(null==data.getOperator() || data.getOperator().isEmpty()) data.setOperator("-");
        return calculate(data);
    }

    /**
     * Generic method to allow multiple operations
     * @param data
     * @return
     * @throws CalcException
     */
    public double calculate(CalcInputData data) throws CalcException{
    	
        double result = 0;

        switch(data.getOperator()) {
            case "+":
                result = data.getOp1() + data.getOp2();
                break;
            case "-":
                result = data.getOp1() - data.getOp2();
                break;
            case "*":
                result = data.getOp1() * data.getOp2();
                break;
            case "/":
                result = data.getOp1() / data.getOp2();
                break;
            case "^":
                result = Math.pow(data.getOp1(),data.getOp2());
                break;
            default:
            	throw new CalcException("Invalid operator >> " + data.getOperator() + " << Valid ones: + - * / ^");
        }

		return result;
        
    }
}