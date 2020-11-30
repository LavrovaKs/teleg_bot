package ru.state;

public interface State {
    void setNext();

    State getState();

    State getNext();
}
