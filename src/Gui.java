import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gui {

    JFrame frame;
    JTextArea leftTextArea, rightTextArea;
    JButton leftButton, rightButton;
    JPanel leftPanel, rightPanel;


    Gui() {
        guiSetter();
    }

    private void guiSetter() {

        frame = new JFrame("StopResume");

        frame.setSize(600, 400);

        leftPanel = new JPanel(new GridLayout(2, 1));
        rightPanel = new JPanel(new GridLayout(2, 1));

        leftButton = new JButton("SUSP");
        rightButton = new JButton("SUSP");

        leftTextArea = new JTextArea();
        rightTextArea = new JTextArea();

        JScrollPane scrollLeftTextArea = new JScrollPane(leftTextArea);
        scrollLeftTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollPane scrollRightTextArea = new JScrollPane(rightTextArea);
        scrollRightTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        final MyThread leftThread = new MyThread(leftTextArea, leftButton);

        final MyThread rightThread = new MyThread(rightTextArea, rightButton);

        DefaultCaret caretLeft = (DefaultCaret) leftTextArea.getCaret();
        caretLeft.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        DefaultCaret caretRight = (DefaultCaret) rightTextArea.getCaret();
        caretRight.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        leftPanel.add(scrollLeftTextArea);
        leftPanel.add(leftButton);

        rightPanel.add(scrollRightTextArea);
        rightPanel.add(rightButton);

        frame.setLayout(new GridLayout(1, 2));

        frame.add(leftPanel);

        frame.add(rightPanel);

        leftButton.addActionListener(new ActionListener() {
            int opo = 0;

            @Override
            public void actionPerformed(ActionEvent e) {

                opo++;

                if (opo % 2 == 0) {
                    leftThread.setLock(false);

                }
                if (opo % 2 != 0) {
                    leftThread.setLock(true);

                }

            }
        });


        rightButton.addActionListener(new ActionListener() {
            int oed = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                oed++;

                if (oed % 2 == 0) {
                    rightThread.setLock(false);

                }
                if (oed % 2 != 0) {
                    rightThread.setLock(true);

                }

            }
        });

        frame.setVisible(true);

        frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int reply = JOptionPane.showConfirmDialog(null,
                        "Czy chcesz wyjść ?", "Dialog", JOptionPane.OK_CANCEL_OPTION);
                if (reply == JOptionPane.YES_OPTION) //obsługa rekacji po kliknięciu Yes
                    System.exit(0);
            }
        });

    }

}
