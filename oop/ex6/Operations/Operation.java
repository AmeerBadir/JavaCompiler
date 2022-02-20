package oop.ex6.Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class is response of checking the validity
 * contain all the functions that check each case that
 * may appear in the file
 */
abstract public class Operation {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;

    /**
     * array list that contain all the objects of scopes
     * the file contain(in the Scope class there is documentation)
     * the scope object contain all the information about the current scope
     */
    public static ArrayList<Scope> scopes = new ArrayList<>();
    /**
     * this list contain all the methods that has been called but yet
     * defined in the file
     */
    public static ArrayList<Matcher> methodsCalledNotFound = new ArrayList<>();
    /**
     * this list contain all the methods that not called yet
     * but defined
     */
    public static ArrayList<String> methodNotCalled = new ArrayList<>();
    /**
     * hash map that contain all the methods defined by it name
     * and the array list of its parameters type
     */
    public static Map<String, ArrayList<String>> allMethodsGlobal = new HashMap<>();
    /**
     * list that contain all the methods in the file by it name
     */
    public static ArrayList<String> allMethods = new ArrayList<>();
    /**
     * this list contain all the variable that appear in the
     * condition field of the (if or while) that have not
     * been defined yet
     */
    public static Map<Integer, String> variablesForIfWhile = new HashMap<>();
    /**
     * this two patterns were used to check if the value of parameter
     * is a variable , the second pattern used to check if the
     * parameters in the complex case of defining multiple variables
     * in the same line is legal
     */
    private static Pattern pattern1 = Pattern.compile(Spliting.VARDICNAME);
    private static Pattern pattern2 = Pattern.compile(Spliting.COMPLEX);
    public static final String INTEGER ="int";
    public static final String WHILE ="while";
    public static final String IF ="if";
    public static final String DOUBL ="double";
    public static final String BOOLEN ="boolean";
    public static final String CHR ="char";
    public static final String STR ="String";
    public static final String FINAL ="final";
    private static final String typeValueError = "THERE IS NO MATCH BETWEEN TYPE AND VALUE";
    private static final String duplicateDefineError = "THERE IS DUPLICATION IN DEFINING THE SAME VARIABLE";
    private static final String finalDuplicateDefineError = "THERE IS DUPLICATION IN DEFINING FOR FINAL VARIABLE";
    private static final String assignError = "YOU TRY TO ASSIGN VALUE TO AN DEFINE VARIABLE";
    private static final String ifWhileConditionError = "THERE IS IS ERROR IN THE CONDITION OF IF OR WHILE";
    private static final String duplicateVariablesMethodError = "THERE IS DUPLICATE VARIABLES IN THE METHOD PARAMETERS";
    private static final String duplicateMethodError = "THERE IS DUPLICATE METHOD DEFINED";
    private static final String callingMethodError = "THERE IS ERROR IN CALLING METHOD";
    private static final String moreThanParamError = "THERE IS ERROR IN THE LINE THAT DEFINE MORE THAN PARAMETER " +
            "IN THE SAME LINE";


    /**
     * this function check if given type and value are suitable to
     * each other
     *
     * @param type  type of variable
     * @param value the value of it
     * @throws ValidityError in case of error that the program will return 1
     */
    private static void validTypeValue(String type, String value) throws ValidityError {
        boolean match = false;
        switch (type) {
            case INTEGER:
                match = Pattern.compile(Spliting.INT).matcher(value).matches();
                break;
            case DOUBL:
                match = Pattern.compile(Spliting.DOBULE).matcher(value).matches();
                break;
            case CHR:
                match = Pattern.compile(Spliting.CHAR).matcher(value).matches();
                break;
            case STR:
                match = Pattern.compile(Spliting.STRING).matcher(value).matches();
                break;
            case BOOLEN:
                match = Pattern.compile(Spliting.BOOLEAN).matcher(value).matches();
                break;
            default:
                throw new ValidityError(typeValueError);
        }
        if (!match) {
            throw new ValidityError(typeValueError);
        }
    }

    /**
     * this function is responsible of of initializing new variables
     * without value
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the variable initialized
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void initializeVar(Matcher matcher, int scopeDepth) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }
        String type = matcher.group(ONE).trim();
        String name = matcher.group(TWO).trim();
        if (scopes.get(scopeDepth).contain(name)) {
            throw new ValidityError(duplicateDefineError);
        }
        scopes.get(scopeDepth).addNew(type, name, null, false);
    }

    /**
     * this function responsible of defining variables that is final
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the variable initialized
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void initializeFinal(Matcher matcher, int scopeDepth) throws ValidityError {

        if (!matcher.matches()) {
            throw new ValidityError();
        }
        String type = matcher.group(TWO).trim();
        String name = matcher.group(3).trim();
        String value = matcher.group(6).trim();
        addOperator(type, name, value, scopeDepth, true);
    }

    /**
     * this function responsible of adding new variable to the scope
     * in case of defining new variable or assign value to exist variable
     *
     * @param type       the type of the var
     * @param name       the name of it
     * @param value      its value
     * @param scopeDepth the scope depth where the variable been mentioned
     * @param isFinal    if it final
     * @throws ValidityError in case of error that the program will return 1
     */
    private static void addOperator(String type, String name, String value, int scopeDepth, boolean isFinal)
            throws ValidityError {
        if (isFinal) {
            if (scopes.get(scopeDepth).contain(name)) {
                throw new ValidityError(duplicateDefineError);
            }
            try {
                validTypeValue(type, value);

            } catch (ValidityError e) {
                if (!scopes.get(scopeDepth).exists(value)) {
                    throw new ValidityError(duplicateDefineError);
                }
                if (!scopes.get(scopeDepth).findVar(value).type.equals(type)) {
                    throw new ValidityError(finalDuplicateDefineError);
                }
            }
            scopes.get(scopeDepth).addNew(type, name, value, true);
            return;
        }
        Variables variable = scopes.get(scopeDepth).findVar(name);
        validTypeValue(variable.type, value);
        variable.value = value;
    }

    /**
     * this function called in case of assigning new value
     * to exist variable
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the variable initialized
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void assignVar(Matcher matcher, int scopeDepth) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }
        String name = matcher.group(ONE).trim();
        String value = matcher.group(4).trim();
        if (scopes.get(scopeDepth).existsNotFinal(name)) {
            addOperator(null, name, value, scopeDepth, false);
            return;
        }
        throw new ValidityError(assignError);
    }

    /**
     * this function called when decelerating new variable that contain type name value
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the variable initialized
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void decelerateVar(Matcher matcher, int scopeDepth) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }
        String type = matcher.group(ONE).trim();
        String name = matcher.group(TWO).trim();
        String value = matcher.group(5).trim();
        if (scopes.get(scopes.size() - ONE).contain(name)) {

            throw new ValidityError(duplicateDefineError);
        }
        if (scopes.get(scopeDepth).exists(value) && scopes.get(scopeDepth).findVar(value).value != null) {
            if (scopes.get(scopeDepth).findVar(value).type.equals(type)) {
                return;
            }
            if (type.equals(BOOLEN) && (scopes.get(scopeDepth).findVar(value).type.equals(INTEGER) ||
                    scopes.get(scopeDepth).findVar(value).type.equals(DOUBL))) {
                return;
            }
            if (type.equals(DOUBL) && scopes.get(scopeDepth).findVar(value).type.equals(INTEGER)) {
                return;
            }
        }
        validTypeValue(type, value);
        scopes.get(scopeDepth).addNew(type.trim(), name.trim(), value.trim(), false);
    }

    /**
     * this function check the condition field of the if while
     * if it is valid and the condition contain valid parameters
     *
     * @param line       the line that if while statement appear in
     * @param scopeDepth the scope depth where the variable initialized
     * @throws ValidityError in case of error that the program will return 1
     */
    private static void ifWhileValidity(String line, int scopeDepth) throws ValidityError {
        String[] tokens = line.split("\\&\\&|\\|\\|");
        for (int i = ZERO; i < tokens.length; i++) {
            if (Pattern.compile(Spliting.BOOLEAN).matcher(tokens[i].trim()).matches() ||
                    Pattern.compile(Spliting.INT).matcher(tokens[i].trim()).matches() ||
                    Pattern.compile(Spliting.DOBULE).matcher(tokens[i].trim()).matches()) {
                continue;
            } else if (Pattern.compile(Spliting.VARDICNAME).matcher(tokens[i]).matches()) {
                if (scopes.get(scopeDepth).exists(tokens[i].trim()) &&
                        (Spliting.ifcondition.matcher(scopes.get(scopeDepth).
                                findVar(tokens[i].trim()).type).matches()) && scopes.get(scopeDepth).
                        findVar(tokens[i].trim()).value != null) {
                    continue;
                } else {
                    variablesForIfWhile.put(scopeDepth, tokens[i].trim());
                }
            } else {
                throw new ValidityError(ifWhileConditionError);
            }
        }
    }

    /**
     * this function is called at the end of the file
     * that check if a variables that appear in the condition field
     * of if while statement that was not defined yet if it defined after
     *
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void checkVariablesForIfWhile() throws ValidityError {
        for (int var : variablesForIfWhile.keySet()) {
            if (scopes.get(var - ONE).contain(variablesForIfWhile.get(var)) ||
                    scopes.get(var).contain(variablesForIfWhile.get(var))) {
                throw new ValidityError(ifWhileConditionError);
            }
            if (scopes.get(var).exists(variablesForIfWhile.get(var)) &&
                    scopes.get(var).findVar(variablesForIfWhile.get(var)).value != null &&
                    methodNotCalled.contains(allMethods.get(allMethods.size() - ONE))) {
                return;
            }
            if (!scopes.get(var).exists(variablesForIfWhile.get(var))) {
                throw new ValidityError(ifWhileConditionError);
            }
            if (scopes.get(var).exists(variablesForIfWhile.get(var)) &&
                    scopes.get(var).findVar(variablesForIfWhile.get(var)).value == null) {
                throw new ValidityError(ifWhileConditionError);
            }
        }
    }

    /**
     * this function called on the line that match if while
     * this function call the above functions to check the validity
     * of the line
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the if while statement appear in
     * @param line       the line that if while statement appear in
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void ifWhileStatement(Matcher matcher, int scopeDepth, String line) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }

        ifWhileValidity(matcher.group(2), scopeDepth);
    }

    /**
     * this function split the arguments of a method by comma
     * and save a list of it parameters types
     *
     * @param tokens     what appear between the brackets
     * @param scopeDepth the scope depth where the method  defined
     * @param name       the method name
     * @throws ValidityError in case of error that the program will return 1
     */
    private static void splitMethodParameters(String[] tokens, int scopeDepth, String name)
            throws ValidityError {
        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (int i = ZERO; i < tokens.length; i++) {
            String[] token = tokens[i].trim().split(" ");
            if (token.length <= ONE) {
                return;
            }
            if (token[0] == null) {
                throw new ValidityError();
            } else if (token[ZERO].equals(FINAL)) {
                types.add(token[ONE]);
                names.add(token[TWO]);
                scopes.get(scopeDepth).addNew(token[ONE], token[2], null, true);
            } else {
                types.add(token[ZERO]);
                names.add(token[ONE]);
                scopes.get(scopeDepth).addNew(token[ZERO], token[ONE], "", false);
            }
        }
        int counter = ZERO; // check if there is more than parameter the share the same name
        for (int i = ZERO; i < names.size(); i++) {
            for (int j = ZERO; j < names.size(); j++) {
                if (names.get(i).equals(names.get(j))) {
                    counter++;
                }
            }
            if (counter > ONE) {
                throw new ValidityError(duplicateVariablesMethodError);
            }
            counter = ZERO;
        }
        allMethodsGlobal.put(name, types);
    }

    /**
     * this function called when defining new method
     * this function call the above functions
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the method  defined
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void defineMethod(Matcher matcher, int scopeDepth) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }
        String name = matcher.group(TWO);
        if (allMethodsGlobal.containsKey(name)) {
            throw new ValidityError(duplicateMethodError);
        }
        String parameters = matcher.group(3);
        String[] tokens = parameters.split(",");
        splitMethodParameters(tokens, scopeDepth, name);
        methodNotCalled.add(name.trim());
        allMethods.add(name.trim());
    }

    /**
     * this function called when call to a function appear in the file
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the method  defined
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void callMethod(Matcher matcher, int scopeDepth) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }
        String name = matcher.group(ONE);
        String parameters = matcher.group(TWO);

        if (name.trim().equals(IF) || name.trim().equals(WHILE)) {
            throw new ValidityError(callingMethodError);
        }
        if (!allMethodsGlobal.containsKey(name)) {
            methodsCalledNotFound.add(matcher);
            return;
        }
        String[] tokens = parameters.split(",");
        ArrayList<String> methodTypes = allMethodsGlobal.get(name);
        if (tokens.length != methodTypes.size()) {
            throw new ValidityError(callingMethodError);
        }
        for (int i = ZERO; i < tokens.length; i++) {
            if (Pattern.compile(Spliting.VARDICNAME).matcher(tokens[i].trim()).matches()) {
                if (scopes.get(scopeDepth).exists(tokens[i].trim())) {
                    if (scopes.get(scopeDepth).findVar(tokens[i].trim()).value != null) {
                        validTypeValue(methodTypes.get(i),
                                scopes.get(scopeDepth).findVar(tokens[i].trim()).value.trim());
                    } else {
                        throw new ValidityError(callingMethodError);
                    }
                }
            } else {
                validTypeValue(methodTypes.get(i), tokens[i].trim());
            }
        }

        methodNotCalled.remove(name.trim());
    }

    /**
     * this function called in case of defining more that variable
     * in the same line
     * this function check structure of the line and split all the variables
     * and save them in the scope object
     *
     * @param matcher    the object of matcher that catch this case
     * @param scopeDepth the scope depth where the method  defined
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void moreThanParameter(Matcher matcher, int scopeDepth) throws ValidityError {
        if (!matcher.matches()) {
            throw new ValidityError();
        }
        boolean isFinal = matcher.group(ONE).trim().equals(FINAL);
        String type = matcher.group(3).trim();
        String parameters = matcher.group(4).trim();
        String[] tokens = parameters.split(",");
        for (int i = ZERO; i < tokens.length; i++) {
            if (pattern1.matcher(tokens[i]).matches()) {
                String name = tokens[i].trim();
                if (scopes.get(scopeDepth).contain(name)) {
                    throw new ValidityError(moreThanParamError);
                }
                scopes.get(scopeDepth).addNew(type, name, null, isFinal);

            } else if (pattern2.matcher(tokens[i]).matches()) {
                String[] token = tokens[i].split("=");
                String name = token[ZERO].trim();
                String value = token[ONE].trim();
                validTypeValue(type, value);
                if (scopes.get(scopeDepth).contain(name)) {
                    throw new ValidityError(moreThanParamError);
                }
                scopes.get(scopeDepth).addNew(type, name, value, isFinal);
            } else {
                throw new ValidityError(moreThanParamError);
            }
        }
    }

}