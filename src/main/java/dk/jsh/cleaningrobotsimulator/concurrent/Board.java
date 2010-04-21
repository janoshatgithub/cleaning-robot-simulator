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
     setField('A', 1, Field.Status.DUSTBIN, Field.UsedBy.EMPTY);
     setField('J', 1, Field.Status.CLEAN, Field.UsedBy.BENDER);
     setField('J', 10, Field.Status.CLEAN, Field.UsedBy.WALL_E);
     setField('A', 10, Field.Status.CLEAN, Field.UsedBy.ANDROID);
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

   public void setField(char column, int row, Field.Status status,
           Field.UsedBy usedBy) {
        int boardColumn = getBoardColumn(column);
        int boardRow = getBoardRow(row);
        testFieldArguments(boardColumn, boardRow);
        Field field = board[boardRow][boardColumn];
        field.setStatus(status);
        field.setUsedBy(usedBy);
   }

   public Field getField(char column, int row) {
        int boardColumn = getBoardColumn(column);
        int boardRow = getBoardRow(row);
        testFieldArguments(boardColumn, boardRow);
        return board[boardRow][boardColumn];
   }

    private int getBoardColumn(char column) {
        int boardColumn = ((int) column) - 65;
        return boardColumn;
    }

    private int getBoardRow(int row) {
        int boardRow = row - 1;
        return boardRow;
    }

   private void testFieldArguments(int boardColumn, int boardRow) throws IllegalArgumentException {
        if (boardColumn < 0 || boardColumn >= Constants.MAX_COLUMNS || boardRow < 0 || boardRow >= Constants.MAX_ROWS) {
            throw new IllegalArgumentException();
        }
    }
}