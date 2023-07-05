package utils;

public class NotEnoughBandwidthException extends Exception{
    public NotEnoughBandwidthException(String msg) {
        super(msg);
    }
}
