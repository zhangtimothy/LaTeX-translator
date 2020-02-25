
import java.util.Scanner;

public class TranslatorDriver {
    private static Scanner in;
    private static Translator translator;
    private static String input;
    private static final String HORIZONTAL_LINE = "------------------------------------------------------------------";
    private static boolean continueProgram;
    private static final Translator[] AVAILABLE_TRANSLATORS = new Translator[]{new LogicTranslator(), new TextTranslator()/*add more here*/};
    private static final String[] YES_OPTIONS = new String[]{"yes", "y", "yeah", "yea", "ya", "ye", "yep", "mhmm", "affirmative", "yes please", "kinda"};
    private static final String[] NO_OPTIONS = new String[]{"no", "n", "nah", "na", "nope", "naw", "no thanks", "negative", "not really"};
    private static final int NUM_ALLOWED_INVALID_RESPONSES = 4;

    public static void main(String[] args) {

        in = new Scanner(System.in);
        continueProgram = true;

        System.out.println("Welcome to the English to LaTeX Translator!");
        while (continueProgram) {
            System.out.println();
            askForTranslator();
            if (!continueProgram)
                break;
            askForFont();
            askForInputString();
            if (!continueProgram)
                break;
            displayTranslation();
            askForContinue();
        }
        endProgram();
    }

    //returns whether or not a given String[] contains a given String
    private static boolean stringArrayContains(String[] arr, String query) {
        for (String s : arr) {
            if (s.equalsIgnoreCase(query))
                return true;
        }
        return false;
    }

    private static void askForContinue() {
        int numInvalidResponses = 0;
        while (numInvalidResponses < NUM_ALLOWED_INVALID_RESPONSES) {
            if (numInvalidResponses > 0)
                System.out.println("\nPlease enter a valid response(e.g. \"Y\", \"N\").");
            System.out.println("Would you like to make another translation?");
            String userInput = in.nextLine();
            if (stringArrayContains(YES_OPTIONS, userInput)) {  //if user inputs yes
                continueProgram = true;
                return;
            }
            if (stringArrayContains(NO_OPTIONS, userInput)) {   //if user inputs no
                continueProgram = false;
                return;
            }
            //if user didn't input yes or no
            numInvalidResponses++;
        }
        //if user had too many invalid responses
        System.out.printf("\nEnding program after %d invalid responses when asked if another translation was desired.\n", numInvalidResponses);
        endProgram();
    }

    private static void askForTranslator() {
        int numInvalidResponses = 0;
        boolean translatorChosen = false;
        start:
        while (!translatorChosen && numInvalidResponses < NUM_ALLOWED_INVALID_RESPONSES) {
            System.out.println("Which translator would you like to use?");
            int translatorNumber = 1;
            for (Translator t : AVAILABLE_TRANSLATORS) {
                System.out.printf("\t%d --- %s\n", translatorNumber, t.getName());
                translatorNumber++;
            }
            System.out.println("Please enter its number:");
            if (!in.hasNextInt()) {
                in.nextLine();
                System.out.println("\nInput wasn't an integer.");
                numInvalidResponses++;
                continue start;
            }
            int userInput = in.nextInt();
            in.nextLine();  //clear end of line character after nextInt()
            if (userInput < 0 || userInput > AVAILABLE_TRANSLATORS.length) {
                System.out.println("\nThat number doesn't correspond to any Translator.");
                numInvalidResponses++;
                continue start;
            }
            translatorChosen = true;
            translator = AVAILABLE_TRANSLATORS[userInput - 1];    //-1 because displayed translators started at 1
        }
        if (!translatorChosen) {
            System.out.printf("\nEnding program after %d invalid responses when asked to choose a Translator.\n", numInvalidResponses);
            endProgram();
        }
        System.out.printf("\n%s was chosen.\n", translator.getName());    //give feedback to user!
    }

    //ends program and displays message
    private static void endProgram() {
        System.out.println("Thanks for using English to LaTeX Translator!");
        System.exit(0);
    }

    private static void askForFont() {
        int numInvalidResponses = 0;
        boolean fontChosen = false;
        start:
        while (!fontChosen && numInvalidResponses < NUM_ALLOWED_INVALID_RESPONSES) {
            System.out.println("Which font would you like to use?");
            int fontNumber = 1;
            Font[] allowedFonts = translator.getAllowedFonts();
            for (Font f : allowedFonts) {
                System.out.printf("\t%d --- %s\n", fontNumber, f.getName());
                fontNumber++;
            }
            System.out.println("Please enter its number:");
            if (!in.hasNextInt()) { //if user didn't input an int
                in.nextLine();
                System.out.println("\nInput wasn't an integer.");
                numInvalidResponses++;
                continue start;
            }
            int userInput = in.nextInt();
            in.nextLine();  //clear end of line character after nextInt()
            if (userInput < 0 || userInput > allowedFonts.length) {    //if user input wasn't a valid number
                System.out.println("\nThat number doesn't correspond to any Font.");
                numInvalidResponses++;
                continue start;
            }
            //if user input was a valid int
            fontChosen = true;
            translator.setFont(allowedFonts[userInput - 1]);  //-1 because displayed fonts started at 1
        }
        //if too many invalid responses and no font chosen
        if (!fontChosen) {
            System.out.printf("\nEnding program after %d invalid responses when asked to choose a Font.\n", numInvalidResponses);
            endProgram();
        }
        System.out.printf("\n%s was chosen.\n", translator.getFontName());    //give feedback to user!
    }

    private static void askForInputString() {
        System.out.println("Please enter an input to be translated:");
        input = in.nextLine();
        System.out.println();
    }

    private static void displayTranslation() {
        String output = translator.translate(input);
        System.out.println("Translation Completed!");
        System.out.println(HORIZONTAL_LINE);
        System.out.printf(" %s\n Font:  %s\n\n\t   Input:\n\t\t\t%s\n\n\t   Output:\n\t\t\t%s\n",
                translator.getName(), translator.getFontName(), input, output);
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
    }
}
