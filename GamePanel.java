import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private boolean answered; //If a question has already been answered, that answer is not allowed to be changed.
    private JPanel centerPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JButton reset = new JButton("Reset");
    private JButton next = new JButton("Next");
    private JButton exit = new JButton("Exit");
    private JButton previous = new JButton("Previous");
    private JLabel definition = new JLabel();
    private JButton answer0 = new JButton();
    private JButton answer1 = new JButton();
    private JButton answer2 = new JButton();
    private JButton answer3 = new JButton();
    private Color answerColor = new Color(200,200,255);
    private Color optionsColor = new Color(175,175,200);
    public GamePanel(int index, String definition, String answer0, String answer1, String answer2, String answer3) {
        super();
        setLayout(new BorderLayout());
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        eastPanel.setLayout(new GridLayout(2, 1, 5, 5));
        westPanel.setLayout(new GridLayout(2, 1, 5, 5));
        northPanel.setLayout(new GridLayout(0, 1));
        answered = false;
        this.definition.setText("<html>" + (index + 1) + ". " + definition + "</html>");
        //The html tags make it so the definition continues in a new line if it runs out of space
        this.definition.setFont(new Font("Arial", Font.BOLD, 25));
        setupAnswerButton(this.answer0,answer0);
        setupAnswerButton(this.answer1,answer1);
        setupAnswerButton(this.answer2,answer2);
        setupAnswerButton(this.answer3,answer3);
        Font sideButtonFont = new Font("Arial",Font.BOLD,15);
        next.setFont(sideButtonFont);
        exit.setFont(sideButtonFont);
        previous.setFont(sideButtonFont);
        reset.setFont(sideButtonFont);
        eastPanel.add(next);
        eastPanel.add(exit);
        westPanel.add(previous);
        westPanel.add(reset);
        northPanel.add(this.definition);
        add(BorderLayout.NORTH, this.northPanel);
        add(BorderLayout.CENTER, this.centerPanel);
        add(BorderLayout.EAST, this.eastPanel);
        add(BorderLayout.WEST, this.westPanel);
        next.setBackground(optionsColor);
        reset.setBackground(optionsColor);
        previous.setBackground(optionsColor);
        exit.setBackground(optionsColor);
        this.answer0.setBackground(answerColor);
        this.answer1.setBackground(answerColor);
        this.answer2.setBackground(answerColor);
        this.answer3.setBackground(answerColor);
    }

    public void setupAnswerButton(JButton button, String text)
    {
        button.setText(text);
        button.setFont(new Font("Arial",Font.BOLD,25));
        centerPanel.add(button);
    }
    public void setAnswered(Boolean answer){
        answered = answer;
    }

    public Boolean getAnswered(){
        return answered;
    }

    public Object getNextButton(){
        return next;
    }

    public Object getPreviousButton(){
        return previous;
    }

    public Object getExitButton(){
        return exit;
    }

    public JButton getAnswer(int answer){
        switch(answer){
            case 0:
                return answer0;
            case 1:
                return answer1;
            case 2:
                return answer2;
            case 3:
                return answer3;
        }
        return answer0;
    }

    public JLabel getDefinition(){
        return definition;
    }

    public Object getResetButton(){
        return reset;
    }
    public void addActionsListenersToGameFrame(GameFrame frame){
        //Makes it so all the buttons are recognized by the main GameFrame
        next.addActionListener(frame);
        previous.addActionListener(frame);
        exit.addActionListener(frame);
        reset.addActionListener(frame);
        answer0.addActionListener(frame);
        answer1.addActionListener(frame);
        answer2.addActionListener(frame);
        answer3.addActionListener(frame);
    }

    public void resetColorAndScore(){
        //Called when the player presses the reset button
        answer0.setBackground(answerColor);
        answer1.setBackground(answerColor);
        answer2.setBackground(answerColor);
        answer3.setBackground(answerColor);
    }
}
