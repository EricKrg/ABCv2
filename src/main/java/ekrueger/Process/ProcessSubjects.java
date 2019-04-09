package ekrueger.Process;

/**
 * @author eric.krueger@uni-jena.de
 */


import ekrueger.Storage.ProcessObserver;

/**
 * This interfaces is need to create an Observer-Pattern between Stores and Processes.
 * that way i can add processes which affect the corresponding store,
 * without making these processes part of the store. thus i can create a model based on processes-instances
 * and not on instances of the stores itself --> model process can be added or removed, without changing the store
 */

public interface ProcessSubjects {
    void registerObs(ProcessObserver pObserver);
    void deleteObs(ProcessObserver pObserver);
    void updateObs(double waterStore);
}
