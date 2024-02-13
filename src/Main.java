import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.core.Attribute;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            // ---------------------------FITXATEGIAK------------------------------
            // Datuak hartu
            DataSource source = new DataSource(args[0]);
            Instances data = source.getDataSet();
            data.setClassIndex(data.numAttributes()-1);

            // Emaitzak gorde
            FileWriter fileWriter = new FileWriter(args[1]);

            // Klasea adierazi
            int classIndex = data.classIndex();
            Attribute classAttribute = data.attribute(classIndex);

            // ----------------------------------- [1,2,3,4,7] ----------------------------------
            fileWriter.write("1- Aztertzen ari garen fitxategiko path-a: " + args[0] + "\n");
            fileWriter.write("2- Instantzia kopurua: " + data.numInstances() + "\n");
            fileWriter.write("3- Atributu kopurua: " + data.numAttributes() + "\n");
            fileWriter.write("4- Lehenengo atributuak har ditzakeen balio ezberdinak:" + data.numDistinctValues(0));
            fileWriter.write("7- Azken aurreko atributuak dituen missing value kopurua: " + data.attributeStats(data.numAttributes()-2).missingCount + "\n");

            // ------------------------------------ [5,6] --------------------------------------
            fileWriter.write("\n" + "5- Azken atributuak hartzen dituen balioak eta beraien maiztasuna:");
            int minFrequency = Integer.MAX_VALUE;
            int minIndex = -1;
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
            fileWriter.write("\n" + "6- Maiztasun txikien duen klasearen identifikatzailea: " + classAttribute.value(minIndex));

            // fileWriter itxi
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}