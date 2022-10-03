import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Ventana que muestra una lista con los habitantes registrados
public class VentanaLista {
    public JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton volverButton;
    private JButton eliminarButton;
    private JTextField filtroT;
    private JButton filtrarButton;
    private JComboBox regionSeleccionada;
    private Regiones regiones;
    private Region region = null;

    public void refresh() {
        ArrayList<Persona> arreglo;
        String[] column = {"Región", "Nombre", "Rut", "Edad", "Función", "Nacionalidad"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        table.setDefaultEditor(Object.class, null);

        if (region != null) {
            for (Persona persona : region.getArray()) {
                tableModel.addRow(persona.getObjs());
            }
        } else {
            for (int i = 1; i < 16; i++) {
                try {
                    arreglo = regiones.getRegion(i).getArray();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (arreglo.size() == 0) continue;
                for (Persona persona : arreglo) {
                    tableModel.addRow(persona.getObjs());
                }
            }
        }
        table.setModel(tableModel);
    }

    public VentanaLista(Regiones regiones, JFrame frame) {
        this.regiones = regiones;
        this.refresh();
        volverButton.addActionListener(e -> {
            frame.setContentPane(new VentanaMenu(regiones, frame).panel);
            frame.setSize(512, 512);
        });
        eliminarButton.addActionListener(e -> {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int[] seleccionadas = table.getSelectedRows();
            if (seleccionadas.length == 0) return;
            for (int i : seleccionadas) {
                String rut = (String) tableModel.getValueAt(i, 2);
                Persona persona = regiones.buscarPersona(rut);

                persona.getRegion().eliminarPersona(persona);
            }

            int eliminadas = 0;
            for (int pos : seleccionadas) {
                tableModel.removeRow(pos - eliminadas);
                eliminadas++;
            }


        });

        filtrarButton.addActionListener(e -> {
            String filtro = filtroT.getText();
            System.out.println(filtro);
            ArrayList<Persona> personas;
            if (region == null) {
                personas = regiones.getPersonas();
            } else {
                personas = region.getArray();
            }
            String[] column = {"Región", "Nombre", "Rut", "Edad", "Función", "Nacionalidad"};
            DefaultTableModel nuevoModel = new DefaultTableModel(column, 0);
            for (Persona persona : personas) {
                Object[] objs = persona.getObjs();
                for (Object obj : objs) { // revisa si alguno de sus campos contiene el filtro
                    String string;
                    if (obj instanceof String) {
                        string = (String) obj;
                    } else {
                        string = String.valueOf(obj);
                    }
                    if (string.contains(filtro)) {
                        nuevoModel.addRow(objs);
                        break;
                    }
                }
            }
            table.setModel(nuevoModel);
        });
        regionSeleccionada.addActionListener(e -> {
            String stringSeleccion = ((String) regionSeleccionada.getSelectedItem()).substring(0, 2);
            try {
                int i = Integer.parseInt(stringSeleccion.replaceAll("\\.", "")); // elimina punto
                region = regiones.getRegion(i);
            } catch (Exception exc) {
                region = null;
            }
            refresh();
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane = new JScrollPane();
        panel.add(scrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable();
        scrollPane.setViewportView(table);
        volverButton = new JButton();
        volverButton.setText("Volver");
        panel.add(volverButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eliminarButton = new JButton();
        eliminarButton.setText("Eliminar selección");
        panel.add(eliminarButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filtroT = new JTextField();
        panel.add(filtroT, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        filtrarButton = new JButton();
        filtrarButton.setText("Filtrar");
        panel.add(filtrarButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        regionSeleccionada = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Todas las regiones");
        defaultComboBoxModel1.addElement("1. Tarapacá");
        defaultComboBoxModel1.addElement("2. Antofagasta");
        defaultComboBoxModel1.addElement("3. Atacama");
        defaultComboBoxModel1.addElement("4. Coquimbo");
        defaultComboBoxModel1.addElement("5. Valparaíso");
        defaultComboBoxModel1.addElement("6. Libertador General Bernardo O’Higgins");
        defaultComboBoxModel1.addElement("7. Maule");
        defaultComboBoxModel1.addElement("8. Biobío");
        defaultComboBoxModel1.addElement("9. La Araucanía");
        defaultComboBoxModel1.addElement("10. Los Lagos");
        defaultComboBoxModel1.addElement("11. Aysén del General Carlos Ibáñez del Campo");
        defaultComboBoxModel1.addElement("12. Magallanes y Antártica Chilena");
        defaultComboBoxModel1.addElement("13. Metropolitana de Santiago");
        defaultComboBoxModel1.addElement("14. Los Ríos");
        defaultComboBoxModel1.addElement("15. Arica y Parinacota");
        defaultComboBoxModel1.addElement("16. Ñuble");
        regionSeleccionada.setModel(defaultComboBoxModel1);
        panel.add(regionSeleccionada, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
