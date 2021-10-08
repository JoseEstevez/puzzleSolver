package puzzles.water;

/**
 * This class represents a single Bucket. It holds the buckets maximum capacity and the amount of water
 * its currently holding. It also has several methods used for filling and dumping these buckets. It is used by WaterConfig.
 *
 * @author Jose Estevez
 */
public class Bucket {
    private int capacity;
    private int current;

    /**
     * Constructs a bucket with a specified capacity and sets its fill level to 0.
     *
     * @param capacity
     */
    public Bucket(int capacity) {
        this.capacity = capacity;
        this.current = 0;
    }

    /**
     * Completely fills a bucket to its capacity
     *
     */
    public void fill() {
        this.current = capacity;
    }

    /**
     * Fills a bucket by a specified amount
     *
     * @param amount the specified amount
     */
    public void fill(int amount) {
        this.current = current + amount;
    }

    /**
     * Removes all of the water from a bucket
     *
     */
    public void dump() {
        this.current = 0;
    }

    /**
     * Removes a specified amount from a bucket
     *
     * @param amount the specified amount
     */
    public void dump(int amount) {
        this.current = current-amount;
    }

    /**
     * Pours as much water as possible from the current bucket to another bucket, if able
     *
     * @param other the other bucket
     */
    public void pour(Bucket other) {
        if (other.space() > 0) {
            if (this.current > other.space()) {
                this.dump(other.space());
                other.fill();
            }
            else {
                other.fill(this.current);
                this.dump();
            }
        }
    }

    /**
     * Accesses the current fill level of the bucket
     *
     * @return fill level
     */
    public int getCurrent() {
        return this.current;
    }

    /**
     * Returns the amount of space a bucket has for more water
     *
     * @return space left
     */
    public int space() {
        return this.capacity-this.current;
    }

    /**
     * Accesses the bucket's maximum capacity
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Checks whether the bucket has the same amount of water as another bucket
     *
     * @param obj other bucket
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bucket) {
            return this.current == ((Bucket) obj).current;
        }
        return false;
    }
}

