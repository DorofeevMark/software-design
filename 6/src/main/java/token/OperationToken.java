package token;

public enum OperationToken implements Token {
    ADD(0),
    SUB(0),
    MUL(1),
    DIV(1);

    private final int priority;

    OperationToken(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
