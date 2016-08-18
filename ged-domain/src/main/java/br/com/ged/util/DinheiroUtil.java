package br.com.ged.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Locale.Category;

import org.apache.commons.lang3.StringUtils;

public class DinheiroUtil {
	
	private DinheiroUtil(){
	}
	
	public static String doubleEmReal(double valor){
		
		NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault(Category.FORMAT));
		return n.format(valor);
	}
	
	public static Double realParaDouble(String precoFormat) {

		Double valor = null;
		
		if (StringUtils.isNotBlank(precoFormat)){
			
			String valorDouble = precoFormat.replace(".", "");
			valorDouble = valorDouble.replace(",", ".");
			
			valor = Double.valueOf(valorDouble);
		}
		
		return valor;
	}

	public static String doubleEmRealSemSimbolo(Double preco) {
		
		String dinheiro = null;
		
		if (preco != null){
			
			dinheiro = doubleEmReal(preco);
			dinheiro = dinheiro.replace("R$", "").trim();
		}
		
		return dinheiro;
	}
}
