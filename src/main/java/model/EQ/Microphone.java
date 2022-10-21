package model.EQ;

import exception.EquipmentException;

public class Microphone extends Equipment{

    private String sensitivity;

    public Microphone(double firstDayCost,
                      double nextDaysCost,
                      double bail,
                      String name,
                      String sensitivity
    ) throws EquipmentException {
        super(firstDayCost, nextDaysCost, bail, name);
        this.sensitivity = sensitivity;
    }
    protected Microphone() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Microphone{");
        sb.append("brightness='").append(sensitivity).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String brightness) {
        this.sensitivity = brightness;
    }
}
