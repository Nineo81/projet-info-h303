package model;

/**
 * Object used by javaFX to show in table
 */
public class Path {

    private String sourceX;
    private String sourceY;
    private String destinationX;
    private String destinationY;
    private String startTime;
    private String endTime;

    public Path(String sourceX, String sourceY, String destinationX,
                String destinationY, String startTime, String endTime){
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSourceX() {
        return sourceX;
    }

    public String getSourceY() {
        return sourceY;
    }

    public String getDestinationX() {
        return destinationX;
    }

    public String getDestinationY() {
        return destinationY;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
