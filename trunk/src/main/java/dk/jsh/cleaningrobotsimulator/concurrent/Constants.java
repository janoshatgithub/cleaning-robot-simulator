package dk.jsh.cleaningrobotsimulator.concurrent;

import java.text.SimpleDateFormat;

/**
 * Cleaning robot simulator constants.
 * @author Jan S. Hansen
 */
public class Constants {
    public final static int MAX_ROWS = 10;
    public final static int MAX_COLUMNS = 10;
    public final static int MAX_DIRTY_FIELDS = 10;
    public final static int MAX_CLEANED_FIELDS = 5;
    public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    //Empty private constructor to prevent that this class can be instantiated.
    private Constants() {}
}
