public class Map {
    int size;
    int mapCenter;

    public Map(int mapSize) {
        this.size = mapSize;
        mapCenter = size/2+1;

    }

    void drawMap() {
        char objChar;
        for (int y = 1; y <= this.size; y++) {
            for (int x = 1; x <= this.size; x++) {
                if ((y == 1 && (x == 1 || x == this.size)) || ((y == this.size && (x == 1 || x == this.size))) || (x == this.mapCenter && y == this.mapCenter))
                    objChar = 'H';
                else
                    objChar = ' ';
                System.out.print("[" + objChar + "]");
            }
            System.out.print("\n");
        }
    }
}
