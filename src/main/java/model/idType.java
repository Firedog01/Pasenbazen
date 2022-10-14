package model;

public enum idType {
    DowodOsobisty(0),
    Passport(1);

    private int id;

    idType(int id) {
        this.id = id;
    }
}