package ru.state;


/**
 * Состояние ожидания сообщения
 */
public class WaitingMessage implements State {

    private State nextState;

    @Override
    public void setNext() {
        nextState = new WaitingEx();
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



