package model;

public enum IdType {
    DowodOsobisty(0),
    Passport(1);

    private int id;

    IdType(int id) {
        this.id = id;
    }

    public String toString() {
        switch(id) {
            case 0 -> {
                return "Dowod Osobisty";
            }
            case 1 -> {
                return "Paszport";
            }
        }
        return "";
    }
}