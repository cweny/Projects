package ca.ubc.ece.eece210.mp3.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.ubc.ece.eece210.mp3.Element;
import ca.ubc.ece.eece210.mp3.Catalogue;

public abstract class ASTNode {
    protected List<ASTNode> children;
    protected Token token;

    // "by(...)", "matches(...)" and "in(...)" and matches all take arguments
    // e.g. matches("Title"). The String "Title" must be stored in this arguments
    // variable.
    protected String arguments = null;

    public ASTNode(Token token) {
	children = new ArrayList<ASTNode>(2);
	this.token = token;
    }

    public void setArguments(String arguments) {
	this.arguments = arguments;
    }

    public void addChild(ASTNode node) {
	children.add(node);
    }

    public String getText() {
	return token.getPayload();
    }

    public abstract Set<Element> interpret(Catalogue argument);

    @Override
    public String toString() {
	if (children == null || children.size() == 0) {
	    return this.getText();
	}
	StringBuffer buf = new StringBuffer();
	if (children != null) {
	    buf.append("(");
	    buf.append(this.getText());
	    buf.append(' ');
	}
	for (int i = 0; children != null && i < children.size(); i++) {
	    ASTNode t = children.get(i);
	    if (i > 0) {
		buf.append(' ');
	    }
	    buf.append(t.toString());
	}
	if (children != null) {
	    buf.append(")");
	}
	return buf.toString();
    }
}
