import edu.princeton.cs.algs4.MinPQ;
public class Solver
{
	private MinPQ<SearchNode> steps;
	private SearchNode initial;
	public Solver(Board initial)
	{
		if(initial == null)
			throw new NullPointerException("Cannot solve a null puzzle.");
		if(!initial.isSolvable())
			throw new IllegalArgumentException("Puzzle is not solvable.");
		this.initial = new SearchNode(initial,null);
		steps = new MinPQ<>();
		steps.insert(initial);
		Iterable<Board> next = steps.min().neighbors();
		Board nextAdd;
		foreach(Board b : next)
		{
			if(nextAdd == null || nextAdd.manhattan() < b.manhattan())
				nextAdd = b;
		}
		steps.insert(nextAdd);
	}	

	public int moves()
	{
		return 0;
	}

	public Iterable<Board> solution()
	{

	}

	public static void main(String[] args) 
	{

		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) 
		{
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else 
		{
			StdOut.println("Unsolvable puzzle");
		}
	}	

	private class SearchNode
	{
		Board board;
		int movesBefore;
		SearchNode previousNode;
		public SearchNode(Board board, SearchNode previousNode)
		{
			this.board = board;
			if(previousNode != null)
			{
				this.previousNode = previousNode;
				movesBefore = previousNode.movesBefore + 1;
			}
		}

		public int priority()
		{
			return movesBefore + board.hamming();
		}
	}
}
