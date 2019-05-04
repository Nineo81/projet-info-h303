package model;

public class Trottinette {

    private int TID;
    private int dateService;
    private int model;
    private int plaint;
    private int battery;
    private int disponibility;
    private int posX;
    private int posY;

    public Trottinette(int TID, int dateService, int model, int plaint, int battery, int disponibility, int posX, int posY){
        this.TID = TID;
        this.dateService = dateService;
        this.model = model;
        this.plaint = plaint;
        this.battery = battery;
        this.disponibility = disponibility;
        this.posX = posX;
        this.posY = posY;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getDateService() {
        return dateService;
    }

    public void setDateService(int dateService) {
        this.dateService = dateService;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
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

    public int getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(int disponibility) {
        this.disponibility = disponibility;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
