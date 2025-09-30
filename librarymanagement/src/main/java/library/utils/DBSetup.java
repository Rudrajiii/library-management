package library.utils;

import java.sql.Connection;
import java.sql.Statement;

public class DBSetup {
    public static void __init__(){
        try(
            Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement();
        ){
            /**
             * @see create users , admin and books table if not exists
             * @see users table has id, name, enrollment, password fields
             * @see admin table has id, usernname, adminId fields
             * @see books table has id, bookName, available fields
             */
            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "enrollment TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS admins (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "adminId TEXT NOT NULL" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "bookName TEXT NOT NULL," +
                    "available INTEGER NOT NULL" +
                    ");");

            System.out.println(">> Database initialized. <<");
        } catch ( Exception error){
            error.printStackTrace();
        }
    }
    
}
