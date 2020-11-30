package ru.state;


/**
 * Состояние ожидания команды /time
 */
public class Time implements State {

    private State nextState;

    @Override
    public void setNext() {
        nextState = new WaitingAnswer();

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
