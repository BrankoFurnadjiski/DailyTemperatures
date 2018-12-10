package DailyTemperatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Temperature {

    private int day;
    private List<Double> temperatures;
    private char scale;

    public Temperature(String row){
        String[] parts = row.split(" ");
        this.day = Integer.parseInt(parts[0]);

        this.scale = parts[1].charAt(parts[1].length()-1);

        /*
        List e abstraktna klasa pa ne moze od nea da pravime remove
        zatoa listata so Arrays.asList() dobiena se praka kako argument
        na new ArrayList<>() koja posle moze da se manipulira

        ArrayList<String> temps = new ArrayList<>(Arrays.asList(parts));
        temps.remove(0);

        this.temperatures = temps.stream()
                                    .map( temp -> Double.parseDouble(temp.substring(0,temp.length()-1)))
                                    .collect(Collectors.toList());
                                    */


        this.temperatures = IntStream.range(1, parts.length)
                                        .mapToObj( index -> parts[index])
                                        .map( temp -> Double.parseDouble(temp.substring(0,temp.length()-1)))
                                        .collect(Collectors.toList());
    }

    private String toStringF() {
        return String.format("%3d: Count:%3d Min:%6.2fF Max:%6.2fF Average:%6.2fF\n", day, count(), min(), max(), average());
    }

    private String toStringC() {
        return String.format("%3d: Count:%3d Min:%6.2fC Max:%6.2fC Average:%6.2fC\n", day, count(), min(), max(), average());
    }

    public String toString(char scale){
        if(scale == 'F'){
            if(this.scale != scale){
                convertToF();
            }
            return toStringF();
        } else {
            if(this.scale != scale){
                convertToC();
            }
            return toStringC();
        }
    }

    private void convertToF() {
        this.scale = 'F';
        this.temperatures = temperatures.stream()
                                        .map( value -> value.doubleValue()*9/5 + 32)
                                        .collect(Collectors.toList());
    }

    private void convertToC() {
        this.scale = 'C';
        this.temperatures = temperatures.stream()
                                        .map( value -> (value.doubleValue()-32)*5/9)
                                        .collect(Collectors.toList());
    }

    private int count(){
        return temperatures.size();
    }

    private Double min(){
        return temperatures.stream()
                            .mapToDouble(value -> value.doubleValue())
                            .min().orElse(0);
    }

    private Double max() {
        return temperatures.stream()
                            .mapToDouble(value -> value.doubleValue())
                            .max().orElse(0);
    }

    private Double average() {
        return temperatures.stream()
                            // .mapToDouble(Double::doubleValue)
                            .mapToDouble(value -> value.doubleValue())
                            .sum() / count();
    }

    public int getDay() {
        return this.day;
    }

}

