package uk.me.paulriley.railwaychildren;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final String FILENAME = "Railway-Children-by-E-Nesbit.txt";

    public static void main(String[] args) {
        BufferedReader bufferedreader = null;
        FileReader fileReader = null;

        FrequencyScanner frequencyScanner = new FrequencyScanner();

        try {
            fileReader = new FileReader(FILENAME);
            bufferedreader = new BufferedReader(fileReader);

            String readLine;

            while ((readLine = bufferedreader.readLine()) != null) {
                frequencyScanner.scanLine(readLine);
            }

            System.out.print(frequencyScanner.displayFrequencyList());
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (bufferedreader != null) {
                    bufferedreader.close();
                }

                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
