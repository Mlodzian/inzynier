package pl.micw.ExcelExtension.atributes;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The class to provide binominal attributes in AttributeColumn
 */
public class BinominalAttribute extends JPanel {

    private String firstOption = "true";
    private String secondOption = "false";
    private Font font = new Font("Courier", Font.ITALIC,12);
    private JTextField firstField;
    private JTextField secondField;
    private boolean firstEditedField = false;
    private boolean secondEditedField = false;
    private Map<Integer, String> fields = new HashMap<>();

    public BinominalAttribute() {
        fields.put(1,firstOption);
        fields.put(2,secondOption);
        this.setVisible(false);
        this.setSize(180, 180);
        this.setLayout(new BoxLayout(this, 1));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Write binominals:"));
        this.add(firstField = new JTextField("true"));
        this.add(Box.createRigidArea(new Dimension(120, 2)));
        this.add(secondField = new JTextField("false"));
        this.add(Box.createRigidArea(new Dimension(120, 2)));
        firstField.setForeground(Color.GRAY);
        firstField.setFont(font);

        secondField.setForeground(Color.GRAY);
        secondField.setFont(font);

        firstField.setMaximumSize(new Dimension(90, 20));
        secondField.setMaximumSize(new Dimension(90, 20));

        firstField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(firstEditedField==false) {
                    firstField.setFont(UIManager.getDefaults().getFont(this));
                    firstField.setForeground(Color.BLACK);
                    firstEditedField=true;
                    fields.put(1,firstOption);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(firstEditedField==false) {
                    firstField.setFont(UIManager.getDefaults().getFont(this));
                    firstField.setForeground(Color.BLACK);
                    firstEditedField = true;
                    fields.put(1, firstOption);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(firstEditedField==false) {
                    firstField.setFont(UIManager.getDefaults().getFont(this));
                    firstField.setForeground(Color.BLACK);
                    firstEditedField = true;
                    fields.put(1, firstOption);
                }
            }
        });

        secondField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(secondEditedField==false) {
                    secondField.setFont(UIManager.getDefaults().getFont(this));
                    secondField.setForeground(Color.BLACK);
                    secondEditedField = true;
                    fields.put(2, secondOption);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(secondEditedField==false) {
                    secondField.setFont(UIManager.getDefaults().getFont(this));
                    secondField.setForeground(Color.BLACK);
                    secondEditedField = true;
                    fields.put(2, secondOption);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(secondEditedField==false) {
                    secondField.setFont(UIManager.getDefaults().getFont(this));
                    secondField.setForeground(Color.BLACK);
                    secondEditedField = true;
                    fields.put(2, secondOption);
                }
            }
        });
    }

    public Map<Integer, String> getFields() {
        return fields;
    }
}