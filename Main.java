import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {

    JTextField nomeField = new JTextField();
    JTextField cognomeField = new JTextField();
    JTextField telefonoField = new JTextField();
    JTextField emailField = new JTextField();

    DefaultTableModel model = new DefaultTableModel();

    static final String FILE_NAME = "contacts.csv";

    public Main() {

        setTitle("Rubrica Desktop");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Nome"));
        panel.add(nomeField);

        panel.add(new JLabel("Cognome"));
        panel.add(cognomeField);

        panel.add(new JLabel("Telefono"));
        panel.add(telefonoField);

        panel.add(new JLabel("Email"));
        panel.add(emailField);

        JButton button = new JButton("Aggiungi");
        panel.add(button);

        add(panel, BorderLayout.NORTH);

        model.addColumn("Nome");
        model.addColumn("Cognome");
        model.addColumn("Telefono");
        model.addColumn("Email");

        JTable table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadContacts();

        button.addActionListener(e -> {

            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String telefono = telefonoField.getText();
            String email = emailField.getText();

            if (nome.isEmpty() || cognome.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Inserisci nome e cognome");

                return;
            }

            model.addRow(new Object[]{
                    nome,
                    cognome,
                    telefono,
                    email
            });

            saveContacts();

            nomeField.setText("");
            cognomeField.setText("");
            telefonoField.setText("");
            emailField.setText("");
        });

        setVisible(true);
    }

    void saveContacts() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (int i = 0; i < model.getRowCount(); i++) {

                writer.write(
                        model.getValueAt(i, 0) + "," +
                        model.getValueAt(i, 1) + "," +
                        model.getValueAt(i, 2) + "," +
                        model.getValueAt(i, 3)
                );

                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void loadContacts() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) return;

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                model.addRow(data);
            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        new Main();
    }
}
