package ca.ubc.ece.eece210.mp3.ast;

import java.util.Set;

import ca.ubc.ece.eece210.mp3.Element;
import ca.ubc.ece.eece210.mp3.Catalogue;

/**
 * 
 * @author Sathish Gopalakrishnan
 *
 */

public class AndNode extends ASTNode {

    public AndNode(Token token) {
	super(token);
    }

    @Override
    public Set<Element> interpret(Catalogue argument) {
	// TODO Auto-generated method stub
	return null;
    }

}
