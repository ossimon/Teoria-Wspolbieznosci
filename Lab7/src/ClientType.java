public enum ClientType {
    CONSUMER,
    PRODUCER;


    public String activity() {
        return switch (this) {
            case CONSUMER -> "consume";
            case PRODUCER -> "produce";
        };
    }
    @Override
    public String toString() {
        return switch (this) {
            case CONSUMER -> "Consumer";
            case PRODUCER -> "Producer";
        };
    }
}
