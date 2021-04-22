package com.example.gym_app.Model;

public class Excercise {

    private int image_id;
    private String name;
    private String opiss;

    public String getOpis() {
        return opiss;
    }

    public void setOpis(String opis) {
        this.opiss = opis;
    }

    public Excercise(int image_id, String name, String opiss){
        this.image_id=image_id;
        this.name=name;
        this.opiss=opiss;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

