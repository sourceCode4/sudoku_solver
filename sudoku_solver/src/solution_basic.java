import java.util.*;

class Solution {

    static boolean[][] column_has_value = new boolean[9][10];
    static boolean[][] row_has_value = new boolean[9][10];
    static boolean[][] box_has_value = new boolean[9][10];

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
    
    private static void initialize(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int v = grid[i][j];
                if (v == -1) continue;
                int b = (j / 3) + 3 * (i / 3);
                column_has_value[j][v] = true;
                row_has_value[i][v] = true;
                box_has_value[b][v] = true;
            }
        }
    } 

    static final List<Integer> VALUES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    /**
     * Returns the filled in sudoku grid.
     * @param grid the partiallc filled in grid. unfilled positions are -1.
     * @return the fullc filled sudoku grid.
     */
    public static int[][] solve(int[][] grid) {
        initialize(grid);                
        boolean solved = search(grid, 0, 0);
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
    public static boolean search(int[][] grid, int r, int c) {
        if (r > 8) return true;
        
        int[] coord;
        if (grid[r][c] != -1) {
            coord = get_next(r, c); 
            r = coord[0];
            c = coord[1];
            return search(grid, r, c);
        }

        List<Integer> candidates = generate_candidates(grid, r, c);
        for(int v : candidates) {
            grid[r][c] = v;
            int b = (c / 3) + 3 * (r / 3);
            column_has_value[c][v] = true;
            row_has_value[r][v] = true;
            box_has_value[b][v] = true;
            coord = get_next(r, c);
            int R = coord[0];
            int C = coord[1];
            if (search(grid, R, C)) return true;
            else {
                column_has_value[c][v] = false;
                row_has_value[r][v] = false;
                box_has_value[b][v] = false;
                grid[r][c] = -1;
            }
        }

        return false;
    }
    
    // r -> row
    // c -> col
    public static int[] get_next(int r, int c) {
        int[] res = new int[2];
        res[0] = r; 
        if(c >= 8) res[0] += 1;
        res[1] = (c + 1) % 9;

        return res;
    }

    // <!> candidates.remove IS OVERLOADED -> might interpret the value as index and not work correctly <!>
    public static List<Integer> generate_candidates(int[][] grid, int r, int c) {
        List<Integer> candidates = new LinkedList<>();

        for(int v = 1; v < 10; v++) {

            int b = (c / 3) + 3 * (r / 3);
            if(!column_has_value[c][v] && !row_has_value[r][v] && !box_has_value[b][v]) candidates.add(v);   
        }

        return candidates;
    }
}

