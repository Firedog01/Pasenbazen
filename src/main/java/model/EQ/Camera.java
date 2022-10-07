package model.EQ;

public class Camera extends Equipment {
    private String resolution;

    public Camera(double firstDayCost, double nextDaysCost, double bail, String name, int id, String resolution) {
        super(firstDayCost, nextDaysCost, bail, name, id);
        this.resolution = resolution;
    }

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
}
