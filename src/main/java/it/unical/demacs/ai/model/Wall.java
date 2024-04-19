package it.unical.demacs.ai.model;

public class Wall {
    private Settings.Orientations orientation;
    private Player owner;

    public Wall(){
        orientation = Settings.Orientations.VOID;
        owner = null;
    }

    public Wall(Settings.Orientations _orientation, Player _owner){
        orientation = _orientation;
        owner = _owner;
    }

    public Settings.Orientations getOrientation() {
        return orientation;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOrientation(Settings.Orientations orientation) {
        this.orientation = orientation;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }


}