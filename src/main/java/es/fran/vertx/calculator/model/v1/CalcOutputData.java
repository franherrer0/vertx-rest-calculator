package es.fran.vertx.calculator.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fran
 * Output data for POST method
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcOutputData {

	private String opDesc;
	private double result;
}
