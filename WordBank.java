import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;


public class WordBank {
    final String wordGrab = "40";
    private String [][] definitionsAndAnswers;
    private String [][] questions;
    public WordBank() throws IOException {
            WebClient webClient = new WebClient(BrowserVersion.CHROME); //Create GUIless browser that can fetch
        //necessary words
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); //These are necessary, as the program
        //crashes without them
            webClient.setJavaScriptTimeout(3000);
            HtmlPage page = webClient.getPage("https://wordcounter.net/random-word-generator");//Website that has
        //the randomly generated words
            DomElement wordNumberElement = page.getElementById("random_words_count");
            HtmlInput wordNumber = (HtmlInput) wordNumberElement;
            wordNumber.setValueAttribute(wordGrab); //Fetch variable box and set the input of the variable
        //box. In this case, we're setting the number of random words obtained to 40
            DomElement button = page.getElementById("random-words");
            button.click();//After we set the amount of random words requested to 40, there is a button to confirm.
        //page.getElementById() grabs the button, button.click() clicks the button.
            Document doc = Jsoup.parse(page.asXml()); //Page is converted into a format that will let us easily
        //grab the necessary words
            definitionsAndAnswers = new String[10][2]; //definitionsAndAnswers are reserved for the correct answers
        //to each of the 10 questions. [x][0] represents the word, [x][1] represents the definition
            questions = new String[10][4]; //questions is an array that represents the possible choices for each
        //question.
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {
                    String word = doc.getElementsByClass("random_word").get(i*4+j).text();
                    questions[i][j] = word; //Set the answer choices
                    if (j == 0) {//The first word inserted in every question is the correct answer, and it's definition
                        //is searched with setWordDefinitions();
                            definitionsAndAnswers[i][0] = word;
                            definitionsAndAnswers[i][1] = setWordDefinitions(i);
                    }
                }
            }
            scrambleAnswers();//The first word inserted for every question is always the answer, therefore the order
        //must be shuffled
            webClient.close();//webClient is no longer needed and is taking up space
        }


    public static int randomBetween(int minInclusive, int maxExclusive){ //Used to shuffle the answers
        return (int)(minInclusive+Math.random()*(maxExclusive-minInclusive));
    }

    public String getAnswer(int index){
        return definitionsAndAnswers[index][0];
    }

    public String getWord(int question, int answer){
        return questions[question][answer];
    }

    public String getDefinition(int index){
        return definitionsAndAnswers[index][1];
    }

    public String setWordDefinitions(int index) throws java.io.IOException {
            try {
                Document doc = Jsoup.connect("https://www.dictionary.com/browse/" +
                        definitionsAndAnswers[index][0]).get(); //Connects to dictionary.com and goes to the definition
                //page of the chosen word
                Elements definitionElement = doc.getElementsByClass("one-click-content css-1p89gle e1q3nk1v4");
                //Grabs the section of code that holds the definition, determined by checking the element
                String definition = definitionElement.get(0).text();
                definition = definition.substring(0,1).toUpperCase()+definition.substring(1);
                if (definition.contains(":")) { //Many definitions have examples that are signified by a colon.
                    //These examples often include the answer within them, so they are removed
                    definition = definition.substring(0, definition.lastIndexOf(":")) +".";
                }
                return definition;
            }
            catch(HttpStatusException ex){//Rarely, the word generator will choose a word that dictionary.com has
                //not recognized. In these scenarios, that word is replaced will failsafe to avoid an error.
                definitionsAndAnswers[index][0] = "failsafe";
                questions[index][0] = "failsafe";
                return "Electronics. pertaining to or noting a mechanism built into a system, as in an early warning " +
                        "system or a nuclear reactor, for insuring safety should the system fail" +
                        " to operate properly.";
            }
    }

    public void scrambleAnswers(){ //Randomizes the word choices
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 4; j++){
                int random = randomBetween(j,4);
                String placeholder = questions[i][j];
                questions[i][j] = questions[i][random];
                questions[i][random] = placeholder;
            }
        }
    }
    }
