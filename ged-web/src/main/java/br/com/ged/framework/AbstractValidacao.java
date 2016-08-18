package br.com.ged.framework;

import java.math.BigInteger;
import java.util.List;

/**
 * 
 * @author Pedro Augusto
 *
 */
public abstract class AbstractValidacao {
	
	private static final double DOUBLE_ZERO = 0.0;

	protected boolean inteiroNaoInformado(Integer inteiro) {
		return inteiro == null || inteiro.intValue() == BigInteger.ZERO.intValue();
	}
	
	protected boolean inteiroNaoInformado(Long inteiro) {
		return inteiro == null || inteiro.longValue() == BigInteger.ZERO.longValue();
	}
	
	protected boolean decimalNaoInformado(Double inteiro) {
		return inteiro == null || inteiro.doubleValue() == DOUBLE_ZERO;
	}
	
	protected boolean listVazia(List<?> list) {
		return list == null || list.isEmpty();
	}
}