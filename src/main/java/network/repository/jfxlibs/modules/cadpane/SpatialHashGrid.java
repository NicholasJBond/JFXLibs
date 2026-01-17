package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;

import java.util.*;

public class SpatialHashGrid {
    private final double cellSize;
    // Map of "X_Y" string keys to a list of points in that cell
    private final Map<String, List<Point2D>> grid = new HashMap<>();

    public SpatialHashGrid(double cellSize) {
        this.cellSize = cellSize;
    }

    // Convert world coordinates into a grid key: "5_12"
    private String getHashKey(double x, double y) {
        int gx = (int) Math.floor(x / cellSize);
        int gy = (int) Math.floor(y / cellSize);
        return gx + "_" + gy;
    }

    public void insert(double x, double y) {
        String key = getHashKey(x, y);
        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(new Point2D(x, y));
    }

    public List<Point2D> query(double minX, double minY, double maxX, double maxY) {
        List<Point2D> found = new ArrayList<>();

        // Find which grid cells overlap the view window
        int startX = (int) Math.floor(minX / cellSize);
        int endX   = (int) Math.floor(maxX / cellSize);
        int startY = (int) Math.floor(minY / cellSize);
        int endY   = (int) Math.floor(maxY / cellSize);

        // Only loop through relevant cells
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                List<Point2D> cellPoints = grid.get(x + "_" + y);
                if (cellPoints != null) {
                    found.addAll(cellPoints);
                }
            }
        }
        return found;
    }
}