package br.com.ged.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.ged.domain.Mensagem;

public final class InitMessageProperties {
	
	private static final String ARQUIVO_CONFIGURACAO_MENSAGENS = "/messages.properties";
	
	private static Properties properties;
	
	private InitMessageProperties(){
		//Evitando instanciação
	}

	static {
		InputStream inStream;
		properties = new Properties();
		inStream = InitMessageProperties.class.getResourceAsStream(ARQUIVO_CONFIGURACAO_MENSAGENS);
		try {
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}
	
	public static String getValue(Mensagem mensagemNegocio) {
		return properties.getProperty(mensagemNegocio.name());
	}
}