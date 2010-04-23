package dk.jsh.cleaningrobotsimulator.concurrent;

import org.jdesktop.application.ResourceMap;

/**
 * Board status. (Singelton)
 *
 * @author Jan S. Hansen
 */
public class Board {
    public final String RES_BENDER = "RobotSimulator.bender";
    public final String RES_WALL_E = "RobotSimulator.wall-e";
    public final String RES_ANDROID = "RobotSimulator.android";
    public final String RES_DIRT = "RobotSimulator.dirt";
    public final String RES_DUSTBIN = "RobotSimulator.dustbin";
    public final String RES_CLEAN = "RobotSimulator.clean";
    public final String RES_RECYCLE = "RobotSimulator.recycle";

   private static Board instance = null;
   private Field[][] board;
   private ResourceMap resourceMap;

   /**
    * Private constructor.
    */
   private Board(ResourceMap resourceMap) {
     this.resourceMap = resourceMap;
     board = new Field[Constants.MAX_ROWS][Constants.MAX_COLUMNS];
     //Clean board
     for (int row = 0; row < Constants.MAX_ROWS; row++) {
       for (int column = 0; column < Constants.MAX_COLUMNS; column++) {
           Field field = new Field(column, row,
                 Field.Status.CLEAN, Field.UsedBy.EMPTY);
           field.jLabel.setIcon(resourceMap.getIcon(RES_CLEAN));
           board[row][column] = field;
       }
     }
     setField(0, 0, Field.Status.DUSTBIN, Field.UsedBy.EMPTY, RES_DUSTBIN);
     setField(9, 0, Field.Status.CLEAN, Field.UsedBy.BENDER, RES_BENDER);
     setField(9, 9, Field.Status.CLEAN, Field.UsedBy.WALL_E, RES_WALL_E);
     setField(0, 9, Field.Status.CLEAN, Field.UsedBy.ANDROID, RES_ANDROID);
   }
   
   /**
    * Create (if null) and return an instance.
    * @return a instance of Board.
    */
   public static Board createInstance(ResourceMap resourceMap) {
      if (instance != null) {
          return instance;
      }
      else {
          synchronized (Board.class) {
               if (instance == null) {
                   instance = new Board(resourceMap);
               }
          }
          return instance;
      }
   }

   /**
    * @return a instance of Board.
    */
   public static Board getInstance() {
      return instance;
   }

   public synchronized boolean tryMove(int fromColumn, int fromRow, 
           int toColumn, int toRow) {
       testFieldArguments(fromColumn, fromRow);
       testFieldArguments(toColumn, toRow);
       Field fromField = getField(fromColumn, fromRow);
       Field toField = getField(toColumn, toRow);
       boolean moveOk = false;
       if (toField.isEmpty() && !fromField.isEmpty()) {
          toField.setUsedBy(fromField.getUsedBy());
          fromField.setUsedBy(Field.UsedBy.EMPTY);
          moveOk = true;
       }
       return moveOk;
   }

   public void setField(int column, int row, Field.Status status,
           Field.UsedBy usedBy, String iconResource) {
        testFieldArguments(column, row);
        Field field = board[row][column];
        field.setStatus(status);
        field.setUsedBy(usedBy);
        field.jLabel.setIcon(resourceMap.getIcon(iconResource));
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