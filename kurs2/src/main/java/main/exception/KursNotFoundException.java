package main.exception;

public class KursNotFoundException extends RuntimeException
{
    public KursNotFoundException(String message)
    {
        super(message);
    }
}
