package br.com.ged.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.ged.domain.EmailRemetenteEnum;

public final class EmailProperties {
	
	private static final String ARQUIVO_CONFIGURACAO = "/email.properties";
	
	private static Properties properties;
	
	private EmailProperties(){
		//Evitando instanciação
	}

	static {
		InputStream inStream;
		properties = new Properties();
		inStream = EmailProperties.class.getResourceAsStream(ARQUIVO_CONFIGURACAO);
		try {
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}
	
	public static String getValue(EmailRemetenteEnum remetente) {
		return properties.getProperty(remetente.getChave());
	}
}