public class Perceptron {
    private final double[] weights;
    private final double learningRate = 0.1;

    public Perceptron(int weightsLength) {
        weights = new double[weightsLength+1]; //+bias
        for (int i = 0; i < weightsLength; i++) {
            weights[i] = Math.random() ;
        }
        weights[weights.length-1]=1; //bias
//        for (int i = 0; i < weights.length; i++) {
//            System.out.println(weights[i]+" "+learningRate);
//        }
    }

    public int solveScalar(double[] input) {
        double sum = 0;
        for (int i = 0; i < weights.length-1; i++) {
            sum += input[i] * weights[i];
        }
        return sum >= 0 ? 1 : -1;
    }

    public void train(double[] inputs, int tag) {
        int answer = checkAnswer(inputs,tag);
//        System.out.println("error=" + answer);
        if (answer != 0) {
            for (int i = 0; i < weights.length-1; i++) {
                weights[i] += answer * inputs[i] * learningRate;
            }
            weights[weights.length-1]+=learningRate*answer; //bias

//            for (int i = 0; i < weights.length; i++) {
//                System.out.println(i + " " + weights[i] + " lr:"+ learningRate);
//            }
        }
    }
    public int checkAnswer(double[] inputs, int tag){
        return tag - solveScalar(inputs);
    }
}
