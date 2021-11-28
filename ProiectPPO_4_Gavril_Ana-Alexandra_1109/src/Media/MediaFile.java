package Media;

public abstract class MediaFile {

    private int id;
    private String path;
    private float size;
    private static int contor = 0;

    public MediaFile(String path, float size) {
        this.id = ++contor;
        this.path = path;
        this.size = size;
    }

    public MediaFile(int id, String path, float size) {
        this.id = id;
        this.path = path;
        this.size = size;
        contor = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public abstract String writeInFile();

    @Override
    public String toString() {
        return  "id=" + id +
                " | path='" + path + '\'' +
                " | size=" + size + "MB";
    }
}
