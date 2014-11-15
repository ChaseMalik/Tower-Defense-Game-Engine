package gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation;

import static org.junit.Assert.*;
import java.util.List;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathCurve;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathLine;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathRoute;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import org.junit.Before;
import org.junit.Test;

public class BackendRouteTranslationTests {

    private Path myPath;

    @Before
    public void setup(){
        myPath = new Path(new Group());
    }

    @Test
    public void testVisibilityPointCreation() {
        boolean visibility = true;
        Point2D point = new Point2D(10, 10);
        VisibilityPoint visibilityPoint = new VisibilityPoint(true, point);
        assertEquals(visibility, visibilityPoint.isVisible());
        assertEquals(point, visibilityPoint.getPoint());
    }

    @Test
    public void testBackendRouteCreation() {
        PathRoute route = new PathRoute();
        PathLine pathLine = new PathLine(30, 30);
        route.add(pathLine);
        BackendRoute backendRoute = new BackendRoute(route);
        List<VisibilityPoint> visibilityPoints = backendRoute.getMyPoints();
        assertEquals(pathLine.getStartingPoint(), visibilityPoints.get(0).getPoint());
        assertEquals(pathLine.getEndingPoint(), visibilityPoints.get(1).getPoint());
    }

    @Test
    public void testRoutesTranslation() {
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        PathCurve pathCurve1 = new PathCurve(100, 100);
        pathCurve1.setEndingPoint(new Point2D(200, 200));
        PathCurve curveInRoute2 = new PathCurve(400, 50);
        curveInRoute2.setEndingPoint(new Point2D(400, 100));
        myPath.addComponentToPath(pathLine1);
        myPath.addComponentToPath(pathCurve1);
        myPath.addComponentToPath(curveInRoute2); 
        List<BackendRoute> routes = BackendRoutesGenerator.getBackendRoutes(myPath);
        assertEquals(2, routes.size());
        BackendRoute route1 = routes.get(0);
        List<VisibilityPoint> route1VisibilityPoints = route1.getMyPoints();
        Point2D[] expectedPointsForRoute1 = new Point2D[]{pathLine1.getStartingPoint(), pathCurve1.getStartingPoint(),
                                                          pathCurve1.getEndingPoint()};
        testCorrectRouteTranslation(expectedPointsForRoute1, route1VisibilityPoints);
        BackendRoute route2 = routes.get(1);
        List<VisibilityPoint> route2VisibilityPoints = route2.getMyPoints();
        Point2D[] expectedPointsForRoute2 = new Point2D[]{curveInRoute2.getStartingPoint(),
                                                          curveInRoute2.getEndingPoint()};
        testCorrectRouteTranslation(expectedPointsForRoute2, route2VisibilityPoints);
    }

    private void testCorrectRouteTranslation (Point2D[] expectedPointsForRoute,
                                              List<VisibilityPoint> routeVisibilityPoints) {
        assertEquals(expectedPointsForRoute.length, routeVisibilityPoints.size());
        for(int i = 0; i < routeVisibilityPoints.size(); i++){
            assertEquals(expectedPointsForRoute[i], routeVisibilityPoints.get(i).getPoint());
        }
    }
}
