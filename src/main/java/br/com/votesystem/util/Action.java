package br.com.votesystem.util;

import br.com.votesystem.enuns.CRUD;

public class Action<T> {
    private T obj = null;
    private CRUD action = null;

    /**
     * Constructor
     */
    public Action() {
        this.obj = null;
        this.action = null;
    }

    /**
     * Constructor
     * @param obj
     * @param action
     */
    public Action(final T obj, final CRUD action) {
        setObj(obj);
        setAction(action);
    }

    /*
     * Setters and Getters
     */

    /**
     * Return obj
     * @return obj
     */
    public T getObj() {
        return obj;
    }

    /**
     * Set obj
     * @param obj
     */
    public void setObj(T obj) {
        this.obj = obj;
    }

    /**
     * Return action
     * @return action
     */
    public CRUD getAction() {
        return action;
    }

    /**
     * Set action
     * @param action
     */
    public void setAction(CRUD action) {
        this.action = action;
    }
}
