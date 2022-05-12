package com.slimtrade.core.saving;

public interface SavableComponent {

    void save();

    void load();

//    private Object saveFile;
//    private JComponent component;
//    private Field field;
//
//    public SavableComponent(JComponent component, String fieldName, Object saveFile) {
//        this.saveFile = saveFile;
//        this.component = component;
//        try {
//            this.field = saveFile.getClass().getField(fieldName);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void save() {
//        try {
//            saveField();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void load() {
//        try {
//            loadField();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveField() throws IllegalAccessException {
//        if (component instanceof JCheckBox) {
//            field.setBoolean(saveFile, ((JCheckBox) component).isSelected());
//        } else if (component instanceof JTextField) {
//            field.set(saveFile, ((JTextField) component).getText());
//        } else if (component instanceof JComboBox) {
//            field.set(saveFile, ((JComboBox<?>) component).getSelectedItem());
//        } else {
//            System.out.println("UNREGISTERED SAVE TYPE");
//        }
//    }
//
//    private void loadField() throws IllegalAccessException {
//        if (component instanceof JCheckBox) {
//            ((JCheckBox) component).setSelected(field.getBoolean(saveFile));
//        } else if (component instanceof JTextField) {
//            ((JTextField) component).setText((String) field.get(saveFile));
//        } else if (component instanceof JComboBox) {
//            ((JComboBox<?>) component).setSelectedItem(field.get(saveFile));
//        } else {
//            System.out.println("UNREGISTERED SAVE TYPE");
//        }
//    }

}