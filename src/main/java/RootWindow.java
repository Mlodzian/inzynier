import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mldz on 2015-12-30.
 */
public class RootWindow extends JFrame {

    private JPanel rootPanel;
    public DefaultListModel defaultListModel = new DefaultListModel();
    private JList processes;
    JScrollPane scrollPane = new JScrollPane(processes);
    private JPanel procesy;
    private JPanel opis;
    private JTextPane toAddNewFunctionalityTextPane;

    File folder = new File("processes");
    File[] listOfFiles = folder.listFiles();

    public RootWindow() {
        super("Excel extension");

        try {
            this.setIconImage(ImageIO.read(new File("resources/excel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentPane(rootPanel);
        //  pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(dim.width / 2 - this.getSize().width - 450 / 2, dim.height / 2 - this.getSize().height - 400 / 2, 450, 400);
        //     this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);

        processes.setModel(defaultListModel);
        this.setResizable(false);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refreshList();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                refreshList();
            }
        });


        procesy.setLayout(new BorderLayout());
        procesy.add(scrollPane = new JScrollPane(processes));

        refreshList();

    }

    public void refreshList() {
        defaultListModel.clear();
        for (File file : listOfFiles) {
            String fileName = file.getName();
            if (fileName.endsWith(".xml") && !defaultListModel.contains(fileName)) {
                defaultListModel.addElement(fileName);
            }
        }
    }

}
