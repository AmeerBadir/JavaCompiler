package oop.ex6.Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;


/**
 * this class responsible of check each line
 * in the file
 */
public abstract class analyzeLine implements Spliting {
    public static final int ZERO = 0;
    public static final int ONE = 1;

    /**
     * this matcher will be used to
     * check each line for which case is suitable
     */
    private static Matcher matcher;
    /**
     * this parameter show the depth of scope right now
     * the global field is in depth 0
     * when this symbol ({) appear the scope depth increase by one
     * when this symbol (}) appear the scope depth decrease by one
     */
    private static int scopeDepth = ZERO;
    /**
     * this counter save the number of the methods
     * defined in the file
     */
    private static int counter = ZERO;

    private static final String ifError = "YOU DEFINE IF IN THE GLOBAL FIELD";
    private static final String whileError = "YOU DEFINE WHILE IN THE GLOBAL FIELD";
    private static final String methodDefineError = "YOU DEFINE METHOD INTO METHOD";
    private static final String returnError = "THERE IS ERROR IN THE RETURN";
    private static final String lineError = "THIS LINE NOT MATCH ANY CASE";
    private static final String scopeError = "THERE IS NOT MATCH BETWEEN SCOPE OPEN AND SCOPE CLOSE";


    /**
     * this function that responsible of fitting each line
     * for what case it suitable(like method definition...)
     * then call the function that will operate in this line
     *
     * @param records array list of all the lines
     * @throws ValidityError in case of error that the program will return 1
     */
    public static void checkLines(ArrayList<String> records) throws ValidityError {
        Operation.scopes.add(new Scope());
        for (int i = ZERO; i < records.size(); i++) {
            if (emptyLine.matcher(records.get(i)).matches() || comments.matcher(records.get(i)).matches()) {
                continue;

            } else if (finalVardicLine.matcher(records.get(i)).matches()) {

                matcher = finalVardicLine.matcher(records.get(i));
                Operation.initializeFinal(matcher, scopeDepth);

            } else if (withoutType.matcher(records.get(i)).matches()) {
                matcher = withoutType.matcher(records.get(i));
                Operation.assignVar(matcher, scopeDepth);

            } else if (withotEqualModifer.matcher(records.get(i)).matches()) {
                matcher = withotEqualModifer.matcher(records.get(i));
                Operation.initializeVar(matcher, scopeDepth);

            } else if (vardicLine.matcher(records.get(i)).matches()) {
                matcher = vardicLine.matcher(records.get(i));
                Operation.decelerateVar(matcher, scopeDepth);

            } else if (ifReg.matcher(records.get(i)).matches()) {
                if (scopeDepth == ZERO) {
                    throw new ValidityError(ifError);
                }
                matcher = ifReg.matcher(records.get(i));
                scopeDepth++;
                Operation.scopes.add(new Scope());
                Operation.scopes.get(scopeDepth).prevScope = Operation.scopes.get(scopeDepth - ONE);
                Operation.ifWhileStatement(matcher, scopeDepth, records.get(i));

            } else if (whileReg.matcher(records.get(i)).matches()) {
                if (scopeDepth == ZERO) {
                    throw new ValidityError(whileError);
                }
                matcher = whileReg.matcher(records.get(i));
                scopeDepth++;
                Operation.scopes.add(new Scope());
                Operation.scopes.get(scopeDepth).prevScope = Operation.scopes.get(scopeDepth - ONE);
                Operation.ifWhileStatement(matcher, scopeDepth, records.get(i));

            } else if (methodDefintion.matcher(records.get(i)).matches()) {
                if (scopeDepth != ZERO) {
                    throw new ValidityError(methodDefineError);
                }
                matcher = methodDefintion.matcher(records.get(i));
                scopeDepth++;
                Operation.scopes.add(new Scope());
                Operation.scopes.get(scopeDepth).prevScope = Operation.scopes.get(scopeDepth - 1);
                Operation.defineMethod(matcher, scopeDepth);

            } else if (methodCalling.matcher(records.get(i)).matches()) {
                matcher = methodCalling.matcher(records.get(i));
                Operation.callMethod(matcher, scopeDepth);
            } else if (endingMethod.matcher(records.get(i)).matches()) {
                scopeDepth--;
                if (scopeDepth < ZERO) {
                    throw new ValidityError(scopeError);
                }

            } else if (returnReg.matcher(records.get(i)).matches()) {

                if (scopeDepth == ZERO) {
                    throw new ValidityError(returnError);
                }
                if (i + ONE == records.size()) {
                    throw new ValidityError(returnError);
                }
                if (scopeDepth == ONE && endingMethod.matcher(records.get(i + ONE)).matches()) {
                    counter++;
                }

            } else if (moreThanParameter.matcher(records.get(i)).matches()) {

                matcher = moreThanParameter.matcher(records.get(i));
                Operation.moreThanParameter(matcher, scopeDepth);

            } else {

                throw new ValidityError(lineError);
            }
        }
        if (scopeDepth != ZERO) { // check if the depth is zero
            throw new ValidityError(scopeError);
        }
        if (Operation.allMethods.size() != counter) { // check the number of methods
            throw new ValidityError(methodDefineError);
        }
        Operation.checkVariablesForIfWhile(); // check if variables that appear while it not
        // not initialized, were init after
    }

    /**
     * because we use static modifier in
     * this project we define this function
     * to initialize new for new file
     */
    public static void newBeginning() {
        scopeDepth = ZERO;
        counter = ZERO;
        Operation.scopes = new ArrayList<>();
        Operation.methodsCalledNotFound = new ArrayList<>();
        Operation.allMethodsGlobal = new HashMap<>();
        Operation.variablesForIfWhile = new HashMap<>();
        Operation.allMethods = new ArrayList<>();
        Operation.methodNotCalled = new ArrayList<>();
    }
}
