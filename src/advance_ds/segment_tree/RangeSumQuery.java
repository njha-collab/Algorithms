package advance_ds.segment_tree;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Implementation of Segment Tree - Range Sum Query
 */
public class RangeSumQuery {
    
    private int[] sgTree; // array to store segment tree
    private int n; // length of input array
    
    public RangeSumQuery(int[] input) {
        n = input.length;
        int sgTreeSize = 2 * nextPowerOfTwo(n) - 1;
        
        sgTree = new int[sgTreeSize];
        buildTree(input, 0, n-1, 0);
    }
    
    /**
     * Creates a new segment tree from given input array.
     */
    private void buildTree(int[] input, int low, int high, int pos) {
        if (low == high) {
            // its a leaf node
            sgTree[pos] = input[low];
            return;
        }
        
        // construct left and right subtrees and then set value for current node
        int mid = (low + high) / 2;
        buildTree(input, low, mid, (2*pos + 1));
        buildTree(input, mid+1, high, (2*pos + 2));
        sgTree[pos] = sgTree[2*pos + 1] + sgTree[2*pos + 2]; 
    }
    
    /**
     * Updates segment tree for given index by given delta
     */
    public void update(int index, int delta){
        updateUtil(index, delta, 0, n-1, 0);
    }

    private void updateUtil(int index, int delta, int low, int high, int pos) {
        // if index to be updated is outside the current node range
        if (index < low || index > high){
            return;
        }
        
        // if low and high become equal, then index will also be equal to them
        // so we update value in segment tree at pos, and return as low == high
        // means its a leaf node
        if (low == high){
            sgTree[pos] += delta;
            return;
        }
        
        // otherwise keep going left and right to find indexes to be updated 
        // and then update current node as sum of left and right children
        int mid = (low + high) / 2;
        updateUtil(index, delta, low, mid, (2*pos + 1));
        updateUtil(index, delta, mid + 1, high, (2*pos + 2));
        sgTree[pos] = sgTree[2*pos + 1] + sgTree[2*pos + 2];
    }
    
    /**
     * Updates segment tree for a given range by given delta
     */
    public void updateRange(int from, int to, int delta) {        
        updateRangeUtil(from, to, delta, 0, n-1, 0);
    }

    private void updateRangeUtil(int from, int to, int delta, int low, int high, int pos) {
        // if range to be updated is outside the current node range
        if (from > high || to < low ) {
            return;
        }

        // if leaf node
        if (low == high) {
            sgTree[pos] += delta;
            return;
        }

        // otherwise keep going left and right to find nodes to be updated 
        // and then update current node as minimum of left and right children
        int mid = (low + high) / 2;
        updateRangeUtil(from, to, delta, low, mid, (2*pos + 1));
        updateRangeUtil(from, to, delta, mid+1, high, (2*pos + 2));
        sgTree[pos] = sgTree[2*pos + 1] + sgTree[2*pos + 2];
    }
    
    /**
     * Calculates sum of numbers in given range.
     */
    public int sumOfRange(int from, int to){
        return sumOfRangeUtil(0, n-1, from, to, 0);
    }

    private int sumOfRangeUtil(int low, int high, int from, int to, int pos) {
        // total overlap
        if (from <= low && to >= high) {
            return sgTree[pos];
        }
        
        // no overlap
        if (from > high || to < low) {
            return 0;
        }
        
        // partial overlap
        int mid = (low + high) / 2;
        int left = sumOfRangeUtil(low, mid, from, to, (2*pos + 1));
        int right = sumOfRangeUtil(mid+1, high, from, to, (2*pos + 2));
        return left + right;
    }

    // Calculates next power of two for given n, is = 2^ceil(lgn))
    // Equivalent to: int x = (int) Math.pow(2, (int) Math.ceil(Math.log(n) / Math.log(2)));
    public static int nextPowerOfTwo(int n) {
        n--; // to handle the case when n is a perfect square
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n |= n >> 32;
        n++;
        return n;
    }

    public static void main(String[] args) {
        int[] input = {0, 3, 4, 2, 1, 6, -1};
        RangeSumQuery rsq = new RangeSumQuery(input);
        
        System.out.println(rsq.sumOfRange(0, 3)); // 9
        System.out.println(rsq.sumOfRange(1, 5)); // 16
        System.out.println(rsq.sumOfRange(1, 6)); // 15
        
        rsq.update(3, 4); // {0, 3, 4, 6, 1, 6, -1}
        System.out.println(rsq.sumOfRange(1, 3)); // 13
        
        rsq.updateRange(3, 5, -2); // {0, 3, 4, 4, -1, 4, -1}
        System.out.println(rsq.sumOfRange(1, 5)); // 14
        System.out.println(rsq.sumOfRange(0, 3)); //11
        
    }
}
