package vin.checker.config.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import io.vertx.core.json.JsonObject;

public enum ConfigReader {

	INSTANCE;
	private JsonObject config;
	public JsonObject getSqlConfig() throws FileNotFoundException, IOException {
		if(config != null) {
			return config; 
		} else {
			config = new JsonObject(IOUtils.toString(new FileReader(new File("src/test/resources/sql_config.json"))));			
		}
		return config;
	}

	public JsonObject getH2Config() throws FileNotFoundException, IOException {
		if(config != null) {
			return config; 
		} else {
			config = new JsonObject(IOUtils.toString(new FileReader(new File("src/test/resources/h2_config.json"))));			
		}
		return config;
	}
}
