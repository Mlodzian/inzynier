package pl.micw.ExcelExtension.GUI;

import pl.micw.ExcelExtension.AddedAttribute;
import pl.micw.ExcelExtension.AttributeColumn;
import pl.micw.ExcelExtension.GeneratedProcess;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * JFrame form used to configure new process.
 */
public class AddProcessFrame extends JFrame {
    private JButton cancelButton;
    private JButton addProcessButton;
    private JPanel addProcessPanel;
    private JButton addAtributeButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel panelOfAtributes;
    private JPanel atributekPanel;
    private JPanel middlePanel;
    private JButton selectProcessPathButton;
    private JLabel pathInfoLabel;
    private JTextField processNameField;
    private JScrollPane atributeScroll;
    private JScrollPane scrollPane;
    public List<AddedAttribute> attributes;
    public static AddProcessFrame addProcesFrame;
    public Path processPath;
    List<AttributeColumn> atributeColumns = new ArrayList<>();
    int quantity;
    int atributeId;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    final int FORM_WIDTH = 700;
    final int FORM_HEIGHT = 390;
    private String processName;
    private GeneratedProcess generatedRapidminerProcess;


    public AddProcessFrame() {
        super("Add process...");

        attributes = new ArrayList<>();
        addProcesFrame = this;

        this.setVisible(true);

        try {
            this.setIconImage(ImageIO.read(new File("resources/excel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentPane(addProcessPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBounds(dim.width / 2 - FORM_WIDTH / 2, dim.height / 2 - FORM_HEIGHT / 2, FORM_WIDTH, FORM_HEIGHT);
        this.setMinimumSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));

        panelOfAtributes.setVisible(true);
        middlePanel.setLayout(new BorderLayout());
        middlePanel.add(scrollPane = new JScrollPane(panelOfAtributes));
        panelOfAtributes.setLayout(new FlowLayout(FlowLayout.CENTER));
        scrollPane.setVisible(true);


        addAtributeButton.addActionListener(e -> {
            atributeColumns.add(new AttributeColumn(quantity));
            AttributeColumn atributeColumn = atributeColumns.get(quantity);
            panelOfAtributes.add(atributeColumn);
            panelOfAtributes.revalidate();
            middlePanel.revalidate();
            atributeId++;
            quantity++;
        });

        processNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                processName=processNameField.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                processName=processNameField.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                processName=processNameField.getText();
            }
        });


        cancelButton.addActionListener(e -> {
            this.dispose();
        });

        selectProcessPathButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML & RMP Rapidminer processes", "xml", "rmp");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(addProcesFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                processPath = FileSystems.getDefault().getPath(chooser.getSelectedFile().getAbsolutePath());
                pathInfoLabel.setText("Selected: \"" + processPath.toString() + "\"");
            }
        });


        addProcessButton.addActionListener(e -> {
            if(processNameField.getText().equals("") || atributeColumns.size()==0) {
                JOptionPane.showMessageDialog(null, "Please add attributes, process path and name", "Fill data...", JOptionPane.PLAIN_MESSAGE);
            }else {
                for (int index = 0; index < atributeColumns.size(); index++) {
                    AttributeColumn atr = atributeColumns.get(index);
                    attributes.add(new AddedAttribute(atr.getAtrName(), atr.getOntologyType(), atr.getFields()));
                }
                if(!RootForm.AddRapidminerProcess(new GeneratedProcess(processName, attributes, processPath))){
                    JOptionPane.showMessageDialog(null, "Process with this name already exist! Please rename process name. ",
                            "Dupicated process name", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    this.dispose();
                }
            }
        });
    }

    public static AddProcessFrame getAddProcessFrame(){
        return addProcesFrame;
    }

    public void deleteAtribute(int index) {
        atributeColumns.get(index).setVisible(false);
        atributeColumns.remove(index);
        updateIndexesOfAtributes();
        middlePanel.revalidate();
        panelOfAtributes.revalidate();
        --quantity;
    }

    public void updateIndexesOfAtributes() {
        for (int index = 0; index < atributeColumns.size(); index++) {
            atributeColumns.get(index).updateIndex(index);
        }
    }

    public GeneratedProcess getGeneratedRapidminerProcess() {
        return generatedRapidminerProcess;
    }
}
