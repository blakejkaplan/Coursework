package model;

import java.util.*;

/**
 * VariableDictionary object.
 */
public class VariableDictionary {

    private static double DEFAULT = 0;
    private Map<String, Double> myVariables;

    /**
     * Creates a VariableDictionary and instantiates the myVariables map.
     */
    public VariableDictionary() {
        myVariables = new HashMap<>();
    }

    /**
     * Makes a variable key/value pair.
     *
     * @param key:   string expression for the variable name.
     * @param value: current value of the variable.
     */
    public void makeVariable(String key, double value) {
        myVariables.put(key, value);
    }

    /**
     * Gets the value associated with the variable name. If the variable does not yet exist, creates it and initializes its value to 0.
     *
     * @param key: variable name.
     * @return variable value.
     */
    public double getNodeFor(String key) {
        if (!contains(key)) {
            myVariables.put(key, DEFAULT);
        }
        return myVariables.get(key);
    }

    /**
     * Checks if a variable of the given name already exists.
     *
     * @param key: variable name to check for.
     * @return true if the variable name already exists; false otherwise.
     */
    public boolean contains(String key) {
        return myVariables.containsKey(key);
    }

    /**
     * Gets the set of variable names that already exist.
     *
     * @return set of existing variable names.
     */
    public Set<String> getKeySet() {
        return myVariables.keySet();
    }

    /**
     * Replaces the variable map with new data.
     * @param newData
     */
    public void setVariableMap(Map<String, Double> newData){
        myVariables = newData;
    }

    /**
     * Creates a clone of the current variable dictionary.
     * @return clone of current variable dictionary.
     */
    public VariableDictionary createClone(){
        VariableDictionary newVarDict = new VariableDictionary();
        newVarDict.setVariableMap(new HashMap<>(myVariables));
        return newVarDict;
    }
}
