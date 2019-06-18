package sample;

public class Hexagonal extends Neighborhood{

    int [] valuesOfNeighborhood = new int [6];

    Cell up, down;

    Hexagonal(int id){
        super(id);
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }
}
