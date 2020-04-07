import java.io.Serializable;
import java.util.Objects;

public class CustomObject implements Serializable {

    private int intField;
    private String stringField;

    public CustomObject(int intField, String stringField) {
        this.intField = intField;
        this.stringField = stringField;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomObject that = (CustomObject) o;
        return intField == that.intField &&
                Objects.equals(stringField, that.stringField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intField, stringField);
    }
}
