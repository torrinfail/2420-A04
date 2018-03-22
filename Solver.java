import edu.princeton.cs.algs4.*;
public class Solver
{
	private MinPQ<SearchNode> steps;
	private SearchNode initial, solvedNode;
	private Board solvedBoard = new Board({{1,2,3},{4,5,6},{7,8,0}});
	public Solver(Board initial)
	{
		if(initial == null)
			throw new NullPointerException("Cannot solve a null puzzle.");
		if(!initial.isSolvable())
			throw new IllegalArgumentException("Puzzle is not solvable.");
		this.initial = new SearchNode(initial,null);
		steps = new MinPQ<>();
		steps.insert(initial);
		SearchNode current = initial;
		while(!current.equals(solvedBoard))
		{
			for(Board b : current.board.neighbors())
			{
				steps.insert(new SearchNode(b,current));
			}
			current = steps.delMin();
		}
		solvedNode = current;
	}	

	public int moves()
	{
		return 0;
	}

	public Iterable<Board> solution()
	{
		Stack<SearchNode> s = new Stack();
		SearchNode node = solvedNode;
		while(node != null)
		{
			s.push(node);
			node = node.previousNode;
		}		
		return s;
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

	private class SearchNode implements Comparable<SearchNode>
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

		public int compareTo(SearchNode otherNode)
		{
			return priority() - otherNode.priority();		
		}
	}
}
