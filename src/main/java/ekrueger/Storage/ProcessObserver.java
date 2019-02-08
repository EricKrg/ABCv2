package ekrueger.Process;

import ekrueger.Model.EnvCon;

public interface ProcessObserver {
    void update(EnvCon environment);
}
