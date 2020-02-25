public class TextTranslator implements Translator{
    private static final String TRANSLATOR_NAME = "Text Translator";
    private static final Font[] ALLOWED_FONTS = new Font[]{Font.ROMAN, Font.SANS_SERIF, Font.TELETYPE, Font.SMALL_CAPITALS};


    private Font font;

    @Override
    public String getName() {
        return TRANSLATOR_NAME;
    }

    @Override
    public Font[] getAllowedFonts() {
        return ALLOWED_FONTS;
    }

    @Override
    public void setFont(Font f) {
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

    @Override
    public String translate(String input) {
        StringBuilder output = new StringBuilder();
        output.append(font.getStartCommand());
        output.append(input);
        output.append((font.getStopCommand()));
        return output.toString();
    }
}
