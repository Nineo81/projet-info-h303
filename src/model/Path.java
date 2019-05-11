package model;

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
}
