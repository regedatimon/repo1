import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by regeda on 23.03.2016.
 */
public class TestClass {

    /**
     * Parameters to perform input and output data
     */
    private final static HashMap<Integer, ArrayList<Integer>> graph = new HashMap();
    private final static ArrayList<ArrayList<Integer>> cyclicTrails = new ArrayList();

    /**
     * Perform travers of graph from each vertex
     */
    private static void traverse() {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : graph.entrySet()) {
            ArrayList<Integer> res = new ArrayList();
            res.add(entry.getKey());
            buildTrail(entry.getKey(), entry.getValue(), res);
        }
    }

    /**
     * Add unique trail to list of cyclic trails
     *
     * @param trail trail to add
     */
    private static void addUniqueTrail(ArrayList<Integer> trail) {
        for (ArrayList<Integer> tr : cyclicTrails) {
            if (tr.containsAll(trail) && tr.size() == trail.size())
                return;
        }
        cyclicTrails.add(trail);
    }

    /**
     * Prints all discovered cyclicTrails to STDOUT
     */
    private static void printCyclicTrials() {
        for (ArrayList<Integer> tr : cyclicTrails) {
            String s = "";
            for (Integer i : tr) s += i + " ";
            System.out.println(s);
        }
    }

    /**
     * Perform recursive traverse of graph with persistance of cyclic dependencies
     *
     * @param root  start vertex
     * @param deps  list of dependent vertex
     * @param trail current trail
     */
    private static void buildTrail(Integer root, ArrayList<Integer> deps, ArrayList<Integer> trail) {
        if (deps == null) {
            return;
        }
        for (Integer dep : deps) {
            if (dep.equals(root)) {
                trail.add(dep);
                addUniqueTrail(trail);
                return;
            }
            if (!trail.contains(dep)) {
                trail.add(dep);
                ArrayList<Integer> localtrail = new ArrayList<Integer>(trail);
                buildTrail(root, graph.get(dep), localtrail);
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        try {
            while ((s = in.readLine()) != null && s.length() != 0) {
                if (s.matches("\\d+\\s\\d+")) {
                    String[] entry = s.split("\\s", 2);
                    if (graph.containsKey(Integer.valueOf(entry[0])))
                        graph.get(Integer.valueOf(entry[0])).add(Integer.valueOf(entry[1]));
                    else {
                        ArrayList<Integer> next = new ArrayList();
                        next.add(Integer.valueOf(entry[1]));
                        graph.put(Integer.valueOf(entry[0]), next);
                    }
                } else
                    System.out.println("Input doesn't match");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        traverse();
        printCyclicTrials();
    }
}
