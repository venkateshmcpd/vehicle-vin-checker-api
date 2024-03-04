package vin.checker.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;
import static vin.checker.constants.VinCheckerConstants.*;

@Log4j2
public enum VinDataSource {

	INSTANCE;
	
	// database connections map.
	private Map<Integer, HikariDataSource> store = new HashMap<>();

	public void buildDataSource() {
		// if DataSources are already built, return.
		if (!store.isEmpty()) {
			return;
		}
		HikariDataSource dsVinChecker = buildHikariDataSource();
		store.put(1, dsVinChecker);

//		HikariDataSource dsXyz = buildHikariDataSource(sqlConfig);
//		store.put(2, dsXyz);
		log.info("database connection success");	
	}

	/**
	 * Get read connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getVinCheckerConnection() throws SQLException {
		HikariDataSource dsVinChecker = store.get(1);
		if (dsVinChecker == null) {
			dsVinChecker = buildHikariDataSource();
			store.put(1, dsVinChecker);
		}
		return dsVinChecker.getConnection();
	}

	/**
	 * Get Xyz connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getXyzConnection() throws SQLException {
		HikariDataSource ds = store.get(2);
		if (ds == null) {
			throw new NullPointerException();
		}

		return ds.getConnection();
	}

	/**
	 * Close the connection
	 * 
	 * @throws SQLException
	 */
	public void shutdown() {
		HikariDataSource ds = store.get(1);
		ds.close();
	}
	
	private HikariDataSource buildHikariDataSource() {
		JsonObject sqlConfig = VinCheckerConfig.getSqlConfig();
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(sqlConfig.getString(DRIVER_CLASS_NAME));
		hikariConfig.setJdbcUrl(sqlConfig.getString(JDBC_URL));
		hikariConfig.setUsername(sqlConfig.getString(USER_NAME));
		hikariConfig.setPassword(sqlConfig.getString(PASSWORD));
		hikariConfig.setMaximumPoolSize(sqlConfig.getInteger(MAX_CON_POOL_SIZE));
		hikariConfig.setConnectionTimeout(sqlConfig.getInteger(CONNECTION_TIME_OUT));
        hikariConfig.addDataSourceProperty(CACHE_PREP_STMTS_KEY, sqlConfig.getBoolean(CACHE_PREP_STMTS));
        hikariConfig.addDataSourceProperty(PREP_STMT_CACHE_SIZE_KEY, sqlConfig.getInteger(PREP_STMT_CACHE_SIZE));
        hikariConfig.addDataSourceProperty(PREP_STMT_CACHE_SQL_LIMIT_KEY, sqlConfig.getInteger(PREP_STMT_CACHE_SQL_LIMIT));
		return new HikariDataSource(hikariConfig);
	}
}
