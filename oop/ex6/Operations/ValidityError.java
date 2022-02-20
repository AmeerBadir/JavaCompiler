package oop.ex6.Operations;


/**
 * the class of the exception that
 * throws when the file contain invalid line
 */
public class ValidityError extends Exception {
    ValidityError() {
    }

    ValidityError(String errorMsg) {
        super(errorMsg);
    }


}
