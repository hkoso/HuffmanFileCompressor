import javax.swing.*;
import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileSavedMessage extends JFrame {
    private JButton ok = new JButton("OK");

    public FileSavedMessage() {
        this.setLayout(new GridLayout(2, 1));

        JPanel button = new JPanel();
        button.add(ok);

        Icon icon = UIManager.getIcon("OptionPane.informationIcon");

        JLabel image = new JLabel(icon);
        JLabel infoText = new JLabel("File Processed Successfully!");
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout());
        info.add(image);
        info.add(infoText);

        this.add(info);
        this.add(button);

        ButtonListener close = new ButtonListener();

        ok.addActionListener(close);

        this.setTitle("Success");
        this.setSize(300, 120);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ok) {
                System.exit(0);
            }
        }
    }
}
