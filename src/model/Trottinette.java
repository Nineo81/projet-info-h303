package model;

public class Trottinette {

    private int TID;
    private String dateService;
    private String model;
    private int plaint;
    private int battery;
    private String state;
    private Double posX;
    private Double posY;

    public Trottinette(int TID, String dateService, String model, int plaint, int battery, String state, Double posX, Double posY){
        this.TID = TID;
        this.dateService = dateService;
        this.model = model;
        this.plaint = plaint;
        this.battery = battery;
        this.state = state;
        this.posX = posX;
        this.posY = posY;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public String getDateService() {
        return dateService;
    }

    public void setDateService(String dateService) {
        this.dateService = dateService;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPlaint() {
        return plaint;
    }

    public void setPlaint(int plaint) {
        this.plaint = plaint;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }
}
