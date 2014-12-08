package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

/**
 * A class to hold information about a slider.
 * @author Austin Kyker
 *
 */
public class SliderInfo {
    private String myInfo;
    private double myMin;
    private double myMax;
    
    public SliderInfo(String info, double min, double max) {
        myInfo = info;
        myMin = min;
        myMax = max;
    }

    public String getMyInfo () {
        return myInfo;
    }

    public double getMyMin () {
        return myMin;
    }

    public double getMyMax () {
        return myMax;
    }
}