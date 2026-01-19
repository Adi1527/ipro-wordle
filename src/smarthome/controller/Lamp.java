package smarthome.controller;

/**
 * Eine smarte Lampe. Enthält als Eigenschaften einen Namen und
 * einen Stromverbrauch (in Watt). Der Zustand einer Lampe
 * besteht aus einem boolean für ein/aus und einer Helligkeits-
 * einstellung. Die Helligkeit kann unabhängig vom ein/aus-Zustand
 * verändert werden.
 */
public class Lamp {
    private final String name;
    private final double powerConsumption; // Watt

    private volatile boolean on = true;
    private volatile double brightness = 1.0;

    Lamp(String name, double powerConsumption) {
        this.name = name;
        this.powerConsumption = powerConsumption;
    }

    public String getName() {
        return name;
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }

    public boolean isOn() {
        return on;
    }

    public double getBrightness() {
        return brightness;
    }

    void toggle(){
        setOn(!on);
    }

    void setBrightness(double brightness) {
        this.brightness = clamp(brightness);
    }

    private double clamp(double value) {
        if (value < 0.0) {
            return 0.0;
        } else {
            return Math.min(value, 1.0);
        }
    }

    private void setOn(boolean on){
        this.on = on;
    }
}
