package model.EQ;

import exception.EquipmentException;

public class Camera extends Equipment {

    private String resolution;

    public Camera(double firstDayCost,
                  double nextDaysCost,
                  double bail,
                  String name,
                  String resolution
    ) throws EquipmentException {
        super(firstDayCost, nextDaysCost, bail, name);
        this.resolution = resolution;
    }

    protected Camera() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Camera{");
        sb.append("resolution='").append(resolution).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
