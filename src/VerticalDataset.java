package solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Utility class to manage a dataset stored in a external file. Provides a vertical representation.
 * Tip: it is probably better to use this if you want to implement a depth first search algorithm.
 *
 * @author Charles Thomas (charles.thomas@uclouvain.be)
 */
public class VerticalDataset {
    private final int[] items; //The different items in the dataset
    private final Map<Integer, Set<Integer>> itemMap; //A map of the transactions in the dataset by item.
    private int nTransactions;

    /**
     * Constructor: reads the dataset and initialises fields.
     * @param filePath the path to the dataset file. It is assumed to have the following format:
     *                 Each line corresponds to a transaction. Blank lines might be present and will be ignored.
     *                 Items in a transaction are represented by integers separated by single spaces.
     */
    public VerticalDataset(String filePath) {


        itemMap = new HashMap<>();
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.ready()) {
                String line = reader.readLine();
                if(line.matches("^\\s*$")) continue; //Skipping blank lines
                final Integer transaction = i;
                Stream.of(line.trim().split(" ")).mapToInt(Integer::parseInt).forEach(item -> {
                    Set<Integer> transactions = itemMap.getOrDefault(item, new HashSet<>());
                    transactions.add(transaction);
                    if (!itemMap.containsKey(item)) itemMap.put(item, transactions);
                });
                i++;
            }
            reader.close();
        }
        catch (IOException e) {
            System.err.println("Unable to read dataset file!");
            e.printStackTrace();
        }
        nTransactions = i;
        items = itemMap.keySet().stream().mapToInt(Number::intValue).toArray();
        Arrays.sort(items);
    }

    /**
     * Returns the number of transactions in the dataset.
     */
    public int transNum() {
        return nTransactions;
    }

    /**
     * Returns the number of different items in the dataset.
     */
    public int itemsNum() {
        return items.length;
    }

    /**
     * Returns an array of all the different items in the dataset
     */
    public int[] items(){
        return items;
    }

    /**
     * Returns the transactions contained in the dataset as an ArrayList of int arrays.
     */
    public Map<Integer, Set<Integer>> getItemMap() {
        return itemMap;
    }

    /**
     * Returns the transactions for an item as a set.
     */
    public Set<Integer> getTransactions(int item) {
        return itemMap.get(item);
    }
}
