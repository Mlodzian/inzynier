package pl.micw.ExcelExtension;

import pl.micw.ExcelExtension.GUI.AddProcessFrame;
import pl.micw.ExcelExtension.atributes.BinominalAttribute;
import pl.micw.ExcelExtension.atributes.PolynominalAtribute;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * The class to create atribute panel in addProcesFrame where user can edit via GUI essential information.
 */
public class AttributeColumn extends JPanel {

    private JButton usunButton = new JButton("Usuń");
    private JTextField nazwaField = new JTextField();
    private JComboBox rodzajComboBox = new JComboBox();
    private Box horizontalBox = Box.createVerticalBox();
    private Map<Integer,String> fields;

    private PolynominalAtribute polynominalAtribute = new PolynominalAtribute();
    private BinominalAttribute binominalAtribute = new BinominalAttribute();
    private AddProcessFrame addProcessFrame = AddProcessFrame.getAddProcessFrame();

    private int index;
    private String atributeName;
    private OntologyTypes ontologyType;
    private String atrName;

    public String getAtrName() {
        return atrName;
    }

    public AttributeColumn(int index) {

        this.setPreferredSize(new Dimension(140, 240));

        nazwaField.setMaximumSize(new Dimension(150, 20));
        rodzajComboBox.setMaximumSize(new Dimension(150, 20));

        this.index = index;
        atributeName = "Atribute no. " + (index + 1);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        rodzajComboBox.addItem("real");
        rodzajComboBox.addItem("binominal");
        rodzajComboBox.addItem("polynominal");

        horizontalBox.add(nazwaField);
        horizontalBox.add(rodzajComboBox);
        horizontalBox.add(binominalAtribute);
        horizontalBox.add(polynominalAtribute);
        horizontalBox.add(Box.createGlue());
        horizontalBox.add(usunButton);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 10)));

        this.add(horizontalBox);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), atributeName));
        this.setVisible(true);

        usunButton.addActionListener(e -> addProcessFrame.deleteAtribute(this.index));

        nazwaField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atrName = nazwaField.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atrName = nazwaField.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atrName = nazwaField.getText();
            }
        });

        ontologyType = OntologyTypes.REAL;

        rodzajComboBox.addActionListener(e -> {
            ontologyType = OntologyTypes.valueOf(((String) rodzajComboBox.getSelectedItem()).toUpperCase());
            setVisiblePanels();
            this.revalidate();
            addProcessFrame.repaint();
        });
    }

    public Map<Integer, String> getFields() {
        return fields;
    }

    public OntologyTypes getOntologyType() {
        return ontologyType;
    }

    public void updateIndex(int newIndex) {
        this.index = newIndex;
        atributeName = "Attribute no. " + (index + 1);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), atributeName));
        this.revalidate();
    }

    private void setVisiblePanels() {
        fields = new HashMap<>();
        switch (ontologyType) {
            case REAL:
                binominalAtribute.setVisible(false);
                polynominalAtribute.setVisible(false);
                break;
            case BINOMINAL:
                binominalAtribute.setVisible(true);
                polynominalAtribute.setVisible(false);
                fields = binominalAtribute.getFields();
                break;
            case POLYNOMINAL:
                binominalAtribute.setVisible(false);
                polynominalAtribute.setVisible(true);
                fields = polynominalAtribute.getFields();
                break;
        }
    }
}