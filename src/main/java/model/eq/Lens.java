package model.eq;

public class Lens extends Equipment{
    private String focalLength;

    public Lens(double firstDayCost, double nextDaysCost, double bait, String name, boolean archive,
                        String description, int id, boolean missing, String focalLength) {
        super(firstDayCost, nextDaysCost, bait, name, archive, description, id, missing);
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
