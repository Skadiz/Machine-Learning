import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Data> train_set = new ArrayList<>();
        Scanner sc = new Scanner(new File("train-set.txt"));
        readData(sc, train_set);

        List<Data> test_set = new ArrayList<>();
        sc = new Scanner(new File("test-set.txt"));
        readData(sc, test_set);

        Perceptron perceptron = new Perceptron(train_set.get(0).getNumbers().length);

        int studyCount = 100;
        for (int i = 0; i < studyCount; i++) {
            for (Data trainingIris : train_set) {
                int tag = trainingIris.getKlasyfikacja().equals("Iris-setosa") ? 1 : -1;
                perceptron.train(trainingIris.getNumbers(), tag);
            }
        }



        int goodSolve = 0;
        int numberSetosa = 0;
        for (Data testIris : test_set) {
//            System.out.println(testIris.getKlasyfikacja());
            if (testIris.getKlasyfikacja().equals("Iris-setosa")) {
                numberSetosa++;
                if (perceptron.solveScalar(testIris.getNumbers()) == 1) {
                    goodSolve++;
                }
            }
        }

        System.out.println("Prawidłowo zaklasyfikowane przypadki: " + goodSolve + "/" + numberSetosa + "\n");

        System.out.println("Wprowadź dane dla klasyfikacji\n" +
                "(" + train_set.get(0).getNumbers().length + " liczby oddzielone spacją)\n" +
                "jeśli chcesz zakończyć wpisz 'Yes' :");
        sc = new Scanner(System.in);
        String line2;
        while (!(line2 = sc.nextLine()).equals("Yes")) {
            String[] data = line2.replaceAll(",", ".").trim().split("\\s+");
            if (data.length != train_set.get(0).getNumbers().length) {
                throw new IllegalArgumentException("Nieprawidłowa liczba wprowadzonych danych (musi być =" + train_set.get(0).getNumbers().length + ")");
            }
            double[] numbers = new double[data.length];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Double.parseDouble(data[i]);
            }
            if (perceptron.solveScalar(numbers) == 1) {
                System.out.println("Iris-setosa | etykieta równa się +1");
            } else {
                System.out.println("Inny | etykieta równa się -1");
            }
        }
    }

    private static void readData(Scanner sc, List<Data> inputData) {
        while (sc.hasNextLine()) {
            String[] data = sc.nextLine().replaceAll(",", ".").trim().split("\\s+");
            String klasyfikacja = data[data.length - 1];
            double[] numbers = new double[data.length - 1];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Double.parseDouble(data[i]);
            }
            inputData.add(new Data(klasyfikacja, numbers));
        }
    }


}
