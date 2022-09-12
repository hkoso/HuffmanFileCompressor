import javax.swing.*;
import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileNotExistMessage extends JFrame {
    private JButton exit = new JButton("Exit");

    public FileNotExistMessage() {
        this.setLayout(new GridLayout(2, 1));

        JPanel button = new JPanel();
        button.add(exit);

        Icon icon = UIManager.getIcon("OptionPane.errorIcon");

        JLabel image = new JLabel(icon);
        JLabel infoText = new JLabel("File does not exist!");
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout());
        info.add(image);
        info.add(infoText);

        this.add(info);
        this.add(button);

        ButtonListener close = new ButtonListener();

        exit.addActionListener(close);

        this.setTitle("Failed");
        this.setSize(300, 120);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == exit) {
                System.exit(1);
            }
        }
    }
}
