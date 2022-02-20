package oop.ex6.Operations;

import java.util.regex.Pattern;

/**
 * the interface that contain all the regex that used in this program
 */
public interface Spliting {
    public final static String EMPTYLINE = "\\s*";
    public final String TYPE = "(String|double|int|char|boolean)";
    public final String BOOLEAN = "((\\s*)(\\s*true\\s*|\\s*false\\s*|(\\s*)(-{0,1}\\d+(\\.\\d+){0,1})\\s*))";
    public final String INT = "((\\s*)(-{0,1}\\d+)\\s*)";
    public final String DOBULE = "((\\s*)(-{0,1}\\d+\\.{0,1}\\d+|" + INT + ")\\s*)";
    public final String STRING = "\\s*\\\"\\s*[\\w]+\\s*\\\"\\s*|\\\"\\\"";
    public static final String CHAR = "\\s*\\'.{1}\\'\\s*";
    public static final String VARDICNAME = "(\\s*([a-zA-Z]\\w*)\\s*|\\s*(_\\w+)\\s*)\\s*";


    public final static String COMPLEX = VARDICNAME + "\\s*=\\s*" + "(" + BOOLEAN + "|" +
            INT + "|" + STRING + "|" + CHAR + "|" + DOBULE + ")" + "\\S*";


    public final String REGEXOFRETURN = "\\s*return\\s*;\\s*";

    public final String METHOD_DEFINTION = "\\s*(void)\\s+([a-zA-Z]+\\w*)\\s*\\(\\s*(" +
            "(\\s*"+TYPE+"\\s+(\\s*([a-zA-Z]\\w*)|" +
            "(_\\w+)\\s*)\\s*)([,]\\s*"+TYPE+"\\s+" +
            "(\\s*([a-zA-Z]\\w*)|(_\\w+)\\s*))*|(\\s*))\\s*\\)\\s*\\{\\s*";

    public static final String CALLING_METHOD = "\\s*([a-zA-Z]{1}\\w*)\\s*\\((.*)\\)\\s*;\\s*";
    public static final String ENDINGOF = "\\s*(\\})\\s*";
    public static final String COMMENT = "^(\\/{2}).*";

    public static final String IF = "\\s*(if)\\s*\\(\\s*(.+)\\s*\\)\\s*\\{\\s*";
    public static final String IFCONDITION = "double|int|boolean";
    public static Pattern ifcondition = Pattern.compile(IFCONDITION);


    public static final String WHILE = "\\s*(while)\\s*\\(\\s*(\\s*("
            + INT + "|" + BOOLEAN + "|" + DOBULE + "|" + VARDICNAME + ")\\s*((\\|\\||\\&\\&)\\s*(\\s*"
            + INT + "|" + BOOLEAN + "|" + DOBULE + "|" + VARDICNAME + ")\\s*)*\\s*)\\s*\\)\\s*\\{\\s*";

    public static final String FINAL_VARDIK_LINE = "\\s*(final)\\s+" + TYPE + "\\s" + VARDICNAME
            + "\\s*=\\s*(" + DOBULE + "|" + INT + "|" + BOOLEAN + "|" + VARDICNAME + "|" + STRING + "|"
            + CHAR + ")+\\s*;\\s*";

    public static final String VARIDK_LINE = "\\s*" + TYPE + "\\s" + VARDICNAME + "\\s*=\\s*(" +
            DOBULE + "|\\\"\\\"|" + INT + "|" + BOOLEAN + "|" + VARDICNAME + "|" + STRING + "|"
            + CHAR + ")\\s*;\\s*";


    public static final String WITHOUT_EQUAL_MODIFER = "\\s*" + TYPE + "\\s(\\s*" + VARDICNAME +
            "\\s*)\\s*;\\s*";
    public static final String WITH_OUT_TYPE = "\\s*" + VARDICNAME + "\\s=\\s*(" + DOBULE +
            "|" + INT + "|" + BOOLEAN + "|" + VARDICNAME + "|" + STRING + "|" + CHAR + ")+\\s*;\\s*";
    public static final String MORE_THAN_PARAMETER = "([ |\\t]*)(final\\s+){0,1}" +
            TYPE +
            "\\s*(([a-zA-z_{2}]+\\w*(\\s*=\\s*(\"(.)\"|'.{0,1}'|[a-zA-z_{2}]+\\w|true|false|-{0,1}" +
            "\\d+|(-{0,1}\\d+\\.{0,1}\\d+))\\s*)?\\s*,\\s*)+([a-zA-z_{2}]+\\w*(\\s*=\\s*(-{0,1}\\d+" +
            "|\"(.)\"|'.?'|[a-zA-z_{2}]+\\w|true|false|(-{0,1}\\d+\\.{0,1}\\d+))\\s*)?\\s*))\\s*;\\s*";

    public static Pattern finalVardicLine = Pattern.compile(FINAL_VARDIK_LINE);
    public static Pattern methodDefintion = Pattern.compile(METHOD_DEFINTION);
    public static Pattern methodCalling = Pattern.compile(CALLING_METHOD);
    static Pattern returnReg = Pattern.compile(REGEXOFRETURN);
    static Pattern ifReg = Pattern.compile(IF);
    static Pattern withoutType = Pattern.compile(WITH_OUT_TYPE);
    static Pattern comments = Pattern.compile(COMMENT);
    static Pattern whileReg = Pattern.compile(WHILE);
    static Pattern vardicLine = Pattern.compile(VARIDK_LINE);
    static Pattern withotEqualModifer = Pattern.compile(WITHOUT_EQUAL_MODIFER);
    static Pattern moreThanParameter = Pattern.compile(MORE_THAN_PARAMETER);
    static Pattern endingMethod = Pattern.compile(ENDINGOF);
    static Pattern emptyLine = Pattern.compile(EMPTYLINE);

}