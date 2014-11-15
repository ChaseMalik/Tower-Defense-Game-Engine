package gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathRoute;
import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to generate the back-end representation of
 * routes from a front-end path object. A route on the back-end will be
 * represented by a list of Visibility Points. A visibility point determines
 * if the enemy will be visible when traveling between two points and a list of
 * points that the enemy will go to one by one representing the route drawn
 * on the front-end in the path-building scene.
 * @author Austin Kyker
 *
 */
public class BackendRoutesGenerator {
    public static List<BackendRoute> getBackendRoutes(Path path) {
        List<BackendRoute> routes = new ArrayList<BackendRoute>();
        for(PathRoute route:path) {
            routes.add(new BackendRoute(route));
        }     
        return routes;
    }
}
