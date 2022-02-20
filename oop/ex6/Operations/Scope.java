package oop.ex6.Operations;

import java.util.ArrayList;

/**
 * the class of the scope that is contain the variables in the scope
 * and other methods that search in the scope
 */
public class Scope {
    public static final int ZERO = 0;
    /**
     * the previous scope of this scope
     */
    Scope prevScope = null;
    /**
     * list of the variables in this scope
     */
    public ArrayList<Variables> variables;

    /**
     * constructor of the class
     */
    Scope() {
        this.variables = new ArrayList<>();
    }

    /**
     * this function check if this variable is exist
     * in this scope or up to this scope
     *
     * @param name the name of the variable
     * @return true if exist false else
     */
    public boolean exists(String name) {
        Scope curScope = this;
        while (curScope != null) {
            for (int i = ZERO; i < curScope.variables.size(); i++) {
                if (curScope.variables.get(i).name.equals(name)) {
                    return true;
                }
            }
            curScope = curScope.prevScope;
        }
        return false;
    }

    /**
     * this function check if this variable is exist
     * in this scope
     *
     * @param name the name of the variable
     * @return true if exist false else
     */
    public boolean contain(String name) {
        for (int i = ZERO; i < variables.size(); i++) {
            if (variables.get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * this function check if this variable is exist
     * in this scope or up to this scope and not final
     *
     * @param name the name of the variable
     * @return true if exist false else
     */
    public boolean existsNotFinal(String name) {
        Scope curScope = this;
        while (curScope != null) {
            for (int i = ZERO; i < curScope.variables.size(); i++) {
                if (curScope.variables.get(i).name.equals(name) && !curScope.variables.get(i).isFinal) {
                    return true;
                }
            }
            curScope = curScope.prevScope;
        }
        return false;
    }

    /**
     * find the variable if exist in this scope and up then return
     * it object
     *
     * @param name the name of the variable
     * @return the object of the variable
     */
    public Variables findVar(String name) {
        Scope curScope = this;
        while (curScope != null) {
            for (int i = ZERO; i < curScope.variables.size(); i++) {
                if (curScope.variables.get(i).name.equals(name)) {
                    return curScope.variables.get(i);
                }
            }
            curScope = curScope.prevScope;
        }
        return null;
    }

    /**
     * adding new variable to the scope
     *
     * @param type    variable type
     * @param name    variable name
     * @param value   variable value
     * @param isFinal if it final
     */
    public void addNew(String type, String name, String value, boolean isFinal) {
        Variables variable = new Variables(type, name, value, isFinal);
        variables.add(variable);
    }
}
