package Media;

import java.util.List;

public class MediaCollection {
    private List<MediaFile> mediaFiles;
    private static MediaCollection instance;

    private MediaCollection(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    synchronized public static MediaCollection getInstance(List<MediaFile> mediaFiles) {
        if(instance == null) {
            instance = new MediaCollection(mediaFiles);
        }
       return instance;
    }

    public static void setInstance(MediaCollection instance) {
        MediaCollection.instance = instance;
    }

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void addMediaObject(MediaFile mediaFile) {
        this.mediaFiles.add(mediaFile);
    }

    public boolean removeMediaObject(int id) {
        return this.mediaFiles.removeIf(mediaFile -> mediaFile.getId() == id);
    }

    @Override
    public String toString() {
        return "MediaCollection{" +
                "mediaFiles=" + mediaFiles +
                '}';
    }
}
