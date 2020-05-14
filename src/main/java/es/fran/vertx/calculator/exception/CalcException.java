package es.fran.vertx.calculator.exception;


/**
 * @author fran
 * CalcException for Calc Application
 */
public class CalcException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CalcException(String message) {
		super("Calc operation exception:: " + message);
	}
}
