package ca.ubc.ece.eece210.mp3.ast;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Sathish Gopalakrishnan
 *
 */

public class Token {

    private TokenType type;
    private String payload;

    public static final Map<String, Token> staticTokens = new HashMap<String, Token>();

    static {
	staticTokens.put("||", new Token(TokenType.OR, "||"));
	staticTokens.put("&&", new Token(TokenType.AND, "&&"));
	staticTokens.put("in", new Token(TokenType.IN, "in"));
	staticTokens.put("matches", new Token(TokenType.MATCHES, "matches"));
	staticTokens.put("(", new Token(TokenType.L_PARAN, "("));
	staticTokens.put(")", new Token(TokenType.R_PARAN, ")"));
	staticTokens.put("END", new Token(TokenType.END, "END"));
    }

    public Token(TokenType type, String payload) {
	this.type = type;
	this.payload = payload;
    }

    public TokenType getType() {
	return type;
    }

    public String getPayload() {
	return payload;
    }

    public static Token getTokenInstance(String tokenText) {
	Token match = staticTokens.get(tokenText);
	if (match == null)
	    match = new Token(TokenType.STRING, tokenText);
	return match;
    }

    @Override
    public String toString() {
	return "[" + type + ", " + payload + "]";
    }

}
