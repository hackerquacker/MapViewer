package net.hackerquacker.cities.parser.token;

/**
 * This class encapulates a string token in a linked list.
 */
public class LexerToken {

    private final String token;
    private TokenType type = null;
    private LexerToken next = null;
    private LexerToken prev = null;

    public LexerToken(String token) {
        this.token = token;
    }

    public LexerToken(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    /**
     * Sets the next token after this token. Also sets the next's prev token to point to this token
     */
    public void setNext(LexerToken token) {
        this.next = token;
        if (this.next != null)
            this.next.prev = this;
    }

    /**
     * Returns true if there is a token after this token
     */
    public boolean hasNext() {
        return this.next != null;
    }

    /**
     * Returns true if there is a token before this one
     */
    public boolean hasPrev() {
        return this.prev != null;
    }

    /**
     * Returns the next token, or null if it doesn't exist
     * TODO: Probably throw an exception instead of return null.
     */
    public LexerToken next() {
        return this.next;
    }

    /**
     * Returns the prev token, or null if it doesn't exist
     * TODO: Probably throw an exception instead of return null.
     */
    public LexerToken prev() {
        return this.prev;
    }

    /**
     * Returns the token as a string
     */
    public String getStringToken() {
        return this.token;
    }

    /**
     * Returns a new token object for this token
     */
    public Token getToken() {
        if (this.type == null)
            return new Token(this.token);
        return new Token(this.token, this.type);
    }

    @Override
    public String toString() {
        return "LexerToken{" + this.token + "}";
    }
}
