package ru.urfu;


interface State {
    void setNext();
    State getState();
    State getNext();


}

class WaitingEx implements State{

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

class WaitingAnswer implements State{

    private State nextState;

    @Override
    public void setNext() {
        nextState = new CheckAnswer();
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

class CheckAnswer implements State{

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

class Context {

    private State currentState;

    public void setCurrentState(State s){ currentState = s; }

    public State getCurrentState(){ return currentState; }

    public State switchState(){ return currentState.getNext(); }
}