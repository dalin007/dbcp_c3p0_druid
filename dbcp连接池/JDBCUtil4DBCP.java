package test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class JDBCUtil4DBCP {
	private static DataSource ds = null;
	private static final ThreadLocal<Connection> tdl = new ThreadLocal<Connection>();
	static {
		try {
			Properties prop = new Properties();
			InputStream is = JDBCUtil4DBCP.class
					.getResourceAsStream("/test/dbcp.properties");
			prop.load(is);
			ds = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = tdl.get();
		if (conn == null) {
			conn = ds.getConnection();
			tdl.set(conn);
		}
		return conn;
	}
}
