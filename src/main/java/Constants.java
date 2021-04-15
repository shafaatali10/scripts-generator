import com.sun.org.apache.bcel.internal.generic.NEW;

public class Constants {
    public static final String CREATE_TABLE="CREATE TABLE ";
    public static final String BRACKET_OPEN="(";
    public static final String BRACKET_CLOSE=")";
    public static final String SPACE="\t";
    public static final String NEW_LINE="\n";
    public static final String COMMA=",";


    //Data types
    public static final String TYPE_VARCHAR="VARCHAR";
    public static final String TYPE_INT="INT";
    public static final String TYPE_DECIMAL="DECIMAL";
    public static final String TYPE_DATETIME="DATETIME";
    public static final String TYPE_DATE="DATE";
    public static final String BIT="bit";
    public static final String SEMI_COLON = ";";


    //Foreign Key Constraint
    public static final String REFERENCES = "REFERENCES";
    public static final String CONSTRAINT = "CONSTRAINT";
    public static final String FOREIGN_KEY = "FOREIGN KEY";
    public static final String FK_PREFIX = "fk";
    public static final String UNDERSCORE = "_";


    public static String constructColumn(String columnName, String dataType){
        return SPACE + SPACE + SPACE + columnName + SPACE + dataType;
    }
}
