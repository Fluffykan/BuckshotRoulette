public class Pair<S, T> {
    private S s;
    private T t;

    public Pair(S s, T t) {
        this.s = s;
        this.t = t;
    }

    public S head() {
        return s;
    }

    public T tail() {
        return t;
    }
}
