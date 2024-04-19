package it.unical.demacs.ai.model;

public class Wall {
    private Settings.Orientations orientation;
    private Settings.Directions owner;

    public Wall(){
        orientation = Settings.Orientations.VOID;
        owner = Settings.Directions.VOID;
    }

    public Wall(Settings.Orientations _orientation, Settings.Directions _owner){
        orientation = _orientation;
        owner = _owner;
    }

    public Settings.Orientations getOrientation() {
        return orientation;
    }

    public Settings.Directions getOwner() {
        return owner;
    }

    public void setOrientation(Settings.Orientations orientation) {
        this.orientation = orientation;
    }

    public void setOwner(Settings.Directions owner) {
        this.owner = owner;
    }


}