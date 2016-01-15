package pl.micw.ExcelExtension.GUI;

import pl.micw.ExcelExtension.GeneratedProcess;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
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
public class RootFrame extends JFrame {

    private JPanel rootPanel;
    private DefaultListModel defaultListModel = new DefaultListModel();
    private JList processes;
    private JScrollPane scrollPane = new JScrollPane(processes);
    private JPanel processesPanel;
    private JPanel descriptionPanel;
    private JButton addNewProcessButton;
    private JPanel butttonPanel;
    private JButton removeProcessButton;
    private JTextPane descriptionTextPane;
    private JTextPane command;
    private static List<GeneratedProcess> rapidminerProcesses = new ArrayList<>();
    private static final int WIDTH = 450;
    private static final int HEIGHT = 400;
    private static final int LIST_WIDTH = 350;
    private static final int LIST_HEIGHT = 100;
    private int selectedIndex = -1;
    private static Set<String> processNames = new HashSet<>();

    public RootFrame() {
        super("Excel extension");

        SimpleAttributeSet sa = new SimpleAttributeSet();
        StyleConstants.setAlignment(sa, StyleConstants.ALIGN_JUSTIFIED);

        descriptionTextPane.setOpaque(false);
        command.setOpaque(false);
        descriptionTextPane.getStyledDocument().setParagraphAttributes(0,LIST_WIDTH,sa,false);

        try {
            this.setIconImage(ImageIO.read(new File("resources/excel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(dim.width / 2 - this.getSize().width - WIDTH / 2,
                dim.height / 2 - this.getSize().height - HEIGHT / 2, WIDTH, HEIGHT);
        this.setVisible(true);

        processes.setModel(defaultListModel);
        this.setResizable(false);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                refreshList();
            }

            @Override
            public void mouseMoved(final MouseEvent e) {
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
                                       public void mouseReleased(final MouseEvent e) {
                                           selectedIndex = processes.getSelectedIndex();
                                       }
                                   }
        );
        processes.setMaximumSize(new Dimension(LIST_WIDTH, LIST_HEIGHT));
        processes.setPreferredSize(new Dimension(LIST_WIDTH, LIST_HEIGHT));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (selectedIndex != -1) {
                    processes.setSelectedIndex(selectedIndex);
                }
            }
        });

        processesPanel.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(processes);
        processesPanel.add(scrollPane);
        refreshList();

    }

    public static boolean checkProcessNames(final String name) {
        return processNames.add(name);
    }

    public static List<GeneratedProcess> getRapidminerProcesses() {
        return rapidminerProcesses;
    }

    public static boolean addRapidminerProcess(final GeneratedProcess rp) {
        if (checkProcessNames(rp.getProcessName())) {
            rapidminerProcesses.add(rp);
            System.out.println(rp);
            return true;
        } else {
            return false;
        }
    }

    public final void removeProcess(final int index) {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Please choose correct process!",
                    "Removing...", JOptionPane.PLAIN_MESSAGE);
        } else {
            processNames.remove(defaultListModel.remove(index));
            rapidminerProcesses.remove(index);
            selectedIndex = -1;
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
