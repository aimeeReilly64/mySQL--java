import java.sql.*;

public class DemoDatabase {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/books?user=root&password=");
            String query = "SELECT * FROM authors;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //metadata gives us information about the data
            ResultSetMetaData meta = rs.getMetaData();

            System.out.printf("%d%n", meta.getColumnCount());
            System.out.println(meta.getColumnCount());
            System.out.printf("%d", meta.getColumnType(1));
            System.out.println(meta.getColumnLabel(2));

            //next would bring you to the next row
            //absolute will also bring you to the row you want
            //then you need a get method to retrieve the data(getString or getObject)
            while (rs.next()) {
                System.out.printf("Author ID:%d\tFirst Name:%s\t Last Name:%s\t%n", rs.getInt(1), rs.getString(2), rs.getString("lastName"));
            //System.out.printf("%d: %s%n", rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
 System.out.println("\n Output from day 2");
        try {
            String username = "root";
            String password = "";
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/books?",username,password);
            Statement stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet authors = stmt.executeQuery("SELECT * FROM authors;");
            authors.absolute(6);
            System.out.printf("%s%n", authors.getString("firstname"));
            authors.updateString("firstname", "Emily");
            authors.updateRow();
            System.out.printf("%s%n", authors.getString("firstname"));
            String sql ="INSERT INTO authors (firstName,lastName) VALUES ('John', 'Doe');";
            int numRows = stmt.executeUpdate(sql);
            System.out.printf("There were %d rows affected", numRows);

            boolean hasResults = stmt.execute("UPDATE authors SET lastName = 'Doe' WHERE authorId = 1");
            if (hasResults) {
                ResultSet results = stmt.getResultSet();
                System.out.printf("Results are received");
            }else {
                System.out.printf("There were %d rows affected", stmt.getUpdateCount());
            }

            stmt.addBatch("INSERT INTO authors (firstName,lastName) VALUES ('Jane', 'Dolly')");
            stmt.addBatch("UPDATE authors SET lastName = 'Smith' WHERE authorId = 1");
            stmt.executeBatch();

        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

}
