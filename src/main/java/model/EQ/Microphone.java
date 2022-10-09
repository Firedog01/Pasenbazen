package model.EQ;

public class Microphone extends Equipment{
    private String brightness;

    public Microphone(double firstDayCost, double nextDaysCost, double bail, String name,
                            int id, String brightness) {
        super(firstDayCost, nextDaysCost, bail, name, id);
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

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }
}
