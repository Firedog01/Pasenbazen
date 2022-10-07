package model.EQ;

public class Lighting extends Equipment{
    private String brightness;

    public Lighting(double firstDayCost, double nextDaysCost, double bait, String name, boolean archive,
                    String description, int id, boolean missing, String brightness) {
        super(firstDayCost, nextDaysCost, bait, name, archive, description, id, missing);
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lighting{");
        sb.append("brightness='").append(brightness).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getBrightness() {
        return brightness;
    }
}
