package flarestar.mirror.mock.element;

/**
 * TODO
 */
public class Name implements javax.lang.model.element.Name {

    private String name;

    public Name(String name) {
        this.name = name;
    }

    @Override
    public boolean contentEquals(CharSequence charSequence) {
        return name.equals(charSequence);
    }

    @Override
    public int length() {
        return name.length();
    }

    @Override
    public char charAt(int i) {
        return name.charAt(i);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return name.subSequence(start, end);
    }

    @Override
    public String toString() {
        return name == null ? "" : name;
    }
}
