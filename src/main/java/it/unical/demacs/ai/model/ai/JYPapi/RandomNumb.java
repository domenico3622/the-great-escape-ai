package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("random")
public class RandomNumb {
    @Param(0)
    int randomNumb;


    public int getRandomNumb() {
        return randomNumb;
    }

    public void setRandomNumb(int randomNumb) {
        this.randomNumb = randomNumb;
    }
}
