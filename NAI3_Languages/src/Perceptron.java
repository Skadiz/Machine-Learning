import java.util.List;

public class Perceptron {
    double[] weights;
    double learningRate;
    String result;

    Perceptron(String result, int weightsLength) {
        weights = new double[weightsLength+1];
        for (int i = 0; i < weightsLength; i++) {
            weights[i] = Math.random();
        }
        this.learningRate = 0.1;
        this.result = result;
        weights[weights.length-1]=1; //bias
    }

    double solveScalar(Language language) {
        double res = 0;
        Double[] xs = language.getPercentage().values().toArray(new Double[26]);
        for (int j = 0; j < xs.length; j++) {
            res += xs[j] * weights[j];
        }
        return res;
    }

    void train(List<Language> languages) {
        while (true) {
            //for (int i = 0; i < 1000; i++) {
            for (Language language : languages) {
                Double[] xs = language.getPercentage().values().toArray(new Double[26]);
                double scalar = solveScalar(language);
                double tag = language.getName().equals(result) ? 1 : 0;
                if (tag - scalar >= 0 && tag - scalar <= 0.01) {
                    System.out.println(result + " trained!");
                    return;
                }
                for (int j = 0; j < weights.length-1; j++) {
                    weights[j] += learningRate * (tag - scalar) * xs[j];
                }
                weights[weights.length-1]+= learningRate *(tag-scalar); //bias
            }
        }
    }
}
