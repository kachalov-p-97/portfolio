import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm {
    private JPanel mainPanel;
    private JButton expandButton;
    private JTextArea textName;
    private JTextArea textPatronymic;
    private JTextArea textSurname;
    private JTextArea textFio;

    private JPanel fio;
    private JPanel surname;
    private JPanel name;
    private JPanel patronymic;
    private JPanel panelButton;


    boolean temp = true;

    public MainForm() {
        fio.setVisible(false);
        if (!textName.toString().equals("") & !textPatronymic.toString().equals("") & !textSurname.toString().equals("")) {
            expandButton.addActionListener(new Action() {
                @Override
                public Object getValue(String key) {
                    return null;
                }

                @Override
                public void putValue(String key, Object value) {

                }

                @Override
                public void setEnabled(boolean b) {

                }

                @Override
                public boolean isEnabled() {
                    return false;
                }

                @Override
                public void addPropertyChangeListener(PropertyChangeListener listener) {

                }

                @Override
                public void removePropertyChangeListener(PropertyChangeListener listener) {

                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean temp = textName.getText().equals("") & textPatronymic.getText().equals("") & textSurname.getText().equals("");
                    if (temp) {
                        JOptionPane.showMessageDialog(mainPanel, "Заполните все поля");
                        return;
                    }
                    textChange();
                    buttonChange();
                }
            });

        }

    }

    private void textChange() {
        String[] arrayString;
        if (temp) {
            textFio.setText(textSurname.getText() + " " + textName.getText() + " " + textPatronymic.getText());
            arrayString = textFio.getText().split(" ");
            System.out.println(arrayString.length);
            if (check(arrayString.length)) {
                JOptionPane.showMessageDialog(mainPanel, "Неверный ввод");
                return;
            }
            temp = false;
            fio.setVisible(true);
            surname.setVisible(false);
            name.setVisible(false);
            patronymic.setVisible(false);
            buttonChange();
            return;
        }
        arrayString = textFio.getText().split(" ");
        if (check(arrayString.length)) {
            JOptionPane.showMessageDialog(mainPanel, "Неверный ввод");
            return;
        }
        temp = true;
        textSurname.setText(arrayString[0]);
        textName.setText(arrayString[1]);
        textPatronymic.setText(arrayString[2]);
        fio.setVisible(false);
        surname.setVisible(true);
        name.setVisible(true);
        patronymic.setVisible(true);
        buttonChange();

    }

    public void buttonChange() {
        expandButton.setText(temp ? "Collapse" : "Expand");
    }

    public Boolean check(int count) {
        return count > 3;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
