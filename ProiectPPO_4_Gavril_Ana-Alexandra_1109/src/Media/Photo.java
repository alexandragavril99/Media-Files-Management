package Media;

public class Photo extends MediaFile{

    private String resolution;

    public Photo(String path, float size, String resolution) {
        super(path, size);
        this.resolution = resolution;
    }

    public Photo(int id, String path, float size, String resolution) {
        super(id,path,size);
        this.resolution = resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String writeInFile() {
        return "Photo" + " " + super.getId() + " " + this.getPath() + " " + this.getSize() + " " + this.getResolution();
    }

    @Override
    public String toString() {
        return "Photo: " + super.toString() + " | resolution=" + resolution + "\n";
    }
}
