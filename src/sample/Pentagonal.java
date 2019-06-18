package sample;

public class Pentagonal extends Neighborhood {

    int [] valuesOfNeighborhood = new int [5];
    int id;
    String name;

    public Pentagonal(int id) {

        switch (id){
            case 1:
            {
                name = "Pentagonal Up";
                this.id = id;
                break;
            }
            case 2:
            {
                name = "Pentagonal Down";
                this.id = id;
                break;
            }
            case 3:
            {
                name = "Pentagonal Left";
                this.id = id;
                break;
            }
            case 4:
            {
                name = "Pentagonal Right";
                this.id = id;
                break;
            }
            default: System.out.println("Error"); break;
        }
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }
}
