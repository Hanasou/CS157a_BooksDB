import java.sql.*;

public class BooksDB {

	public static void main (String[] args) {
		Statement stmt = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting");
			//Add your DB username and password into the second and third fields
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
					+ "firstName CHAR(20) NOT NULL,"
					+ "lastName CHAR(20) NOT NULL, "
					+ "PRIMARY KEY (authorID))";
			stmt.addBatch(authorsTable);
			String publishersTable = "CREATE TABLE IF NOT EXISTS publishers (publisherID INTEGER NOT NULL AUTO_INCREMENT, "
					+ "publisherName CHAR(100) NOT NULL, "
					+ "PRIMARY KEY (publisherID))";
			stmt.addBatch(publishersTable);
			String titlesTable = "CREATE TABLE IF NOT EXISTS titles (ISBN CHAR(10) NOT NULL,"
					+ "title VARCHAR(500) NOT NULL,"
					+ "editionNumber INTEGER NOT NULL,"
					+ "year CHAR(4) NOT NULL,"
					+ "publisherID INTEGER NOT NULL,"
					+ "price DECIMAL(8,2) NOT NULL,"
					+ "PRIMARY KEY (ISBN),"
					+ "FOREIGN KEY (publisherID) REFERENCES publishers (publisherID))";
			stmt.addBatch(titlesTable);
			String authorISBNTable = "CREATE TABLE IF NOT EXISTS authorISBN (authorID INTEGER NOT NULL AUTO_INCREMENT,"
					+ "isbn CHAR(10) NOT NULL,"
					+ "FOREIGN KEY (ISBN) REFERENCES titles (ISBN),"
					+ "FOREIGN KEY (authorID) REFERENCES authors (authorID))";
			stmt.addBatch(authorISBNTable);
			int[] count = stmt.executeBatch();
			con.commit();
			System.out.println("Tables created");
			
			//TODO: Insert records and everything else
			System.out.println("Inserting Records");
			String insertAuthors = "INSERT INTO authors(firstName, lastName)"
					+ "VALUES ('John', 'Doe'),"
					+ "('Michael', 'Faraday'),"
					+ "('Alan', 'Turing'),"
					+ "('Door', 'Kicker'),"
					+ "('James', 'Watson'),"
					+ "('Sherlock', 'Holmes'),"
					+ "('Real', 'Name'),"
					+ "('Todd', 'Howard'),"
					+ "('Gabe', 'Newell'),"
					+ "('Fyodor', 'Dostoyevsky'),"
					+ "('George', 'Orwell'),"
					+ "('Ernest', 'Hemingway'),"
					+ "('Neil', 'Gaiman'),"
					+ "('Charles', 'Dickens'),"
					+ "('John', 'Tolkien')";
			stmt.addBatch(insertAuthors);
			String insertPublishers = "";
			String insertTitles = "";
			String insertAuthorISBN = "";
			int[] count2 = stmt.executeBatch();
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
			System.out.println("Finished");
		}
	}
}
