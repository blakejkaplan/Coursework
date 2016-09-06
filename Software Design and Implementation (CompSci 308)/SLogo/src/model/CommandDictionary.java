package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandDictionary {

    private Map<String, Command> myCommands;
    private Map<String, Integer> myNumArguments;
    private Map<String, String> myCommandText;

    public CommandDictionary() {
        myCommands = new HashMap<>();
        myNumArguments = new HashMap<>();
        myCommandText = new HashMap<>();
    }

    /**
     * Creates a new command with the given command name.
     *
     * @param key:        command name.
     * @param newCommand: command object representing the command.
     */
    public void createCommand(String key, Command newCommand) {
        if (!myCommands.containsKey(key)) {
            myCommands.put(key, newCommand);
        } else {
            myCommands.remove(key);
            myCommands.put(key, newCommand);
        }
    }
    
    /**
     * Creates a placeholder entry for a command that is defined but not yet made.
     * @param key: name of command.
     * @param numParams: number of parameters.
     */
    public void createPlaceholderCommand(String key, int numParams) {
    	Command placeHolder = new Command(key);
    	myNumArguments.put(key, numParams);
    	myCommands.put(key, placeHolder);
    }

    /**
     * Gets the command associated with a given command name.
     *
     * @param key: command name.
     * @return command object for the given command name.
     */
    public Command getCommandFor(String key) {
        Command commandToGet = myCommands.get(key);
        Command ret = new Command(key);
        for (int i = 0; i < commandToGet.getParams().size(); i++) {
            ret.addParam(commandToGet.getParams().get(i));
        }
        ret.setProcedure(commandToGet.getProcedure());
        return ret;
    }


    /**
     * Stores the text that created the given command.
     * @param key: command name.
     * @param commandText: text that created the given command.
     */
    public void storeCommandText(String key, String commandText) {
        myCommandText.put(key, commandText);
    }

    /**
     * Checks if a command of the given name already exists.
     *
     * @param key: command name to check.
     * @return true if command name already exists; false otherwise.
     */
    public boolean contains(String key) {
        return myCommands.containsKey(key);
    }

    /**
     * Sets the number of arguments required for a given command.
     * @param key: command name.
     * @param numArgs: number of arguments required.
     */
    public void setNumArguments(String key, int numArgs) {
        myNumArguments.put(key, numArgs);
    }

    /**
     * Gets the number of arguments required for a given command.
     * @param key: command name.
     * @return number of arguments required.
     */
    public int getNumArgsForkey(String key) {
        return myNumArguments.get(key);
    }

    /**
     * Gets the text used to create the command.
     * @param key: command name.
     * @return the String used to create the command.
     */
    public String getCommandTextForKey(String key) { return myCommandText.get(key); }

    /**
     * Gets the set of command names already defined.
     *
     * @return existing set of command names.
     */
    public Set<String> getCommandKeySet() {
        return myCommands.keySet();
    }

    /**
     * Gets the set of command names already defined and whose command texts are available.
     * @return existing set of command names whose command texts are available.
     */
    public Set<String> getCommandTextKeySet() {
        return myCommandText.keySet();
    }
}
