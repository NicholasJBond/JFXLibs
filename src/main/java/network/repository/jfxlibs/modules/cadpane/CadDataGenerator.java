package network.repository.jfxlibs.modules.cadpane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CadDataGenerator {
    private static final Random random = new Random();

    public static List<CadFeature> createSampleData() {
        List<CadFeature> features = new ArrayList<>();

        // 1. Add a Grid (Static background lines)
        for (int i = 0; i <= 1000; i += 10) {
            features.add(new CadLine(random.nextDouble() * 1000, random.nextDouble() * 1000, random.nextDouble() * 1000, random.nextDouble() * 1000)); // Vertical
        }

        // 2. Add 1,000 Random Points
        for (int i = 0; i < 1000; i++) {
            features.add(new CadPoint("Point " + i,random.nextDouble() * 1000, random.nextDouble() * 1000,0));
        }

        // 3. Add 50 Random Polygons
        for (int i = 0; i < 50; i++) {
            features.add(createRandomPolygon(3 + random.nextInt(5), 1600, 1000));
        }

        return features;
    }

    private static CadPolygon createRandomPolygon(int sides, double maxWidth, double maxHeight) {
        double centerX = random.nextDouble() * maxWidth;
        double centerY = random.nextDouble() * maxHeight;
        double radius = 20 + random.nextDouble() * 50;

        double[] x = new double[sides];
        double[] y = new double[sides];

        for (int i = 0; i < sides; i++) {
            double angle = 2 * Math.PI * i / sides;
            x[i] = centerX + Math.cos(angle) * radius;
            y[i] = centerY + Math.sin(angle) * radius;
        }
        return new CadPolygon(x, y);
    }
}
