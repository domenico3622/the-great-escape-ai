package it.unical.demacs.ai.model;

public class Settings
{
    public static int boardDim; // dimensioni della board
    public static enum Orientations {VERTICAL, HORIZONTAL, VOID};
    public static enum Directions {RIGHT, LEFT, UP, DOWN, VOID};

    public static String executablePath(String type) {
        if (type.equals("clingo")) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                return "lib/clingo.exe";
            } else if (os.contains("nix") || os.contains("nux")) {
                return "lib/clingo";
            } else if (os.contains("mac")) {
                return "lib/clingo-mac";
            }
        } else if (type.equals("dlv2")){
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                return "lib/dlv2.exe";
            } else if (os.contains("nix") || os.contains("nux")) {
                return "lib/dlv2";
            } else if (os.contains("mac")) {
                return "lib/dlv2-mac";
            }
        }
        return null;
    }
}
