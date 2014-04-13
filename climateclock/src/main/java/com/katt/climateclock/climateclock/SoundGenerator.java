package com.katt.climateclock.climateclock;
import android.provider.Settings;

import it.octograve.weatherlib.*;

import java.io.File;
import java.lang.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import android.net.Uri;

import de.jartist.weather.wunderground.api.domain.DataSet;
import de.jartist.weather.wunderground.api.domain.WeatherStation;
import de.jartist.weather.wunderground.api.domain.WeatherStations;
import de.jartist.weather.wunderground.impl.services.HttpDataReaderService;


/**
 * Created by Thomas on 4/12/14. A class that can generate a sound
 * file by layering sounds. It also fetches the weather.
 */
public class SoundGenerator {
    private class Sounds{
        String[] rain = {"rain", "snow"};
        String[] bird = {"birds", "birds2", "birds3", "birds4"};
    }
    /*
     * Holds all the sound bindings.
     */
    Sounds soundResources = new Sounds();

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

    public SoundGenerator(){
        StationsList list = null;

        station = null;
    }

    /*
     * Keeps track of whether we should put wind on the sound file.
     */
    boolean isWindy = false;
    boolean isRainy = false;
    boolean isSunny = false;
    
    /*
     * Fetch the weather.
     * @return A Weather that corresponds to the best match for
     * weather.
     */
    public void getWeather()
    {


        WeatherStation aWeatherStation = new WeatherStation("KILURBAN8");

        HttpDataReaderService dataReader = new HttpDataReaderService();
        dataReader.setWeatherStation(aWeatherStation );

        Double currentTemperature = dataReader.getCurrentData().getTemperature();

        Double windspeed = dataReader.getCurrentData().getWindspeedAvgKmh();

        Double rain = dataReader.getCurrentData().getRainRateHourlyMm();

        if(windspeed >= 10){

            isWindy = true;

        }

        if(rain > 1 ) {

            isRainy = true;

        }

        if(rain == 0) {

            isSunny = true;

        }



    }

    /*
     * Chooses a random sound file from a folder.
     * @param A string representing the path to the directory.
     * @Return A string representing the path to the specific music file.
     */
    private String randomSoundFromDirectory(String[] resources)
    {
        Random randomGenerator = new Random();
        return resources[randomGenerator.nextInt(resources.length)];
    }

    public float getTemp(){
        return station.getWeather().getTemperature();
    }

    /*
     * Get the settings and update the configuration.
     */
    public void updateSettings()
    {
       //TO be added
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

   

    /*
     * @param WeatherType
     * @return String of the path of the sound file
     */
    private Uri soundPath(WeatherTypes weatherCondition){
        if(weatherCondition == WeatherTypes.RAIN){
            return Uri.parse(String.format("android.resource://com.katt.climateclock.climateclock/raw/%s",
                    randomSoundFromDirectory(soundResources.rain)));
        }
        return Uri.parse(String.format("android.resource://com.katt.climateclock.climateclock/raw/%s",
                randomSoundFromDirectory(soundResources.rain)));
    }

    /*
     * Return the path to the sound file created by the mixer. Right now it only returns the
     * weather path.
     * @return A string to the path.
     */
    public Uri getSoundPath(){
        WeatherTypes currWeatherType = getWeather();
        return soundPath(currWeatherType);
    }

    public Uri getRainPath(){
        WeatherTypes currWeatherType = WeatherTypes.RAIN;
        return soundPath(currWeatherType);
    }
}
