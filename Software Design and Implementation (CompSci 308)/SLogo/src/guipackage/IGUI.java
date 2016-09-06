package guipackage;

/**
 * This interface is the external API. This contains the information that the frontend
 * will pass to the backend. The backend will call these methods in order to change the
 * model after receiving the commands that are typed into the console.
 */
public interface IGUI {

    /**
     * Gives the backend the dimensions for the canvas so that they can create and
     * update the Model.
     *
     * @return
     */
    int getWidth();

    int getHeight();

    /**
     * This method notifies the observers of the TurtleObservable object that it
     * has changed. Once the observers see that the object has changed, it can
     * update itself and match the changes of the TurtleObservable object.
     */
    void notifyAllObservers();
}
