import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel{
    private JLabel label;
    private JPanel centerPanel;
    private JButton button;
    public MenuPanel(String label, String button){
        super();
        setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.PAGE_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.label = new JLabel(label);
        this.label.setFont(new Font("Arial",Font.BOLD,40));
        this.button = new JButton(button);
        this.button.setFont(new Font("Arial",Font.BOLD,25));
        add(BorderLayout.CENTER,centerPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(1,175)));
        centerPanel.add(this.label);
        centerPanel.add(this.button);
        this.label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void addActionListeners(GameFrame frame){
        button.addActionListener(frame);
    }

    public JButton getButton(){
        return button;
    }

    public JLabel getLabel(){
        return label;
    }
}
