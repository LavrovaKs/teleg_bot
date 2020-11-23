package ru.urfu;

public interface State {
    void setNext();
    State getState();
    State getNext();
}
