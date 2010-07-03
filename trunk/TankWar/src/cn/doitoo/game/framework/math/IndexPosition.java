package cn.doitoo.game.framework.math;

public class IndexPosition {
   /**
	 * 行位置,begin with 0
    */
  public int rowIndex;
  /**
   * 列位置,begin with 0
   */
  public int colIndex;
  
  public IndexPosition(int rowIndex,int colIndex){
	  this.rowIndex = rowIndex;
	  this.colIndex = colIndex;
  }
}
