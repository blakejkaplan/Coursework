package model;

/**
 * IfElse function.
 */
public class IfElse extends IfNode {

    private static final String IFELSE = "IfElse ";

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return IFELSE + childrenToString();
    }

}
