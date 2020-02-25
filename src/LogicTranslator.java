import java.util.HashMap;
import java.util.List;

/*
Translates logical statements from English into LaTeX commands
 */
public class LogicTranslator implements Translator {

    private static final String TRANSLATOR_NAME = "Logic Translator";
    private static final Font[] ALLOWED_FONTS = new Font[]{Font.MATH_INLINE, Font.MATH_DISPLAYED};
    private static final String DELIMITERS = "[ ]+";
    private static HashMap<String, String> logicCommands;
    private static HashMap<String, String> logicRegexCommands;
    private static HashMap<Character, String> reservedCharacters;
    private Font font = Font.DEFAULT;

    public LogicTranslator() {
        initializeCommands();
    }

    public LogicTranslator(Font f){
        initializeCommands();
        font = f;
    }

    public void setFont(Font f){
        font = f;
    }

    @Override
    public String getFontName() {
        return font.getName();
    }

    @Override
    public Font getFont() {
        return font;
    }

    private static void initializeCommands(){
        initializeLogicCommands();
        initializeLogicRegexCommands();
        initializeReservedCharacters();
    }
    //returns the translation of the given line, WITH the font start and end commands added
    public String translate(String inputLine) {
        StringBuilder output = new StringBuilder();
        output.append(getFont().getStartCommand());   //add font start command

        output.append(translateString(inputLine));

        output.append(font.getStopCommand());  //add font stop command
        return output.toString();
    }

    //returns the translation of the given string
    private String translateString(String inputLine){
        StringBuilder output = new StringBuilder();
        InputHeap inputHeap = new InputHeap(prepareInput(inputLine), DELIMITERS);
        List<String> currentInputHeapList;
        nextElement:
        //nextElement label makes the continue statement jump here
        while (inputHeap.hasMoreElements()) {

            currentInputHeapList = inputHeap.toList();
            for (String query : currentInputHeapList) {
                String input = query.toLowerCase();
                if (logicCommands.containsKey(input)) {   //check for commands
                    output.append(logicCommands.get(input) + " ");
                    inputHeap.removeFromEach(input.length() + 1);//+1 for the space afterwards
                    continue nextElement;
                }
                String regexCommand = getRegexCommand(input); //will be null if input doesn't match any regex commands
                if (regexCommand != null) {
                    String temp = input.replaceFirst(regexCommand, logicRegexCommands.get(regexCommand));
                    temp = translateString(temp);
                    output.append(temp + " ");
                    inputHeap.removeFromEach(input.length() + 1);
                    continue nextElement;
                }
            }
            //if none are a command, just add the last element but change reserved characters
            String currentToken = inputHeap.getLastElement();
            output.append(translateReservedChars(currentToken) + " ");
            inputHeap.removeFromEach(currentToken.length() + 1);
        }
        return output.toString();
    }
    //returns the key regex command in logicRegexCommands that matches the given input, or null if none match
    private static String getRegexCommand(String input) {
        for (String pattern : logicRegexCommands.keySet()) {
            if (input.matches(pattern))
                return pattern;
        }
        return null; //if input matches no patterns
    }

    public Font[] getAllowedFonts() {
        return ALLOWED_FONTS;
    }

    //takes in one string and replaces reserved characters with the command in LaTeX that displays that character
    private static String translateReservedChars(String s) {
        StringBuilder output = new StringBuilder();
        char currentChar;
        for (int i = 0; i < s.length(); i++) {
            currentChar = s.charAt(i);
            if (reservedCharacters.containsKey(currentChar)) {
                output.append(reservedCharacters.get(currentChar));
            } else {
                output.append(currentChar);
            }
        }
        return output.toString();
    }

    /*
    prepares input by adding spaces before and after every parentheses
    to avoid stuff like: "x or(y and z)" the "or" wouldn't get translated
    the extra spaces don't matter in LaTeX math mode
    */
    private static String prepareInput(String in) {
        return in.replaceAll("([(]|[)]|[,]|[=])", " $1 ");
    }

    public String getName() {
        return TRANSLATOR_NAME;
    }

    private static void initializeLogicCommands() {
        logicCommands = new HashMap<>();
        logicCommands.put("and", "\\land");
        logicCommands.put("or", "\\lor");
        logicCommands.put("exists", "\\exists");
        logicCommands.put("there exists", "\\exists");
        logicCommands.put("there exist", "\\exists");
        logicCommands.put("for all", "\\forall");
        logicCommands.put("for every", "\\forall");
        logicCommands.put("not", "\\neg");
        logicCommands.put("in", "\\in");
        logicCommands.put("in the set of", "\\in");
        logicCommands.put("in set", "\\in");
        logicCommands.put("reals", "\\textbf{R}");
        logicCommands.put("all reals", "\\textbf{R}");
        logicCommands.put("is real", "\\in \\textbf{R}");
        logicCommands.put("natural numbers", "\\textbf{N}");
        logicCommands.put("is a natural number", "\\in \\textbf{N}");
        logicCommands.put("is natural", "\\in \\textbf{N}");
        logicCommands.put("rational numbers", "\\textbf{Q}");
        logicCommands.put("rationals", "\\textbf{Q}");
        logicCommands.put("is rational", "\\in \\textbf{Q}");
        logicCommands.put("integers", "\\textbf{Z}");
        logicCommands.put("is an integer", "\\in \\textbf{Z}");
        logicCommands.put("implies", "\\Rightarrow");
        logicCommands.put("less than", "<");
        logicCommands.put("smaller than", "<");
        logicCommands.put("greater than", ">");
        logicCommands.put("more than", ">");
        logicCommands.put("less than or equal to", "\\leq");
        logicCommands.put("greater than or equal to", "\\geq");
        logicCommands.put("< =", "\\leq");
        logicCommands.put("> =", "\\geq");
        logicCommands.put("not equals", "\\neq");
        logicCommands.put("not equal to", "\\neq");
        logicCommands.put("is not equal to", "\\neq");
        logicCommands.put("such that", "\\textrm{ s.t. }");
        logicCommands.put("equals", "=");
        logicCommands.put("some", "");  //is this right?
        logicCommands.put("is", "");
        logicCommands.put("therefore", "\\therefore");
        logicCommands.put("...", "\\dots");
        logicCommands.put("*", "\\cdot");
        logicCommands.put("times", "\\cdot");
        logicCommands.put("multiplied by", "\\cdot");


    }

    private static void initializeLogicRegexCommands() {
        logicRegexCommands = new HashMap<>();
        //this processes fractions with variables. "x/y" becomes "\frac{x}{y}", "ab/cd" becomes "\frac{ab}{cd}", "a b/cd e" becomes "a \frac{b}{cd} e"
        //the "\\\\" prints ONE backslash, we need 4 because "\" is an escape character in both regex and java, so we have to escape both
        logicRegexCommands.put("^([a-z0-9]+)[\\s]*[/][\\s]*([a-z0-9]+)$", "\\\\frac{$1}{$2}");
        //fractions with neighboring parentheses indicating the numerator and denominator
        logicRegexCommands.put("^[\\s]*[(](.+)[)][\\s]*[/][\\s]*[(](.+)[)][\\s]*$", "\\\\frac{$1}{$2}");
        logicRegexCommands.put("^[\\s]*[(](.+)[)][\\s]*[/][\\s]*([a-z0-9]+)[\\s]*$", "\\\\frac{$1}{$2}");
        logicRegexCommands.put("^([a-z0-9]+)[\\s]*[/][\\s]*[(](.+)[)][\\s]*$", "\\\\frac{$1}{$2}");
    }

    private static void initializeReservedCharacters() {
        reservedCharacters = new HashMap<>();
        reservedCharacters.put('#', "\\#");
        reservedCharacters.put('$', "\\$");
        reservedCharacters.put('%', "\\%");
        reservedCharacters.put('&', "\\&");
        reservedCharacters.put('~', "\\~");
    }


}
