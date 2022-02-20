package oop.ex6.Operations;

/**
 * the class of variable
 */
public class Variables {
    /**
     * name of the variable
     */
    public String name;
    /**
     * the type of the variable
     */
    public String type;
    /**
     * the value of the variable
     */
    public String value;
    /**
     * if the variable is final
     */
    public boolean isFinal;

    /**
     * constructor
     *
     * @param type    the type of the variable
     * @param name    name of the variable
     * @param value   the value of the variable
     * @param isFinal if the variable is final
     */
    Variables(String type, String name, String value, boolean isFinal) {
        this.isFinal = isFinal;
        this.name = name;
        this.type = type;
        this.value = value;

    }
}
