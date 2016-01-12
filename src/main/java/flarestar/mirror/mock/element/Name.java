package flarestar.mirror.mock.element;

/**
 * TODO
 */
public class Name implements javax.lang.model.element.Name {

    private String name;

    public Name(String name) {
        this.name = name;
    }

    public boolean contentEquals(CharSequence charSequence) {
        return name.equals(charSequence);
    }

    public int length() {
        return name.length();
    }

    public char charAt(int i) {
        return name.charAt(i);
    }

    public CharSequence subSequence(int start, int end) {
        return name.subSequence(start, end);
    }

    public String toString() {
        return name == null ? "" : name;
    }
}
