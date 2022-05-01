package lcmp;

import java.util.function.Supplier;

public class LCMP implements Supplier<Object> {
    protected Long k() { return -3L; }

    @Override
    public Object get() {
        return k() > 0L;
    }
}
