package vin.checker.server;

import static vin.checker.constants.VinCheckerConstants.CLASS;
import static vin.checker.constants.VinCheckerConstants.DEPLOYING_WORKER;
import static vin.checker.constants.VinCheckerConstants.INSTANCES;
import static vin.checker.constants.VinCheckerConstants.SERVER_CONFIG;
import static vin.checker.constants.VinCheckerConstants.WORKERS;
import static vin.checker.constants.VinCheckerConstants.WORKER_POOL_SIZE;

import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Deployer extends AbstractVerticle {

	@Override
	public void start() {
		JsonObject config = config();
		// vertx options
		VertxOptions options = new VertxOptions();
		Long maxWorkerExecuteTime = (long) 6e+11;
		options.setBlockedThreadCheckInterval(maxWorkerExecuteTime);
		options.setMaxWorkerExecuteTime(maxWorkerExecuteTime);
		options.setMaxEventLoopExecuteTime(maxWorkerExecuteTime);
		options.setWorkerPoolSize(config.getInteger(WORKER_POOL_SIZE));
		Vertx vertx = Vertx.vertx(options);

		JsonObject serverConfig = config.getJsonObject(SERVER_CONFIG);
		JsonObject workerDefinitions = config.getJsonObject(WORKERS);

		// VinCheckerConfig build
		log.info("set config values - start");
		VinCheckerConfig.setConfigValues(config);
		log.info("set config values - end");
		
		// establish sql server connection
		log.info("Establising database connection");
		VinDataSource.INSTANCE.buildDataSource();
		log.info("database connection established");
		
		// Deploy Server
		DeploymentOptions serverDeploymentOptions = new DeploymentOptions()
				.setInstances(serverConfig.getInteger(INSTANCES));
		log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Deploying Server Verticle &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& ");
		vertx.deployVerticle(serverConfig.getString(CLASS), serverDeploymentOptions);

		//Deploy Workers
		Set<String> workers = workerDefinitions.fieldNames();
		for (String worker : workers) {
			DeploymentOptions deploymentOptions = new DeploymentOptions()
					.setWorker(true)
					.setInstances(workerDefinitions.getJsonObject(worker).getInteger(INSTANCES));					
			log.info(String.format(DEPLOYING_WORKER, worker));
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Deploying VIN Search Verticle &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& ");
			vertx.deployVerticle(worker, deploymentOptions);
		}
	}

	@Override
	public void stop() {
		log.info("Stopping Deployer Verticle");
		VinDataSource.INSTANCE.shutdown();
	}
}