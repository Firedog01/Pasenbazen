package model.EQ;

public class Lighting extends Equipment{
    private String brightness;

    public Lighting(double firstDayCost, double nextDaysCost, double bail, String name,
                    int id, String brightness) {
        super(firstDayCost, nextDaysCost, bail, name, id);
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
