package model.EQ;

public class Lens extends Equipment{
    private String focalLength;

    public Lens(double firstDayCost, double nextDaysCost, double bail, String name,
                        int id, String focalLength) {
        super(firstDayCost, nextDaysCost, bail, name, id);
        this.focalLength = focalLength;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lens{");
        sb.append("focalLength='").append(focalLength).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getFocalLength() {
        return focalLength;
    }
}
