import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        ArrayList<Language> languages = new ArrayList<>();
        int numberOftexts = (Objects.requireNonNull(new File("data/Train-set/language1").listFiles()).length) - 1;
        int numberOfLanguages = (Objects.requireNonNull(new File("data/Train-set").listFiles()).length);

        System.out.println("Training:" + "\n");

        for (int i = 1; i <= numberOfLanguages; i++) {
            reader = new BufferedReader(new FileReader("data/Train-set/language" + i + "/name.txt"));
            String languageName = reader.readLine();
            Language language = new Language(languageName);
            ArrayList<String> data = new ArrayList<>();
            for (int j = 0; j < numberOftexts; j++) {
                reader = new BufferedReader(new FileReader("data/Train-set/language" + i + "/text" + j + ".txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    data.add(line.replaceAll("[^a-zA-Z]", ""));
                }
            }
            language.setData(data);
            languages.add(language);
        }

        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        languages.forEach(language -> {
            Perceptron perceptron = new Perceptron(language.getName(), 26);
            perceptron.train(languages);
            perceptrons.add(perceptron);
        });

        System.out.println("\n"+"Testing: "+"\n");
        //TESTOWANIE

        BufferedReader readerTEST;
        int numberOfTests = (Objects.requireNonNull(new File("data/Test-set").listFiles()).length);
        int goodSolve=0;
        for (int i = 0; i < numberOfTests; i++) {
            readerTEST = new BufferedReader(new FileReader("data/Test-set/test" + i + "/name.txt"));
            String languageTestName = readerTEST.readLine();
            Language testLanguage = new Language(languageTestName);
            ArrayList<String> testData = new ArrayList<>();
            for (int j = 0; j < numberOfTests; j++) {
                readerTEST = new BufferedReader(new FileReader("data/Test-set/test" + i + "/test.txt"));
                String testLine;
                while ((testLine = readerTEST.readLine()) != null) {
                    testData.add(testLine.replaceAll("[^a-zA-Z]", ""));
                }
            }

            testLanguage.setData(testData);
            languages.add(testLanguage);
            AtomicReference<Perceptron> max = new AtomicReference<>();
            perceptrons.forEach(perceptron -> {
                StringBuilder data = new StringBuilder();
                for (String testDatum : testData) {
                    data.append(testDatum);
                }
                double res = perceptron.solveScalar(new Language().addData(data.toString()));
                if (max.get() == null) max.set(perceptron);
                if (res > max.get().solveScalar(new Language().addData(data.toString()))) max.set(perceptron);
            });
//            System.out.println(max.get().result + " "+ languageTestName);
            String isSuccessful = max.get().result.equals(languageTestName) ? "Successful" : "Not successful";
            if (isSuccessful.equals("Successful"))
                goodSolve +=1;
            System.out.println("Test №"+i+" result: "+isSuccessful);
        }
        //==============================
        System.out.println("\n"+"Correctly classified cases: " + goodSolve + "/" + numberOfTests + "\n");
        GUI.perceptrons = perceptrons;
        GUI.launch(args);
    }
}
