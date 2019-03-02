package ekrueger.Storage;

import ekrueger.Model.EnvCon;

/*
(water) stores are observers to processes
 */

public interface ProcessObserver {
    void update(double waterStore);
}
