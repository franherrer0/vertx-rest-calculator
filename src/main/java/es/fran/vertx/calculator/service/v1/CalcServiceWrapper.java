package es.fran.vertx.calculator.service.v1;


import es.fran.vertx.calculator.model.v1.CalcInputData;
import es.fran.vertx.calculator.model.v1.CalcOutputData;

public class CalcServiceWrapper {

	private CalcService calcService = new CalcService(); 
	
	public CalcOutputData add(CalcInputData data){
		return new CalcOutputData(data.toString(), calcService.add(data));
	}
	
	public CalcOutputData subtract(CalcInputData data){
		return new CalcOutputData(data.toString(), calcService.subtract(data));
	}
	
    public CalcOutputData calc(CalcInputData data){
    	return new CalcOutputData(data.toString(), calcService.add(data));
    }
}
