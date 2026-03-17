class Spot {
    String plate;
    long entryTime;
}

public class ParkingLotOpenAddressing {

    private Spot[] table;
    private int size;

    public ParkingLotOpenAddressing(int capacity) {
        table = new Spot[capacity];
        size = capacity;
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    public int parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while(table[index] != null) {
            index = (index + 1) % size;
            probes++;
        }

        Spot s = new Spot();
        s.plate = plate;
        s.entryTime = System.currentTimeMillis();

        table[index] = s;

        System.out.println("Assigned spot #" + index +
                " (" + probes + " probes)");
        return index;
    }

    public void exitVehicle(String plate) {

        for(int i=0;i<size;i++) {
            if(table[i]!=null && table[i].plate.equals(plate)) {

                long duration =
                        (System.currentTimeMillis() - table[i].entryTime)/1000;

                table[i]=null;
                System.out.println("Vehicle exited, duration " + duration + "s");
                return;
            }
        }
    }

    public static void main(String[] args) {

        ParkingLotOpenAddressing p =
                new ParkingLotOpenAddressing(500);

        p.parkVehicle("ABC1234");
        p.parkVehicle("ABC1235");

        p.exitVehicle("ABC1234");
    }
}