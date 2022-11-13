package pl.lodz.p.edu.rest.model;

import java.io.Serializable;

public enum idType implements Serializable {
    DowodOsobisty(0),
    Passport(1);

    private int id;

    idType(int id) {
        this.id = id;
    }

    public String toString() {
        switch(id) {
            case 0:
                return "Dowod Osobisty";

            case 1:
                return "Paszport";
        }
        return "";
    }
}