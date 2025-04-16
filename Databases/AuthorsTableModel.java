
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

/**
 * AuthorsTableModel - a table model to contain the data for a JTable
 *
 * @author <a href="mailto:James.Ronholm@CanadoreCollege.ca">James Ronholm</a>
 * @version Nov 2, 2021
 */
public class AuthorsTableModel extends AbstractTableModel {

    private ResultSet resultSet;
    private ResultSetMetaData metaData;

    /**
     * The constructor connects to the Authors table and queries for the data
     */
    public AuthorsTableModel() {
        final String DATABASE_URL = "jdbc:mariadb://localhost:3306/books";
        final String SELECT_QUERY
                = "SELECT authorID, firstName, lastName FROM authors";
        try ( Connection connection = DriverManager.getConnection(
                DATABASE_URL, "root", "")) {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_QUERY);
            metaData = resultSet.getMetaData();

            resultSet.first();
            System.out.println(resultSet.getString("firstName"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    /**
     * Get the number of rows for the table
     *
     * @return the number of rows in this ResultSet
     */
    @Override
    public int getRowCount() {
        int result = 0;
        try {
            resultSet.last();
            result = resultSet.getRow();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * get the number of columns for the table
     *
     * @return the number of columns
     */
    @Override
    public int getColumnCount() {

        int result = 0;

        try {
            result = metaData.getColumnCount();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * return a single value from the TableModel
     * @param row the row to retrieve from
     * @param col the column to retrieve from
     * @return the value retrieved
     */
    @Override
    public Object getValueAt(int row, int col) {
        Object result = "error";

        try {
            resultSet.first();

            int rowCount = 0;
            while (rowCount < row) {
                rowCount++;
                resultSet.next();
            }
            result = resultSet.getObject(col + 1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = ex;
        }
        System.out.printf("getValueAt %s at %d, %d%n", result, row, col);
        return result;
    }

    /**
     * Get column names to be displayed in the JTable
     * @param col the column we want a name for
     * @return the name (note this implementation used the getColumnLabel(int)
     * method of the underlying ResultSetMetaData which might be different than
     * the actual column name in the database
     */
    @Override
    public String getColumnName(int col) {
        String result = "";
        try {
            result = metaData.getColumnLabel(col + 1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * This method specifies whether a given cell is editable - currently
     * returns false for every cell.
     * @param row the row the specified cell is in
     * @param col the column the specified cell is in
     * @return whether the given cell is editable
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * not currently implemented - a method to set a specific value in the
     * TableModel - would be useful if some outside event was able to change
     * the values
     * @param value the new value
     * @param row the row where the cell is
     * @param col the column where the cell is
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        //TODO currently empty method
        System.out.println("Called setvalueat");
    }

    /**
     * From "The Java Tutorial TableDemo.java" linked from
     * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
     * helps make sure data is displayed in a format that is suitable
     *
     * @param c
     * @return the class type of the objects displayed under a column
     */
    @Override
    public Class getColumnClass(int c) {
        System.out.printf("getColumnClass %s%n", getValueAt(0, c).getClass());
        return getValueAt(0, c).getClass();
    }

}
