package token;

import visitors.TokenVisitor;

public interface Token {
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
