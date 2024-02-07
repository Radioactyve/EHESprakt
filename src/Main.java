import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Datuak hartu
            String inPath = "C:/Users/radio/Desktop/System/Uni/3/Erabakiak/1. Praktika Datuak-20240122/heart-c.arff";
            DataSource source = new DataSource(inPath);
            Instances data = source.getDataSet();
            // Emaitzak gorde
            String outPath = "C:/Users/radio/Desktop/emaitzak.txt";
            FileWriter fileWriter = new FileWriter(outPath);

            // Klasea adierazi
            data.setClassIndex(data.numAttributes()-1);
            int classIndex = data.classIndex();

            // 1-Aztertzen ari garen fitxategiko path-a
            fileWriter.write("1- Aztertzen ari garen fitxategiko path-a: " + inPath + "\n");

            // 2-Instantzia kopurua
            fileWriter.write("2- Instantzia kopurua: " + data.numInstances() + "\n");

            // 3-Atributu kopurua
            fileWriter.write("3- Atributu kopurua: " + data.numAttributes() + "\n");

            // 4-Lehenengo atributuak har ditzakeen balio ezberdinak (balio ezberdin kopurua)
            fileWriter.write("4- Lehenengo atributuak har ditzakeen balio ezberdinak (v2):" + data.attributeStats(0).distinctCount);
            fileWriter.write("4- Lehenengo atributuak har ditzakeen balio ezberdinak:" + data.numDistinctValues(0));

            // 5-Azken atributuak hartzen dituen balioak eta beraien maiztasuna
            Attribute classAttribute = data.attribute(classIndex);
            int minFrequency = Integer.MAX_VALUE;
            int minIndex = -1;
            fileWriter.write("\n" + "5- Azken atributuak hartzen dituen balioak eta beraien maiztasuna:");
            for (int i = 0; i < data.numInstances(); i++) {
                //"i" instantziak errekorritu
                String value = classAttribute.value(i);
                int frekuentzia = data.attributeStats(classIndex).nominalCounts[i];
                fileWriter.write("Balioa: "+ value + ", eta frekuentzia: " + frekuentzia + " \n");
                //maiztasun txikiena duen klasea gorde
                if (frekuentzia < minFrequency){
                    minFrequency = frekuentzia;
                    minIndex = i;
                }
            }

            // 6-Maiztasun txikien duen klasearen identifikatzailea eman
            fileWriter.write("\n" + "6- Maiztasun txikien duen klasearen identifikatzailea: " + classAttribute.value(minIndex));

            // 7-Azken aurreko atributuak dituen missing value kopurua
            fileWriter.write("\n" + "7- Azken aurreko atributuak dituen missing value kopurua: " + data.attributeStats(data.numAttributes()-2).missingCount);

            // fileWriter itxi
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}