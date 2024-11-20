package org.actividadut02.dataaccess;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Manejo de conexiones.
 *  Clase ClassicModelsCP implementa un pool de conexiones con HirakiCP y usa el patrón Singleton
 *  para manejar la instancia de clase.
 *
 * @Author: Alejandra y David
 * @Date: 2024-10-09
 */
public class ClassicModelsCP {

	private static final String CONNECTION_STRING = "jdbc:mariadb://localhost/classicmodels";
	private static final String USERNAME = "classicmodels";
	private static final String PASSWORD = "classicmodels";

	private HikariDataSource dataSource;

	//	Creamos un connection pool con HirakiCP y Singleton.
	@Getter
	private static ClassicModelsCP instance = new ClassicModelsCP();

	private ClassicModelsCP() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(CONNECTION_STRING);
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD);

		dataSource = new HikariDataSource(config);
	}
	/**
	 * Permite obtener una conexión a la BD
	 * @return la conexión a la base de datos.
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}


}
