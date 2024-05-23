package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("random")
public class RandomNumb {
    @Param(0)
    private int randomNum;

    public RandomNumb(int randomNum) {
        this.randomNum = randomNum;
    }

    public RandomNumb() {}

    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum =   randomNum;
    }
}
