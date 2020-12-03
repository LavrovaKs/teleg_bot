package ru.state;

public class WaitingName implements State {

    private State nextState;

    @Override
    public void setNext() {
        nextState = new WaitingMessage();
    }

    @Override
    public State getState() {
        return this;
    }

    @Override
    public State getNext() {
        return nextState;
    }
}
