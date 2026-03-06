package polygon;

import java.awt.geom.*; // for Point2D.Double
import java.util.ArrayList; // for ArrayList
// import gpdraw.*; // Commented out because the library is missing

public class IrregularPolygon {
    private ArrayList<Point2D.Double> myPolygon = new ArrayList<Point2D.Double>();

    public IrregularPolygon() {}

    public void add(Point2D.Double aPoint) {
        myPolygon.add(aPoint);
    }

    public double perimeter() {
        if (myPolygon.size() < 2) return 0.0;

        double totalPerimeter = 0.0;
        int n = myPolygon.size();

        for (int i = 0; i < n; i++) {
            Point2D.Double current = myPolygon.get(i);
            Point2D.Double next = myPolygon.get((i + 1) % n); // Wrap to start
            totalPerimeter += current.distance(next);
        }
        return totalPerimeter;
    }

    public double area() {
        if (myPolygon.size() < 3) return 0.0;

        double sum = 0.0;
        int n = myPolygon.size();

        for (int i = 0; i < n; i++) {
            Point2D.Double current = myPolygon.get(i);
            Point2D.Double next = myPolygon.get((i + 1) % n); // Wrap to start
            
            // Shoelace formula: (x0*y1 + x1*y2...) - (y0*x1 + y1*x2...)
            sum += (current.getX() * next.getY()) - (current.getY() * next.getX());
        }

        return Math.abs(sum) / 2.0;
    }

    public void draw() {
        // Wrap in try/catch as the template suggested for headless environments
        try {
            System.out.println("Drawing is disabled in this environment.");
            /* The following is commented out to resolve the compilation error 
               caused by the missing gpdraw library in Codespaces.
            
            DrawingTool pen = new DrawingTool(new SketchPad(500, 500));
            if (myPolygon.size() < 2) return;
            Point2D.Double start = myPolygon.get(0);
            pen.up();
            pen.move(start.getX(), start.getY());
            pen.down();
            for (int i = 1; i < myPolygon.size(); i++) {
                pen.move(myPolygon.get(i).getX(), myPolygon.get(i).getY());
            }
            pen.move(start.getX(), start.getY());
            */
        } catch (Exception e) {
            System.out.println("Exception: No graphics support available.");
        }
    }
}