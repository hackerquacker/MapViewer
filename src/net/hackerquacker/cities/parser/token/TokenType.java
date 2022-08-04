package net.hackerquacker.cities.parser.token;

/**
 * This class contains all the token types
 */
public enum TokenType {

    /**
     * Any user defined variables or functions
     */
    IDENTIFIER,

    /**
     * ==, !=, <, <=, etc... including 'or' and 'and'
     */
    OPERATOR,

    /**
     * Conditional keywords, like: if, for, while
     */
    CONDITION,

    /**
     * Any other keywords like: struct, class, func, method
     */
    KEYWORD,

    /**
     * Number literals (0, -3, 5.12)
     */
    INTEGER_LIT,

    /**
     * String literals (strings enclosed with '"')
     */
    STRING_LIT,

    /**
     * The semicolon
     */
    EOL,

    /**
     * Self explanitory: ( ) { }
     */
    OPEN_BRACKET,
    CLOSED_BRACKET,
    OPEN_BLOCK,
    CLOSED_BLOCK,

    /**
     * The = when assigning a variable
     */
    ASSIGN,

    /**
     * The var and const keywords
     */
    VARIABLE,

    /**
     * The , for seperating parameters
     */
    SEPERATOR,

    TYPE_DEF,

    UNKNOWN;
}
