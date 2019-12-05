import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//Carlos Garcia
//Final Project - Dictoinary Game
//A project which utilizes webscraping to create randomly generated questions consisting of a definition and
//4 answer choices
public class GameFrame extends JFrame implements ActionListener {
    private int questionIndex = 0;
    private int score = 0;
    private CardLayout cardLayout;
    private WordBank wordBank;
    private MenuPanel menu;
    private GamePanel [] gamePanel =  new GamePanel[10];
    private MenuPanel scoreSheet;
    private Color correctAnswerColor = new Color(0, 200, 0);
    private Color wrongAnswerColor = new Color(200,0,0);
    public GameFrame() throws java.io.IOException{
        super("Dictionary Game");
        cardLayout = new CardLayout();
        setSize(800,500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        wordBank = new WordBank();
        setLayout(cardLayout);
        menu = new MenuPanel("Dictionary Game", "Start");
        add("menu",menu);
        scoreSheet = new MenuPanel("Score: "+score,"Reset");
        setupQuestions();
        add("score", scoreSheet);
        menu.addActionListeners(this);
        scoreSheet.addActionListeners(this);
    }

    public void setupQuestions(){
        for(int i = 0; i < 10; i++){
            Font font = new Font("Arial",Font.BOLD,15);
            String definition = wordBank.getDefinition(i);//Grabs the words and definitions
            // from wordBank in order to create the answer choices
            String answer0 = wordBank.getWord(i,0);
            String answer1 = wordBank.getWord(i,1);
            String answer2 = wordBank.getWord(i,2);
            String answer3 = wordBank.getWord(i,3);
            gamePanel[i] = new GamePanel(i,definition,answer0,answer1,answer2,answer3);
            add((""+i),gamePanel[i]);//The id for cardLayout is the index of the question in string format
            gamePanel[i].addActionsListenersToGameFrame(this);//Adds all the buttons from the panel to
            //main GameFrame's actionListener
        }
        JButton submit = (JButton)gamePanel[9].getNextButton(); //Last question has the word submit instead of next
        submit.setText("Submit");
    }

    public void resetQuestions() throws IOException{
        wordBank = new WordBank();
        for(int i = 0; i < 10; i++){
            gamePanel[i].getAnswer(0).setText(wordBank.getWord(i,0));
            gamePanel[i].getAnswer(1).setText(wordBank.getWord(i,1));
            gamePanel[i].getAnswer(2).setText(wordBank.getWord(i,2));
            gamePanel[i].getAnswer(3).setText(wordBank.getWord(i,3));
            gamePanel[i].getDefinition().setText("<html>" +(1+i) + ". " + wordBank.getDefinition(i)+"</html>");
        }
        score = 0;
    }

    public void highlightCorrectAnswer(){
        for(int i = 0; i < 4; i++){
            if(gamePanel[questionIndex].getAnswer(i).getText().equals(wordBank.getAnswer(questionIndex)))
                gamePanel[questionIndex].getAnswer(i).setBackground(correctAnswerColor);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(menu.getButton()==e.getSource()){
            cardLayout.next(getContentPane());
        }
        else if(gamePanel[questionIndex].getResetButton()==e.getSource() || scoreSheet.getButton()==e.getSource()){
            try {
                resetQuestions();
                for(int i = 0; i < 10; i++){
                    gamePanel[i].resetColorAndScore();
                    gamePanel[i].setAnswered(false);
                    cardLayout.show(getContentPane(),"menu");
                    questionIndex = 0;
                }
            }catch(java.io.IOException ex){}
        }
        else if(gamePanel[questionIndex].getNextButton()==e.getSource()) {
                if(questionIndex<9) {
                    questionIndex++;
                }
                cardLayout.next(getContentPane());
        }
        else if(gamePanel[questionIndex].getPreviousButton()==e.getSource()){
                if(questionIndex>0) {
                    questionIndex--;
                }
                cardLayout.previous(getContentPane());
        }
        else if(gamePanel[questionIndex].getExitButton()==e.getSource()){
            super.dispose();
        }
        else if (!gamePanel[questionIndex].getAnswered()) {
            JButton source = (JButton) e.getSource();
            gamePanel[questionIndex].setAnswered(true);
            if (source.getText().equals(wordBank.getAnswer(questionIndex))) {
                System.out.println("right!");
                score++;
            } else {
                System.out.println("wrong!");
                source.setBackground(wrongAnswerColor);
            }
            highlightCorrectAnswer();
        }
        scoreSheet.getLabel().setText("Score: "+score+"/10");
    }
}
