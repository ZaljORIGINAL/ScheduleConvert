package Schedule;

public class TimeSession {
    private final int start;
    private final int finish;

    public TimeSession(int start, int finish) {
        this.start = start;
        this.finish = finish;
    }

    public int getStart() {
        return start;
    }

    public int getFinish() {
        return finish;
    }
}
