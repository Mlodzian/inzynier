package pl.micw.ExcelExtension.GUI;

import pl.micw.ExcelExtension.GeneratedProcess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main JFrame form.
 */
public class RootForm extends JFrame {

    private JPanel rootPanel;
    private DefaultListModel defaultListModel = new DefaultListModel();
    private JList processes;
    private JScrollPane scrollPane = new JScrollPane(processes);
    private JPanel processesPanel;
    private JPanel descriptionPanel;
    private JTextPane firstRunExcellSheetTextPane;
    private JButton addNewProcessButton;
    private JPanel butttonPanel;
    private JButton removeProcessButton;
    private JLabel command;
    volatile private static List<GeneratedProcess> rapidminerProcesses = new ArrayList<>();
    private final int WIDTH = 450;
    private final int HEIGHT = 400;
    private int selectedIndex = -1;

    private static Set<String> processNames = new HashSet<>();

    public RootForm() {
        super("Excel extension");

        try {
            this.setIconImage(ImageIO.read(new File("resources/excel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(dim.width / 2 - this.getSize().width - 450 / 2, dim.height / 2 - this.getSize().height - 400 / 2, WIDTH, HEIGHT);
        this.setVisible(true);

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

        addNewProcessButton.addActionListener(e -> {
            AddProcessFrame addProcesForm = new AddProcessFrame();
        });

        removeProcessButton.addActionListener(e -> {
            removeProcess(processes.getSelectedIndex());
            refreshList();
        });

        processes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        processes.addMouseListener(new MouseAdapter() {
                                       @Override
                                       public void mouseReleased(MouseEvent e) {
                                           selectedIndex = processes.getSelectedIndex();
                                       }
                                   }
        );

        processes.setMaximumSize(new Dimension(WIDTH-90, 100));
        processes.setPreferredSize(new Dimension(WIDTH-90, 100));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(selectedIndex != -1) {
                    processes.setSelectedIndex(selectedIndex);
                }
            }
        });

        processesPanel.setLayout(new BorderLayout());
        processesPanel.add(scrollPane = new JScrollPane(processes));
        refreshList();

    }

    public static boolean checkProcessNames(String name){
        return processNames.add(name);
    }

    public static List<GeneratedProcess> getRapidminerProcesses() {
        return rapidminerProcesses;
    }

    public static boolean AddRapidminerProcess(GeneratedProcess rp) {
        if(checkProcessNames(rp.getProcessName())) {
            rapidminerProcesses.add(rp);
            System.out.println(rp);
            return true;
        }
        else
            return false;
    }

    public void removeProcess(int index) {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Please choose correct process!", "Removing...", JOptionPane.PLAIN_MESSAGE);
        } else {
            defaultListModel.remove(index);
            rapidminerProcesses.remove(index);
            selectedIndex=-1;
        }
    }

    private void refreshList() {
        if (rapidminerProcesses.size() > 0) {
            defaultListModel.clear();
            for (GeneratedProcess generatedProcess : rapidminerProcesses) {
                defaultListModel.addElement(generatedProcess.getProcessName());
            }
        }
        this.revalidate();
        processesPanel.revalidate();
    }

}
