package com.katt.climateclock.climateclock;

/**
 * Created by Thomas on 4/12/14. A class that can generate a sound
 * file by layering sounds. It also fetches the weather.
 */
public class SoundGenerator {
    /*
     * Enum for storing different weather types.
     */
    public enum Weather{SUNNY, RAIN, SNOW, CLOUDY};
    
    /*
     * Fetch the weather.
     * @return A Weather that corresponds to the best match for
     * weather.
     */
    public Weather fetchWeather()
    {
	return Weather.SUNNY;
    }

    /*
     * Get the settings and update the configuration.
     */
    public void updateSettings()
    {

    }

    /*
     * Generate the sound.
     */
    public void generate()
    {

    }

    /*
     * Write the generated sound to file.
     * @param String representing the output path.
     */
    public void write(String path)
    {

    }

    /*
     * Layer two sounds.
     * @param The first sound.
     * @param The second sound.
     * @param The time.
     * @return The layered sound.
     */
    private String layerSounds(String sound1, String sound2, int time)
    {
	return "Hello world";
    }
}
