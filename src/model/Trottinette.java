package model;

public class Trottinette {

    private String name;
    private String position;

    public Trottinette(String name, String position){
        this.name = name;
        this.position = position;
    }

    public String getName(){
        return name;
    }

    public String getPosition(){
        return position;
    }
}
