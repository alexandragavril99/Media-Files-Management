package Media;

public class Audio extends MediaFile{

    private float duration;

    public Audio(String path, float size, float duration) {
        super(path, size);
        this.duration = duration;
    }

    public Audio(int id, String path, float size, float duration) {
        super(id,path,size);
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public String writeInFile() {
        return "Audio" + " " + super.getId() + " " + this.getPath() + " " + this.getSize() + " " + this.getDuration();
    }

    @Override
    public String toString() {
        return "Audio: " + super.toString() + " | duration=" + duration + "\n";
    }
}
