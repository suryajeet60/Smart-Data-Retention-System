package com.FullStack.Smart_Data_Retention_System.exception;

public class SmartDataRetentionException extends RuntimeException
{

    public SmartDataRetentionException()
    {
        super();
    }

    public SmartDataRetentionException(String message)
    {
        super(message);
    }

    public SmartDataRetentionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SmartDataRetentionException(Throwable cause)
    {
        super(cause);
    }

}
