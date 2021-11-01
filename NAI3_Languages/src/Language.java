import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Language {
    private String name;
    private ArrayList<String> data;

    Language(String name) {
        this.name = name;
    }

    Language() {
        this.data = new ArrayList<>();
    }

    void setData(ArrayList<String> data) {
        this.data = data;
    }

    Language addData(String data) {
        this.data.add(data);
        return this;
    }

    int getDataSize(){
        return data.size();
    }

    HashMap<Character, Double> getPercentage() {
        HashMap<Character, Double> result = new HashMap<>();
        StringBuilder data = new StringBuilder();
        this.data.forEach(data::append);
        ArrayList<Character> symbols = new ArrayList<>();
        for (char symbol : data.toString().toCharArray())
            symbols.add(symbol);
        for (char i = 97; i < 123; i++) {
            result.put(i,
                    Collections.frequency(symbols, i) / (double) symbols.size());
        }
        return result;
    }

    public String getName() {
        return name;
    }

//    @Override
//    public String toString() {
//        return "Language: " + name + "\nTexts: \n" + data + "\n--------------------------";
//    }
}
