package backend.controller;

import backend.repository.Repository;

// Controller handles and processes requests from given endpoints
public abstract class Controller {
    protected final Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }
}
