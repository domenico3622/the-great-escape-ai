package it.unical.demacs.ai;

import java.awt.*;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Objects;
import javax.imageio.ImageIO;

public class InfoManager {
    private static InfoManager instance = null;
    private static Dictionary<String, Image> playersIcon;

    private InfoManager() {
    }

    public static InfoManager getInstance() {
        if (instance == null) {
            instance = new InfoManager();
        }
        return instance;
    }
}
