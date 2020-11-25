package ru.urfu;

/**
 * Класс отвечает за текущее состояние и его переключение
 */
public class StateManager {
    private State currentState;

    public void setCurrentState(State s) {
        currentState = s;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void switchState() {
        currentState.setNext();
        setCurrentState(currentState.getNext());
    }
}
