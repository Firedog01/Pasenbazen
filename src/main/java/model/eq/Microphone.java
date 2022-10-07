package model.eq;

public class Microphone extends Equipment{
    private String brightness;

    public Microphone(double firstDayCost, double nextDaysCost, double bait, String name, boolean archive,
                            String description, int id, boolean missing, String brightness) {
        super(firstDayCost, nextDaysCost, bait, name, archive, description, id, missing);
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Microphone{");
        sb.append("brightness='").append(brightness).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getBrightness() {
        return brightness;
    }
}
