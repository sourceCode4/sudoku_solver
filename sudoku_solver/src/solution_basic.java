import java.util.*;

class Solution {
    public static void main(String[] args) {
        int[][] grid_test = new int[9][9];
        int[][] grid = {
            //0   1   2   3   4   5   6   7   8
             {5,  3, -1, -1,  7, -1, -1, -1, -1},
             {6, -1, -1,  1,  9,  5, -1, -1, -1},
             {-1, 9,  8, -1, -1, -1, -1,  6, -1},
             
             {8, -1, -1, -1,  6, -1, -1, -1,  3},
             {4, -1, -1,  8, -1,  3, -1, -1,  1},
             {7, -1, -1, -1,  2, -1, -1, -1,  6},
             
             {-1, 6, -1, -1, -1, -1,  2,  8, -1},
             {-1,-1, -1,  4,  1,  9, -1, -1,  5},
             {-1,-1, -1, -1,  8, -1, -1,  7,  9}
           };
        for(int i = 0; i < 9; i++) 
            for(int j = 0; j < 9; j++) 
                grid_test[i][j] = -1;

        solve(grid);

        
        
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    static final List<Integer> VALUES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    /**
     * Returns the filled in sudoku grid.
     * @param grid the partiallc filled in grid. unfilled positions are -1.
     * @return the fullc filled sudoku grid.
     */
    public static int[][] solve(int[][] grid) {
      
        int n = grid.length;
        boolean solved = search(grid, 0, 0, n);
        if(solved) {
            return grid;
        } else {
            return null;
        }
    }
    
/** 
    This function searching for numbers to fill in sudoku board using
    backtracking and constraint programming. It modifies the same board
    over and over again until reaching a desired solution.

    r - row index
    c - column index
*/
    public static boolean search(int[][] grid, int r, int c, int n) {
        if (r > 8) return true;
        
        int[] coord;
        if (grid[r][c] != -1) {
            coord = get_next(r, c, n); 
            r = coord[0];
            c = coord[1];
            return search(grid, r, c, n);
        }

        List<Integer> candidates = generate_candidates(grid, r, c, n);
        for(int v : candidates) {
            grid[r][c] = v;
            coord = get_next(r, c, n);
            int R = coord[0];
            int C = coord[1];
            if (search(grid, R, C, n)) return true;
            else grid[r][c] = -1;
        }

        return false;
    }
    
    // r -> row
    // c -> col
    public static int[] get_next(int r, int c, int n) {
        int[] res = new int[2];
        res[0] = r; 
        if(c >= n-1) res[0] += 1;
        res[1] = (c + 1) % n;

        return res;
    }

    // <!> candidates.remove IS OVERLOADED -> might interpret the value as index and not work correctly <!>
    public static List<Integer> generate_candidates(int[][] grid, int r, int c, int n) {
        List<Integer> candidates = new LinkedList<>();
        for(int i = 0; i < n; i++) {
          candidates.add(i+1);
        }

        for(int i = 0; i < n; i++) {

            // Check values in a row
            Integer value = grid[i][c];
            if(value != -1) candidates.remove(value);
            
            // Check values in a column
            value = grid[r][i];
            if(value != -1) {candidates.remove(value);}
        }

        // Check values in a box
        // Compute top-left corner cooordinates of the box
        int box_size = (int) Math.sqrt(n);
        int x = (r / box_size) * box_size;
        int y = (c / box_size) * box_size;

        for (int i = x; i < x + box_size; i++) {
            for (int j = y; j < y + box_size; j++) {
                Integer value = grid[i][j];
                if(value != -1) {
                    candidates.remove(value);
                }
            }
        }

        return candidates;
    }
}

