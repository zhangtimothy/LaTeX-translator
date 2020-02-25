//stores start and stop commands for each font
public enum Font {
    DEFAULT("Default","", ""),
    MATH_INLINE("Math: Inline","$", "$"),
    MATH_DISPLAYED("Math: Displayed","$$", "$$"),
    ROMAN("Roman", "\\textrm{", "}"),
    SANS_SERIF("Sans Serif", "\\textsf{", "}"),
    TELETYPE("Teletype", "\\texttt{", "}"),
    SMALL_CAPITALS("Small Capitals", "\\textsc{", "}");



    private final String name;
    private final String startCommand;
    private final String stopCommand;


    Font(String name, String startCommand, String stopCommand) {
        this.name = name;
        this.startCommand = startCommand;
        this.stopCommand = stopCommand;
    }

    public String getName(){
        return name;
    }

    protected String getStartCommand() {
        return startCommand;
    }

    protected String getStopCommand() {
        return stopCommand;
    }
}


