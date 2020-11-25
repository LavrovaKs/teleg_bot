package ru.urfu;

/**
 * Состояние ожидания ответа на задания
 */
class WaitingAnswer implements State {

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
