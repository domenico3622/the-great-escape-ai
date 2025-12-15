package it.unical.demacs.ai.model.ai.IRS;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.utils.Coordinates;

import java.awt.*;
import java.util.Arrays;
import java.util.PriorityQueue;

public class PathFinder {
    static class Node {
        int row, col, distance;

        public Node(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }

    enum Dir {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        private final int rowDelta;
        private final int colDelta;

        Dir(int rowDelta, int colDelta) {
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
        }

        public int getRowDelta() {
            return rowDelta;
        }

        public int getColDelta() {
            return colDelta;
        }
    }

    public static int shortestPath(Player player) {
        int rows = 9;
        int cols = 9;
        boolean[][] visited = new boolean[rows][cols];
        int[][] distances = new int[rows][cols];

        PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> a.distance - b.distance);

        for (int i = 0; i < rows; i++)
            Arrays.fill(distances[i], Integer.MAX_VALUE);

        Dir[] directions = { Dir.DOWN, Dir.UP, Dir.RIGHT, Dir.LEFT };

        queue.offer(new Node(player.getCoord().row, player.getCoord().column, 0));

        Player toCheck = new Player(player.getCoord(), player.getDirection(), player.getColor(), player.getPath());
        distances[player.getCoord().row][player.getCoord().column] = 0;
        while (!queue.isEmpty()) {

            Node curr = queue.poll();
            int row = curr.row;
            int col = curr.col;

            switch (player.getDirection()) {
                case RIGHT -> {
                    if (col == 8)
                        return distances[row][col];
                }
                case LEFT -> {
                    if (col == 0)
                        return distances[row][col];
                }
                case UP -> {
                    if (row == 0)
                        return distances[row][col];
                }
                case DOWN -> {
                    if (row == 8)
                        return distances[row][col];
                }
            }
            toCheck.setCoord(new Coordinates(row, col));
            if (visited[row][col])
                continue;

            visited[row][col] = true;
            for (Dir dir : directions) {

                int newRow = row + dir.getRowDelta();
                int newCol = col + dir.getColDelta();

                Settings.Directions casted = null;
                switch (dir) {
                    case RIGHT -> casted = Settings.Directions.RIGHT;
                    case LEFT -> casted = Settings.Directions.LEFT;
                    case DOWN -> casted = Settings.Directions.DOWN;
                    case UP -> casted = Settings.Directions.UP;
                }

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && Game.getInstance().canMove(toCheck, 0, 0, casted) && !visited[newRow][newCol]) {
                    int newDistance = distances[row][col] + 1;
                    if (newDistance < distances[newRow][newCol]) {
                        distances[newRow][newCol] = newDistance;
                        queue.offer(new Node(newRow, newCol, newDistance));
                    }
                }
            }
        }
        return -1;
    }
}
