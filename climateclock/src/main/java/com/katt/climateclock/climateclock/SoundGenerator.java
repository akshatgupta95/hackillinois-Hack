package com.katt.climateclock.climateclock;
import android.provider.Settings;

import it.octograve.weatherlib.*;
import java.lang.*;


/**
 * Created by Thomas on 4/12/14. A class that can generate a sound
 * file by layering sounds. It also fetches the weather.
 */
public class SoundGenerator {

    /*
     * Declaring a station so that it can be used throughout the class
     */
    public Station station;
    /*
    * Default constructor to initialize the current station
     */
    public SoundGenerator(String location){
        StationsList list = null;
        try{
            list = StationsList.fetchStationsList();
        }
        catch(WeatherException e){
            System.err.println("Unable to fetch list");
        }

        station = list.getByLocation(location);
        if(station == null){
            System.err.println("Could not get the the station");
        }
    }
    /*
     * Enum for storing different weather types.
     */
    public enum WeatherTypes{SUNNY, RAIN, SNOW, CLOUDY, STORMY};

    /*
     * Keeps track of whether we should put wind on the sound file.
     */
    boolean isWindy = false;
    
    /*
     * Fetch the weather.
     * @return A Weather that corresponds to the best match for
     * weather.
     */
    public WeatherTypes getWeather()
    {

        try{
            station.updateWeather();
        }
        catch(WeatherException e){
            System.err.println("Could not get weather");
            return null;
        }

        WeatherSummary currWeather = station.getWeather().summary();
        if(station.getWeather().getWindMaxSpeed() > 10){
            isWindy = true;
        }
        if(currWeather == WeatherSummary.RAINY){
            return WeatherTypes.RAIN;
        }
        if(currWeather == WeatherSummary.STORMY){
           return WeatherTypes.STORMY   ;
        }
        if(currWeather == WeatherSummary.CLOUDY ||
                currWeather == WeatherSummary.FEW_CLOUDS ||
                currWeather == WeatherSummary.OVERCAST){
            return WeatherTypes.CLOUDY;
        }
        if (currWeather == WeatherSummary.SNOWY || currWeather == WeatherSummary.ICY ){
            return WeatherTypes.SNOW;
        }
        if (currWeather == WeatherSummary.SUNNY ||
                currWeather == WeatherSummary.NOT_AVAILABLE ||
             currWeather == WeatherSummary.WINDY){
            return WeatherTypes.SUNNY;
        }

        return null;
    }



    public float getTemp(){
        return station.getWeather().getTemperature();
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
