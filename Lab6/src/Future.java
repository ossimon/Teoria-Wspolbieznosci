public class Future {
    private boolean success;
    private boolean error;
    public Future() {
        this.success = false;
        this.error = false;
    }
    public Future(boolean error) {
        this.error = true;
    }
    public void success() {
        this.success = true;
    }
    public int rendezvous() {
        if (error) return -1;
        else if (success) return 1;
        return 0;
    }
}
