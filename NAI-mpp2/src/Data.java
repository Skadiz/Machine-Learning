public class Data {
    private final String klasyfikacja;
    private double[] numbers;

    public Data(String klasyfikacja, double[] numbers) {
        this.klasyfikacja = klasyfikacja;
        this.numbers = numbers;
    }

    public String getKlasyfikacja() {
        return klasyfikacja;
    }

    public double[] getNumbers() {
        return numbers;
    }
}
