package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/program-nth-catalan-number/
// http://www.geometer.org/mathcircles/catalan.pdf

public class CatalanNumber {
    
    /*
     * Suppose you have n pairs of parentheses and you would like to form valid groupings of them, 
     * where "valid" means that each open parenthesis has a matching closed parenthesis. 
     * For example, "(()())" is valid, but "())()(" is not. How many valid groupings can be formed  
     * for a given value of n?

     * We know that in any balanced set, the first character has to be '('. We also know that
     * somewhere in the set is the matching ')' for that opening one. In between that pair of 
     * parentheses is a balanced set of parentheses (lets call it A), and to the right of it 
     * is another balanced set of parentheses (lets call it B).
     * 
     *                                      (A)B
     * 
     * So A is a balanced set of parentheses and so is B. Both A and B together can contain up to n-1
     * pairs of parentheses (because out of n, one pair we have already considered). If A contains
     * k pairs, then B will contain (n - 1 - k) pairs. The value of k can vary from 0 to n-1 (either
     * A or B can have 0 pair of parentheses as well.)
     */

    /*
     * Below is recurrence equation for Catalan Number
     * C(n) = Summation[C(k) * C(n-1-k)]
     *           k = 0 to n-1
     * 
     * i.e., C(n) = C(0)*C(n-1) + C(1)*C(n-2) ... C(n-2)*C(1) + C(n-1)*C(0)
     * 
     * Base cases: when (n == 0 or n == 1)
     * C(0) = C(1) = 1
     * 
     * for n == 0, answer is 1, because there is exactly one way of arranging zero
     * parentheses: don't write anything.
     */
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int catalanNumber(int n) {
            if (n == 0 || n == 1)
                return 1;
            
            int count = 0;
            for (int k = 0; k < n; k++)
                count += catalanNumber(k) * catalanNumber(n-1-k);
            return count;
        }
    }
    
    class DPSolution {
        // T(n): O(n^2), S(n): O(n)
        public int catalanNumber(int n) {
            int[] dp = new int[n+1];
            dp[0] = dp[1] = 1;
            
            for (int i = 2; i <= n; i++)
                for (int k = 0; k < i; k++)
                    dp[i] += dp[k] * dp[i-1-k];
            
            return dp[n];
        }
    }
    
    public static void main(String[] args) {
        CatalanNumber o = new CatalanNumber();
        int n = 5;
        System.out.println(o.new SimpleRecursiveSolution().catalanNumber(n)); // 42
        System.out.println(o.new DPSolution().catalanNumber(n)); // 42
    }
}
