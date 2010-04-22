package dk.jsh.cleaningrobotsimulator.concurrent;

/**
 * Board status. (Singelton)
 *
 * @author Jan S. Hansen
 */
public class Board {
   private static Board instance = null;
   private Field[][] board;

   /**
    * Private constructor.
    */
   private Board() {
     board = new Field[Constants.MAX_ROWS][Constants.MAX_COLUMNS];
     //Clean board
     for (int row = 0; row < Constants.MAX_ROWS; row++) {
       for (int column = 0; column < Constants.MAX_COLUMNS; column++) {
         board[row][column] = new Field(Field.Status.CLEAN, Field.UsedBy.EMPTY);
       }
     }
     setField(0, 0, Field.Status.DUSTBIN, Field.UsedBy.EMPTY);
     setField(9, 0, Field.Status.CLEAN, Field.UsedBy.BENDER);
     setField(9, 9, Field.Status.CLEAN, Field.UsedBy.WALL_E);
     setField(0, 9, Field.Status.CLEAN, Field.UsedBy.ANDROID);
   }
   
   /**
    * @return a instance of Board.
    */
   public static Board getInstance() {
      if (instance != null) {
          return instance;
      }
      else {
          synchronized (Board.class) {
               if (instance == null) {
                   instance = new Board();
               }
          }
          return instance;
      }
   }

   public void setField(int column, int row, Field.Status status,
           Field.UsedBy usedBy) {
        testFieldArguments(column, row);
        Field field = board[row][column];
        field.setStatus(status);
        field.setUsedBy(usedBy);
   }

   public Field getField(int column, int row) {
        testFieldArguments(column, row);
        return board[row][column];
   }

   private void testFieldArguments(int column, int row)
           throws IllegalArgumentException {
        if (column < 0 || column >= Constants.MAX_COLUMNS ||
                row < 0 || row >= Constants.MAX_ROWS) {
            throw new IllegalArgumentException();
        }
    }
}