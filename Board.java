import java.util.ArrayList;

public class Board {
	
	private int N;
	private int [][] blocks;
	
	public Board(int[][] blocks){
		N = blocks[0].length;
		this.blocks = new int [N][N];
		for (int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}
	
	public int size(){
		return N;
	}
	
	public int hamming(){
		int error = 0;
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				if (blocks[i][j] != i*N + j + 1 && blocks[i][j] != 0){
					error += 1;
				}
			}
		}
		return error;
	}
	
	public int manhattan(){
		int error = 0;
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				if (blocks[i][j] !=0){
					int row = (blocks[i][j] - 1)/N;
					int col = (blocks[i][j] -1)%N;
					error += Math.abs(row - i) + Math.abs(col - j);
				}
			}
		}
		return error;
	}
	
	public boolean isGoal(){
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				if (blocks[i][j] != i*N + j +1 && (i != N - 1 || j != N - 1)){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isSolvable(){
		 Board newBoard = new Board(blocks);
	        for (int i = N - 1; i >= 0; i--) {
	            for (int j = N - 2; j >= 0; j--) {
	                if (newBoard.blocks[i][j] != 0 && newBoard.blocks[i][j + 1] != 0) {
	                    newBoard.exch(i, j, i, j + 1);
	                    return false;
	                }
	            }
	        }
	        return true;
	    }

	
	public boolean equals(Object y){
		if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.blocks.length != N || that.blocks[0].length != N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
	}
	
	public Iterable<Board> neighbors(){
		if (N < 2) {
            return null;
        }
        ArrayList<Board> boards = new ArrayList<Board>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    if (j > 0) {
                        boards.add(getNeighbor(i, j, i, j - 1));
                    }
                    if (j < N - 1) {
                        boards.add(getNeighbor(i, j, i, j + 1));
                    }
                    if (i > 0) {
                        boards.add(getNeighbor(i, j, i - 1, j));
                    }
                    if (i < N - 1) {
                        boards.add(getNeighbor(i, j, i + 1, j));
                    }
                    return boards;
                }
            }
        }
        return null;
	}
	
	public String toString(){
	    String s = "" + N + "\n";
        for (int i = 0; i < N; i++) {
            s += " ";
            for (int j = 0; j < N; j++) {
                s += blocks[i][j] + "  ";
            }
            s += "\n";
        }
        return s;
	}
	
	 private void exch(int i0, int j0, int i, int j) {
	        int temp = blocks[i0][j0];
	        blocks[i0][j0] = blocks[i][j];
	        blocks[i][j] = temp;
	    }
	 
	   private Board getNeighbor(int i0, int j0, int i, int j) {
	        Board newBoard = new Board(blocks);
	        newBoard.exch(i0, j0, i, j);
	        return newBoard;
	    }
	 
	public static void main(String[] args){
		//int N = StdIn.readInt();
		Board testBoard = new Board(new int[][]{
			{1, 1, 3},
			{4, 0, 2},
			{7, 6, 5}});
	System.out.println(testBoard.toString());
    }
	}