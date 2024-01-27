import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        try {
            // datuak kargatu
            DataSource source = new DataSource("C:/Users/radio/Desktop/System/Uni/3/Erabakiak/1. Praktika Datuak-20240122/heart-c.arff");
            Instances data = source.getDataSet();

            // 1. Aztertzen ari garen fitxategiko path-a
            System.out.println("1- Aztertzen ari garen fitxategiko path-a: " + source.toString());

            // 2. Instantzia kopurua
            System.out.println("2- Instantzia kopurua: " + data.numInstances());

            // 3. Atributu kopurua
            System.out.println("3- Atributu kopurua: " + data.numAttributes());

            // 4. Lehenengo atributuak har ditzakeen balio ezberdinak (balio ezberdin kopurua)
            System.out.println("4- Lehenengo atributuak har ditzakeen balio ezberdinak:");
            Enumeration<Attribute> attributes = data.enumerateAttributes();
            while (attributes.hasMoreElements()) {
                Attribute attribute = attributes.nextElement();
                if (attribute.index() == 0) continue; // Saltar el identificador de la instancia
                System.out.println("   - " + attribute.name() + ": " + attribute.numValues());
            }

            // 5. Azken atributuak hartzen dituen balioak eta beraien maiztasuna
            System.out.println("5- Azken atributuak hartzen dituen balioak eta beraien maiztasuna:");

            Attribute lastAttribute = data.attribute(data.numAttributes() - 1);
            HashMap<String, Integer> valueFrequencyMap = new HashMap<>();
            for (int i = 0; i < data.numInstances(); i++) {
                String value = data.instance(i).stringValue(lastAttribute);
                valueFrequencyMap.put(value, valueFrequencyMap.getOrDefault(value, 0) + 1);
            }
            for (String value : valueFrequencyMap.keySet()) {
                System.out.println("   - Balioa: " + value + ", Maiztasuna: " + valueFrequencyMap.get(value));
            }

            // 6. Maiztasun txikien duen klasearen identifikatzailea eman
            System.out.println("6- Maiztasun txikien duen klasearen identifikatzailea: " + getMinFrequencyClass(data));

            // 7. Azken aurreko atributuak dituen missing value kopurua
            System.out.println("7- Azken aurreko atributuak dituen missing value kopurua: " + countMissingValues(data, data.numAttributes() - 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Maiztasun txikien duen klasearen identifikatzailea lortu
    private static String getMinFrequencyClass(Instances data) {
        Attribute classAttribute = data.attribute(data.numAttributes() - 1);

        HashMap<String, Integer> classFrequencyMap = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++) {
            String classValue = data.instance(i).stringValue(classAttribute);
            classFrequencyMap.put(classValue, classFrequencyMap.getOrDefault(classValue, 0) + 1);
        }

        String minFrequencyClass = null;
        int minFrequency = Integer.MAX_VALUE;

        for (String value : classFrequencyMap.keySet()) {
            int frequency = classFrequencyMap.get(value);
            if (frequency < minFrequency) {
                minFrequency = frequency;
                minFrequencyClass = value;
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