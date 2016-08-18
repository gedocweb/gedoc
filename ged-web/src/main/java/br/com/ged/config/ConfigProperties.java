package br.com.ged.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigProperties {
	
	private static final String ARQUIVO_CONFIGURACAO_CONFIG = "/config.properties";
	
	private static Properties properties;
	
	private ConfigProperties(){
		//Evitando instanciação
	}

	static {
		InputStream inStream;
		properties = new Properties();
		inStream = ConfigProperties.class.getResourceAsStream(ARQUIVO_CONFIGURACAO_CONFIG);
		try {
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}
	
	public static String getValue(Config config) {
		return properties.getProperty(config.name());
	}
}