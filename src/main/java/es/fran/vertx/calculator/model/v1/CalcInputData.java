package es.fran.vertx.calculator.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fherrero
 * Input data (request object) for POST method
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcInputData {

    private double op1;
    private String operator;
    private double op2;
    
 	public CalcInputData(double op1, double op2) {
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public String toString() {
		return  op1 + " " + operator + " " + op2;
	}
}
