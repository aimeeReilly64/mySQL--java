
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * JTableDemo
 * @author <a href="mailto:James.Ronholm@CanadoreCollege.ca">James Ronholm</a>
 * @version Nov 2, 2021
 */
public class JTableDemo {

    /**
     * Executable method to start the program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame app = new JFrame("JTable Demo");
        Container contentPane = app.getContentPane();
        contentPane.setLayout(new GridLayout(2,0));
        app.setSize(400,300);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //simple JTable
        String simpleData[][] = { {"one-one", "one-two", "one-three"},
                                  {"two-one", "two-two", "two-three"},
                                  {"three-one", "three-two", "three-three"},
                                  {"four-one", "four-two", "four-three"}
                                };
        JTable tblData = new JTable(simpleData, new String[]{"Col 1", "Col 2", "Col 3"});
        JScrollPane scroller1 = new JScrollPane(tblData);
        contentPane.add(scroller1);
        
        //Table based on SQL query
        //the TableModel takes care of retrieving data
        AuthorsTableModel model = new AuthorsTableModel();
        JTable tblAuthors = new JTable(model);
        
        JScrollPane scroller2 = new JScrollPane(tblAuthors);
        contentPane.add(scroller2);

        app.invalidate();app.validate();
        app.setVisible(true);
    }

}
