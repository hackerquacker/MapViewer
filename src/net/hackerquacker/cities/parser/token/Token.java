package net.hackerquacker.cities.parser.token;

/**
 * Token Type
 */
public class Token {

    /**
     * The string representation of the token
     */
    private final String token;

    /**
     * The type of this token
     */
    private final TokenType type;

    /**
     * Allows this object to be both a node in a tree and a doubly linked list
     */
    private Token parent = null;
    private Token next = null;
    private Token prev = null;

    /**
     * Default constructor
     *
     * @param token The raw string representation of this token
     */
    public Token(String token) {
        this.token = token;
        this.type = Token.getTypeFromString(token);
    }

    public Token(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    /**
     * Returns the string representation of this token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Returns the type that this token is
     */
    public TokenType getType() {
        return this.type;
    }

    /**
     * Returns the next token
     */
    public Token next() {
        return this.next;
    }

    /**
     * Returns the previous token
     */
    public Token prev() {
        return this.prev;
    }

    /**
     * Sets the next token for this token
     */
    public void setNext(Token next) {
        //if (this.next != null)
        //    this.next.setPrev(null);
        this.next = next;

        //if (next != null)
        //next.setPrev(this);
    }

    /**
     * Sets the previous token for this token
     */
    public void setPrev(Token token) {
        this.prev = token;
        if (token != null)
            token.setNext(this);
    }

    /*
     *  Implement the basic java methods for this object
     */

    @Override
    public boolean equals(Object o) {
        if (o instanceof Token)
            return (((Token) o).getType() == this.getType() && ((Token) o).getToken().equals(this.getToken()));

        return false;
    }

    public boolean equals(TokenType type) {
        return this.getType().equals(type);
    }

    public boolean equals(String token) {
        return this.getToken().equals(token);
    }

    @Override
    public String toString() {
        return "Token{'" + this.token + "', " + String.format("%s", this.type) + "}";
    }

    /**
     * This function takes a raw token string and tries to workout what type the token is
     *
     * @param token the raw token string
     */
    private static TokenType getTypeFromString(String token) {
        // TODO: Implement this method to return the type of the token from the token string
        if (token.equals("("))
            return TokenType.OPEN_BRACKET;
        if (token.equals(")"))
            return TokenType.CLOSED_BRACKET;
        if (token.equals(","))
            return TokenType.SEPERATOR;
        if (token.equals(";"))
            return TokenType.EOL;
        if (token.equals("{"))
            return TokenType.OPEN_BLOCK;
        if (token.equals("}"))
            return TokenType.CLOSED_BLOCK;
        if (token.equals("="))
            return TokenType.ASSIGN;
        if (token.equals(":"))
            return TokenType.TYPE_DEF;
        if (token.equals("->"))
            return TokenType.TYPE_DEF;
        if (token.matches("(<|>|\\+|\\-|\\*|/||!|=)=") || token.equals("&&") || token.equals("||") || token.equals("++") || token.equals("--"))
            return TokenType.OPERATOR;
        if (token.matches("if|while|else|elif|for"))
            return TokenType.CONDITION;
        if (token.matches("func|static|const|var|this|return|new"))
            return TokenType.KEYWORD;
        return TokenType.IDENTIFIER;
    }

    public void setParent(Token peek) {
        this.parent = peek;
    }
}
