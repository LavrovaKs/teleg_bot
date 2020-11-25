package ru.urfu;

/**
 * Состояние ожидания команды /time
 */
class Time implements State {

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
