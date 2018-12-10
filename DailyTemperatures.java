package DailyTemperatures;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DailyTemperatures {

    private List<Temperature> temperatures;

    public DailyTemperatures(){
        temperatures = new ArrayList<Temperature>();
    }

    void readTemperatures(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        temperatures = bufferedReader.lines()
                                    .map(line -> new Temperature(line))
                                    .collect(Collectors.toList());

    }

    void writeDailyStats(OutputStream outputStream, char scale){
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        temperatures.stream()
                .sorted((temp1,temp2) -> temp1.getDay() > temp2.getDay() ? 1 : -1)
                .forEach(temperature -> {
                    try {
                        bufferedWriter.write(temperature.toString(scale));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        try {
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
