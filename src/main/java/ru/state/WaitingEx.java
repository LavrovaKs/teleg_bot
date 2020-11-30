package ru.state;

import ru.state.State;
import ru.state.WaitingAnswer;

/**
 * Состоягие ожидания команды /exercise
 */
public class WaitingEx implements State {

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
