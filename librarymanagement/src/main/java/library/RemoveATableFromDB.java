
package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RemoveATableFromDB {
	public static void main(String[] args) {
		String url = "jdbc:sqlite:library.db";
		String sql = "DROP TABLE IF EXISTS admins";
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("Table 'admins' dropped successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
