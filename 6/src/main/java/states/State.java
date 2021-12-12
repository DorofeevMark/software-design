package states;

import token.Tokenizer;

public abstract class State {
    protected final Tokenizer tokenizer;

    public State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract void process(char ch);

    public void end() {
        tokenizer.setState(new EndState(tokenizer));
    }
}