import java.sql.*;

public class BooksDB {

	public static void main (String[] args) {
		Statement stmt = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			//this is the driver we're using
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting");
			//Execute this to establish connection with mysql. Add your DB username and password as the second and third parameters.
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books?createDatabaseIfNotExist=true", "root", "");
			
			System.out.println("Creating Books Database");
			//Create statement here.
			stmt = con.createStatement();
			String createDatabase = "CREATE DATABASE IF NOT EXISTS Books";
			//Call executeUpdate to execute the command.
			stmt.executeUpdate(createDatabase);
			System.out.println("Database Created");
			
			System.out.println("Connecting to Books");
			System.out.println("Connected");
			
			System.out.println("Creating tables");
			//Creating tables. When adding to a batch, set auto commit to false.
			con.setAutoCommit(false);
			String authorsTable = "CREATE TABLE IF NOT EXISTS authors (authorID INTEGER NOT NULL AUTO_INCREMENT, "
					+ "firstName CHAR(20) NOT NULL,"
					+ "lastName CHAR(20) NOT NULL, "
					+ "PRIMARY KEY (authorID))";
			//Add each of these statements into a batch. If one statement in the batch fails to execute, do not execute any of them.
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
			String authorISBNTable = "CREATE TABLE IF NOT EXISTS authorISBN (authorID INTEGER NOT NULL,"
					+ "isbn CHAR(10) NOT NULL,"
					+ "FOREIGN KEY (ISBN) REFERENCES titles (ISBN),"
					+ "FOREIGN KEY (authorID) REFERENCES authors (authorID))";
			stmt.addBatch(authorISBNTable);
			//Executes batch
			int[] count = stmt.executeBatch();
			//manual commit
			con.commit();
			System.out.println("Tables created");
			
			//Populating the tables by inserting records. This is also done with a batch.
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
					+ "('Stephen', 'King'),"
					+ "('Charles', 'Dickens'),"
					+ "('John', 'Tolkien')";
			stmt.addBatch(insertAuthors);
			String insertPublishers = "INSERT INTO publishers(publisherName)"
					+ "VALUES ('Penguin'),"
					+ "('Puffin'),"
					+ "('Muffin'),"
					+ "('Forest'),"
					+ "('Alpha'),"
					+ "('Bravo'),"
					+ "('Charlie'),"
					+ "('Delta'),"
					+ "('Echo'),"
					+ "('Foxtrot'),"
					+ "('Golf'),"
					+ "('Hotel'),"
					+ "('India'),"
					+ "('Juliett'),"
					+ "('Kilo'),"
					+ "('Lima')";
			stmt.addBatch(insertPublishers);
			String insertTitles = "INSERT INTO titles(ISBN, title, editionNumber, year, publisherID, price)"
					+ "VALUES (010, 'Coldsteel the Hedgehog', 4, 1990, 1, 4.20),"
					+ "(20, 'Nydus their Main', 3, 2001, 1, 4.21),"
					+ "(30, 'How to Chop an Onion', 6, 2040, 5, 5.20),"
					+ "(40, 'Craps', 10, 2000, 15, 4.70),"
					+ "(50, 'Soul of Venture', 23, 1811, 2, 1200.20),"
					+ "(60, 'Judge This', 16, 2010, 5, 18.20),"
					+ "(70, 'The Human Condition', 1, 1600, 7, 0.10),"
					+ "(80, 'Inconvenience', 13, 1290, 13, 44.20),"
					+ "(90, 'Book Burning 101', 11, 1790, 12, 144.20),"
					+ "(100, 'Jiminy Cricket', 6, 1291, 6, 9.20),"
					+ "(110, 'Try Something New', 80, 1391, 9, 9.00),"
					+ "(120, 'Cooking', 10, 2001, 14, 10.00),"
					+ "(130, 'Crach', 3, 900, 12, 9.23),"
					+ "(140, 'Conspiracy Theories', 3, 3, 3, 3.33),"
					+ "(150, 'The End of Time', 15, 2000, 15, 15.20)";
			stmt.addBatch(insertTitles);
			String insertAuthorISBN = "INSERT INTO authorISBN (authorID, ISBN)"
					+ "VALUES (3, 10),"
					+ "(4, 20),"
					+ "(1, 30),"
					+ "(5, 40),"
					+ "(6, 50),"
					+ "(2, 60),"
					+ "(7, 70),"
					+ "(8, 80),"
					+ "(9, 90),"
					+ "(10, 100),"
					+ "(11, 110),"
					+ "(12, 120),"
					+ "(13, 130),"
					+ "(14, 140),"
					+ "(15, 150)";
			stmt.addBatch(insertAuthorISBN);
			int[] count2 = stmt.executeBatch();
			con.commit();
			System.out.println("Tables Populated");
			
			//Set autocommit back to true when we're not executing batches.
			con.setAutoCommit(true);
			
			//Printing out authors' last name and first name. Order alphabetically from lastname then firstname.
			System.out.println("________________________");
			System.out.println("All Authors ordered by lastName, firstName");
			System.out.println("----------------------------------");
			String selectAuthors = "SELECT * FROM authors "
					+ "ORDER BY lastName, firstName";
			rs = stmt.executeQuery(selectAuthors);
			while (rs.next()) {
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				System.out.println(lastName + ", " + firstName);
			}
			rs.close();
			
			//Print all publishers
			System.out.println("________________________");
			System.out.println("All Publishers");
			System.out.println("----------------------------------");
			String selectPublishers = "SELECT * FROM publishers";
			rs = stmt.executeQuery(selectPublishers);
			while (rs.next()) {
				String publishers = rs.getString("publisherName");
				System.out.println(publishers);
			}
			rs.close();
			
			//Print out all titles published by a specific publisher
			System.out.println("________________________");
			System.out.println("All Books from publisher 1");
			System.out.println("----------------------------------");
			String booksFromPublisher = "SELECT title, year, ISBN"
					+ " FROM titles"
					+ " WHERE publisherID = 1 "
					+ " ORDER BY title";
			rs = stmt.executeQuery(booksFromPublisher);
			while (rs.next()) {
				String isbn = rs.getString("ISBN");
				String title = rs.getString("title");
				int year = rs.getInt("year");
				System.out.println(isbn + ", " + title + ", " + year);
			}
			rs.close();
			
			//Adding in a new author. Insert into authors table.
			System.out.println("________________________");
			System.out.println("Add new author");
			System.out.println("----------------------------------");
			String newAuthor = "INSERT INTO authors(firstName, lastName)"
					+ "VALUES ('Jack', 'Black')";
			stmt.executeUpdate(newAuthor);
			
			//Editing this author. Use UPDATE and edit the name of where the authorID was inserted.
			System.out.println("________________________");
			System.out.println("Edit new author");
			System.out.println("----------------------------------");
			String editAuthor = "UPDATE authors"
					+ " SET firstName = 'James', lastName = 'Johnson'"
					+ " WHERE authorID = 16";
			stmt.executeUpdate(editAuthor);
			
			//Adding a new title for a specific author. Insert a new record in the titles table and insert a corresponding record in the authorISBN table.
			System.out.println("________________________");
			System.out.println("Add new title for author");
			System.out.println("----------------------------------");
			String addTitle = "INSERT INTO titles(ISBN, title, editionNumber, year, publisherID, price)"
					+ "VALUES (160, 'New Title', 9, 1991, 15, 4.20)";
			stmt.executeUpdate(addTitle);
			String addISBN = "INSERT INTO authorISBN (authorID, ISBN)"
					+ "VALUES (16, 160)";
			stmt.executeUpdate(addISBN);
			
			//Add a new publisher. Do this by inserting a new record into the publisher table.
			System.out.println("________________________");
			System.out.println("Add new publisher");
			System.out.println("----------------------------------");
			String newPublisher = "INSERT INTO publishers(publisherName)"
					+ "VALUES ('Mockery')";
			stmt.executeUpdate(newPublisher);
			
			//Edit this new publisher by updating the name. Use the id of the record last inserted.
			System.out.println("________________________");
			System.out.println("Edit new publisher");
			System.out.println("----------------------------------");
			String editPublisher = "UPDATE publishers"
					+ " SET publisherName = 'Obviously'"
					+ " WHERE publisherID = 17";
			stmt.executeUpdate(editPublisher);
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		finally {
			//close everything
			try {
				con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Finished");
		}
	}
}
