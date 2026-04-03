package tower;

public class TowerSolver {
    private TowerModel model;

    public TowerSolver() {
        // Nothing to do here
    }

    public void solve(TowerModel model) {
        this.model = model;
        // Start the recursive process: 
        // Move all disks from tower 0 to tower 2, using tower 1 as a spare.
        solveRecursive(model.getHeight(), 0, 2, 1);
    }

    // Overloaded recursive method
    private void solveRecursive(int n, int source, int destination, int spare) {
        // Base case: If there are no disks to move, stop.
        if (n == 0) {
            return;
        }

        // Step 1: Move n-1 disks from source to spare
        solveRecursive(n - 1, source, spare, destination);

        // Step 2: Move the nth (largest) disk from source to destination
        model.move(source, destination);

        // Step 3: Move the n-1 disks from spare to destination
        solveRecursive(n - 1, spare, destination, source);
    }
}