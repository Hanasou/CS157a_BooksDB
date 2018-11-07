import java.sql.*;

public class BooksDB {

	public static void main (String[] args) {
		Statement stmt = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books?createDatabaseIfNotExist=true", "root", "eraser");
			
			System.out.println("Creating Books Database");
			stmt = con.createStatement();
			String createDatabase = "CREATE DATABASE IF NOT EXISTS Books";
			stmt.executeUpdate(createDatabase);
			System.out.println("Database Created");
			
			System.out.println("Connecting to Books");
			System.out.println("Connected");
			
			System.out.println("Creating tables");
			con.setAutoCommit(false);
			String authorsTable = "CREATE TABLE IF NOT EXISTS authors (authorID INTEGER NOT NULL AUTO_INCREMENT, "
					+ "firstName CHAR(20), "
					+ "lastName CHAR(20), "
					+ "PRIMARY KEY (authorID))";
			stmt.addBatch(authorsTable);
			String authorISBNTable = "CREATE TABLE IF NOT EXISTS authorISBN (authorID INTEGER NOT NULL AUTO_INCREMENT,"
					+ "isbn CHAR(10),"
					+ "FOREIGN KEY (authorID) REFERENCES authors(authorID))";
			stmt.addBatch(authorISBNTable);
			int[] count = stmt.executeBatch();
			con.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		finally {
			try {
				con.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
