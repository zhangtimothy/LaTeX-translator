public interface Translator {

    public abstract String getName();

    public Font[] getAllowedFonts();

    public void setFont(Font f);

    public String getFontName();

    public Font getFont();

    public abstract String translate(String input);

}
