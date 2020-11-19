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

class WaitingMessage implements State{

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

class Time implements State{

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

    public void switchState(){
        currentState.setNext();
        setCurrentState(currentState.getNext());
    }
}