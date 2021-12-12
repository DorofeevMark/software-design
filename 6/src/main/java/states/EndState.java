package states;

import token.Tokenizer;

public class EndState extends State {

    public EndState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void process(char ch) {
        throw new StateProcessException();
    }

    @Override
    public void end() {
    }
}
