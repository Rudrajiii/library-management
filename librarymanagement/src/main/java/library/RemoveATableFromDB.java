
package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RemoveATableFromDB {
	public static void main(String[] args) {
		try {
			// Explicitly load the SQLite JDBC driver
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("SQLite JDBC driver not found!");
			e.printStackTrace();
			return;
		}
		
		String url = "jdbc:sqlite:library.db";
		String sql = "DROP TABLE IF EXISTS books";
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("Table 'books' dropped successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
