import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import java.util.*;
import java.io.*;


public class Main {

    public static void main(String[] args) {
        try {
            //Datuak hartu
            String inPath="C:/Users/radio/Desktop/System/Uni/3/Erabakiak/1. Praktika Datuak-20240122/heart-c.arff";
            DataSource source = new DataSource(inPath);
            Instances data = source.getDataSet();
            //Emaitzak gorde
            String outPath="C:/Users/radio/Desktop/emaitzak.txt";
            FileWriter fileWriter = new FileWriter(outPath);

            //1-Aztertzen ari garen fitxategiko path-a
            fileWriter.write("1- Aztertzen ari garen fitxategiko path-a: " + inPath + "\n");

            //2-Instantzia kopurua
            fileWriter.write("2- Instantzia kopurua: " + data.numInstances() + "\n");

            //3-Atributu kopurua
            fileWriter.write("3- Atributu kopurua: " + data.numAttributes() + "\n");

            //4-Lehenengo atributuak har ditzakeen balio ezberdinak (balio ezberdin kopurua)
            fileWriter.write("4- Lehenengo atributuak har ditzakeen balio ezberdinak:" );
            Attribute firstAttribute = data.attribute(0);
            ArrayList<String> firstAtributeValues = new ArrayList<String>();
            int valueCount = 0;
            for (int i = 0; i < data.numInstances(); i++){
                if (!firstAtributeValues.contains(data.get(i).stringValue(0))){
                    firstAtributeValues.add(data.get(i).stringValue(0));
                    valueCount++;
                }
            }

            for (String value : firstAtributeValues) {
                fileWriter.write(value + "\n");
            }
            fileWriter.write(valueCount);


            //5-Azken atributuak hartzen dituen balioak eta beraien maiztasuna
            fileWriter.write("\n" + "5- Azken atributuak hartzen dituen balioak eta beraien maiztasuna:");
            Attribute lastAttribute = data.attribute(data.numAttributes() -1);

            HashMap<String, Integer> valueFrequencyMap = new HashMap<>();
            for (int i = 0; i < data.numInstances(); i++) {
                String value = data.instance(i).stringValue(lastAttribute);
                valueFrequencyMap.put(value, valueFrequencyMap.getOrDefault(value, 0) + 1);
            }
            for (String value : valueFrequencyMap.keySet()) {
                fileWriter.write("   - Balioa: " + value + ", Maiztasuna: " + valueFrequencyMap.get(value));
            }

            //6-Maiztasun txikien duen klasearen identifikatzailea eman
            fileWriter.write("\n" + "6- Maiztasun txikien duen klasearen identifikatzailea: " + getMinFrequencyClass(data));

            //7-Azken aurreko atributuak dituen missing value kopurua
            fileWriter.write("\n" +  "7- Azken aurreko atributuak dituen missing value kopurua: " + countMissingValues(data, data.numAttributes() - 2));

            //fileWriter itxi
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Maiztasun txikien duen klasearen identifikatzailea lortu
    private static String getMinFrequencyClass(Instances data) {
        Attribute classAttribute = data.attribute(data.numAttributes() - 1);
        String minFrequencyClass = null;
        int minFrequency = Integer.MAX_VALUE;

        HashMap<String, Integer> classFrequencyMap = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++) {
            String classValue = data.instance(i).stringValue(classAttribute);
            int frequency = classFrequencyMap.getOrDefault(classValue, 0) + 1;
            classFrequencyMap.put(classValue, frequency);
            if (frequency < minFrequency) {
                minFrequency = frequency;
                minFrequencyClass = classValue;
            }
        }
        return minFrequencyClass;
    }


    // atributu baten missing value kopurua kontatu
    private static int countMissingValues(Instances data, int attributeIndex) {
        int missingValues = 0;
        for (int i = 0; i < data.numInstances(); i++) {
            if (data.instance(i).isMissing(attributeIndex)) {
                missingValues++;
            }
        }
        return missingValues;
    }
}