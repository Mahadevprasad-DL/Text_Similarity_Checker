import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlagiarismCheckerUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Plagiarism Checker");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Plagiarism Checker", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(50, 120, 220));
        titleLabel.setForeground(Color.WHITE);
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel textContainer = new JPanel(new BorderLayout());
        textContainer.setBorder(new LineBorder(Color.BLACK, 2));
        JTextArea textArea = new JTextArea(5, 40);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textContainer.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton enterTextButton = new JButton("Enter Text");
        JButton uploadFileButton = new JButton("Upload File");
        JButton checkPlagiarismButton = new JButton("Check Plagiarism");
        buttonPanel.add(enterTextButton);
        buttonPanel.add(uploadFileButton);
        buttonPanel.add(checkPlagiarismButton);

        textPanel.add(textContainer, BorderLayout.CENTER);
        textPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(textPanel, BorderLayout.CENTER);

        String[] columns = {"Text", "Link", "Plagiarism %"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.BOLD, 14));
        table.setForeground(Color.BLACK);
        table.setBackground(new Color(245, 245, 245));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(Color.BLUE);
        header.setBackground(Color.LIGHT_GRAY);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(tableScrollPane, BorderLayout.SOUTH);

        // Predefined words and their links (acts like a database)
        Map<String, String> database = new HashMap<>();
        database.put("java programming", "https://www.java.com");
        database.put("python programming", "https://www.python.org");
        database.put("Hello i am Mahadevprasad DL i am from Chamarajanagara ", "https://projects.free.nf/Home.html?i=1");
        database.put("html css", "https://developer.mozilla.org/en-US/");
        database.put("data structures", "https://www.geeksforgeeks.org/data-structures/");

        enterTextButton.addActionListener(e -> textArea.requestFocus());

        uploadFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line, content = "";
                    while ((line = reader.readLine()) != null) {
                        content += line + "\n";
                    }
                    textArea.setText(content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkPlagiarismButton.addActionListener(e -> {
            String enteredText = textArea.getText().trim().toLowerCase();

            if (!enteredText.isEmpty()) {
                if (database.containsKey(enteredText)) {
                    String link = database.get(enteredText);
                    String plagiarismPercentage = (int) (Math.random() * 50 + 10) + "%";
                    model.addRow(new Object[]{enteredText, link, plagiarismPercentage});
                    textArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Sorry, the word is not recognized.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter text or upload a file!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}
