package gameAuthoring.scenes.pathBuilding.pathComponents;


import static org.junit.Assert.*;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import org.junit.Before;
import org.junit.Test;


public class PathCreationTests {
    
    private Path myPath;
    
    @Before
    public void setup(){
        myPath = new Path(new Group());
    }

    @Test
    public void AddSingleLineComponentTest () {
        PathLine pathLine = new PathLine(30, 30);
        myPath.addComponentToPath(pathLine);
        assertEquals(1, myPath.getAllPathComponents().size());
        assertEquals(pathLine, myPath.getAllPathComponents().get(0));
        assertEquals(1, myPath.getNumRoutes());
    }
    
    @Test
    public void AddSingleCurveComponentTest () {
        PathCurve pathCurve = new PathCurve(30, 30);
        myPath.addComponentToPath(pathCurve);
        assertEquals(1, myPath.getAllPathComponents().size());
        assertEquals(pathCurve, myPath.getAllPathComponents().get(0));
        assertEquals(1, myPath.getNumRoutes());
    }
    
    @Test
    public void ConnectTwoLinesTest () {
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        PathLine pathLine2 = new PathLine(100, 100);
        pathLine2.setEndingPoint(new Point2D(200, 200));
        myPath.addComponentToPath(pathLine1);
        myPath.addComponentToPath(pathLine2);
        assertEquals(2, myPath.getAllPathComponents().size());
        assertEquals(1, myPath.getNumRoutes());       
    }
    
    @Test
    public void ConnectTwoCurvesTest () {
        PathCurve pathCurve1 = new PathCurve(30, 30);
        pathCurve1.setEndingPoint(new Point2D(100, 100));
        PathCurve pathCurve2 = new PathCurve(100, 100);
        pathCurve2.setEndingPoint(new Point2D(200, 200));
        myPath.addComponentToPath(pathCurve1);
        myPath.addComponentToPath(pathCurve2);
        assertEquals(2, myPath.getAllPathComponents().size());
        assertEquals(1, myPath.getNumRoutes());       
    }
    
    @Test
    public void ConnectLineToCurveTest () {
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        PathCurve pathCurve1 = new PathCurve(100, 100);
        pathCurve1.setEndingPoint(new Point2D(200, 200));
        myPath.addComponentToPath(pathLine1);
        myPath.addComponentToPath(pathCurve1);
        assertEquals(2, myPath.getAllPathComponents().size());
        assertEquals(1, myPath.getNumRoutes());       
    }
    
    @Test
    public void CreateTwoRoutesTest () {
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        PathCurve pathCurve1 = new PathCurve(100, 100);
        pathCurve1.setEndingPoint(new Point2D(200, 200));
        PathCurve curveInNewRoute = new PathCurve(400, 50);
        curveInNewRoute.setEndingPoint(new Point2D(400, 100));
        myPath.addComponentToPath(pathLine1);
        myPath.addComponentToPath(pathCurve1);
        myPath.addComponentToPath(curveInNewRoute);
        assertEquals(3, myPath.getAllPathComponents().size());
        assertEquals(2, myPath.getNumRoutes());       
    }
    
    @Test
    public void StartingLocationsMustExistToBeConfiguredCorrectlyTest() {
        assertFalse(myPath.startingLocationsConfiguredCorrectly());
        myPath.addStartingLocation(20, 20);
        assertTrue(myPath.startingLocationsConfiguredCorrectly());
    }
    
    @Test
    public void EndingLocationsMustExistToBeConfiguredCorrectlyTest() {
        assertFalse(myPath.endingLocationsConfiguredCorrectly());
        myPath.addEndingLocation(20, 20);
        assertTrue(myPath.endingLocationsConfiguredCorrectly());
    }
    
    @Test
    public void ClearStartingLocationsTest() {
        myPath.addStartingLocation(20, 20);
        myPath.clearEnemyStartingLocations();
        assertFalse(myPath.startingLocationsConfiguredCorrectly());
    }
    
    @Test
    public void ClearEndingLocationsTest() {
        myPath.addEndingLocation(20, 20);
        myPath.clearEnemyEndingLocations();
        assertFalse(myPath.endingLocationsConfiguredCorrectly());
    }   
    
    @Test
    public void ConnectComponentToStartingLocationTest() {
        myPath.addStartingLocation(30, 30);
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        myPath.addComponentToPath(pathLine1);
        assertTrue(myPath.getRoutes().get(0).isConnectedToStartingLocation());
    }
    
    @Test
    public void ConnectComponentToEndingLocationTest() {
        myPath.addEndingLocation(100, 100);
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        myPath.addComponentToPath(pathLine1);
        myPath.tryToAddConnectComponentToEndingLocation(pathLine1);
        assertTrue(myPath.getRoutes().get(0).isConnectToEndingLocation());
    }
    
    @Test
    public void IsLocationCloseToMethodTest() {
        PathStartingLocation loc = new PathStartingLocation(100, 100);
        Point2D closePoint = new Point2D(105, 110);
        Point2D farPoint = new Point2D(250, 250);
        assertTrue(myPath.isLocationCloseToPoint(loc, closePoint));
        assertFalse(myPath.isLocationCloseToPoint(loc, farPoint));
    }
    
    @Test
    public void AddingStartingLocationWithNearbyStartLocationTest() {
        myPath.addStartingLocation(120, 110);
        assertFalse(myPath.canCreateLocationAtPoint(100, 100));
        myPath.clearEnemyStartingLocations();
        assertTrue(myPath.canCreateLocationAtPoint(100, 100));
    }
    
    @Test
    public void AddingStartingLocationWithNearbyEndingLocationTest() {
        myPath.addEndingLocation(120, 110);
        assertFalse(myPath.canCreateLocationAtPoint(100, 100));
        myPath.clearEnemyEndingLocations();
        assertTrue(myPath.canCreateLocationAtPoint(100, 100));
    }
    
    @Test
    public void GetRouteContainingComponentTest() {
        PathLine pathLine1 = new PathLine(30, 30);
        pathLine1.setEndingPoint(new Point2D(100, 100));
        PathLine pathLine2 = new PathLine(300, 0);
        pathLine2.setEndingPoint(new Point2D(330, 0));
        myPath.addComponentToPath(pathLine1);
        myPath.addComponentToPath(pathLine2);
        assertNotEquals(myPath.getRouteContaining(pathLine1),
                        myPath.getRouteContaining(pathLine2));
        assertEquals(myPath.getRoutes().get(0), myPath.getRouteContaining(pathLine1));
        assertEquals(myPath.getRoutes().get(1), myPath.getRouteContaining(pathLine2));
    }
}