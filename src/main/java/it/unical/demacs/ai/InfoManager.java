package it.unical.demacs.ai;

public class InfoManager {
    private InfoManager instance = null;

    private InfoManager() {
    }

    public InfoManager getInstance() {
        if (instance == null) {
            instance = new InfoManager();
        }
        return instance;
    }
}
