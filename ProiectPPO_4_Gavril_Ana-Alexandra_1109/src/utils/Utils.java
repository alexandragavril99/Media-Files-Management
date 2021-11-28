package utils;

import Media.Audio;
import Media.MediaFile;
import Media.Photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Utils {

    public static float getFileSize(String fileName) {

        Path path = Paths.get(fileName);

        float bytes = 0;
        float value = 0;
        try {
            bytes = Files.size(path);

            BigDecimal bd = new BigDecimal(Float.toString((float) (bytes/Math.pow(1024,2))));
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            value = bd.floatValue();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    };

    public static String getResolution(String pathName) {
        int width = 0;
        int height = 0;
        try {
            BufferedImage bimg = ImageIO.read(new File(pathName));
            width = bimg.getWidth();
            height = bimg.getHeight();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return width + "X" + height;
    }

    public static float getDuration(String pathName) {
        FileInputStream fileInputStream = null;
        float duration = 0;

        try {
            fileInputStream = new FileInputStream(pathName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            duration = Objects.requireNonNull(fileInputStream).getChannel().size() / 128;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static void writeInFile(List<MediaFile> mediaFiles) {
        File f = new File("mediaFile.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(mediaFiles.size() + "\n");
            int i = 0;
            for (MediaFile mediaFile : mediaFiles) {
                if (mediaFile instanceof Audio) {
                    bufferedWriter.write(mediaFile.writeInFile());
                    if (i < mediaFiles.size() - 1) {
                        bufferedWriter.write("\n");
                    }
                    i++;
                }
                if (mediaFile instanceof Photo) {
                    bufferedWriter.write(mediaFile.writeInFile());
                    if (i < mediaFiles.size() - 1) {
                        bufferedWriter.write("\n");
                    }
                    i++;
                }
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<MediaFile> readFromFile() {
        List<MediaFile> mediaFiles = new ArrayList<>();

        try{
            File f = new File("MediaFile.txt");
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int lengthOfFile = bufferedReader.read();

            String line;
            for(int i = 0; i < lengthOfFile; i++) {
                if ((line = bufferedReader.readLine()) != null) {

                    String p[] = line.split(" ");

                    if (p[0].equals("Photo")) {
                        MediaFile photo = new Photo(Integer.parseInt(p[1]), p[2],Float.parseFloat(p[3]),p[4]);
                        mediaFiles.add(photo);
                    } else if(p[0].equals("Audio")) {
                        MediaFile audio = new Audio(Integer.parseInt(p[1]),p[2],Float.parseFloat(p[3]),Float.parseFloat(p[4]));
                        mediaFiles.add(audio);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaFiles;
    }

    public static void writeReport(List<MediaFile> mediaFiles) {
        Map<String, Float> smallMediaFiles = new HashMap<>();
        Map<String, Float> bigMediaFiles = new HashMap<>();

        for(int i = 0; i<mediaFiles.size(); i++) {
           if(mediaFiles.get(i).getSize() < 10) {
               smallMediaFiles.put(mediaFiles.get(i).getPath(), mediaFiles.get(i).getSize());
           } else {
               bigMediaFiles.put(mediaFiles.get(i).getPath(), mediaFiles.get(i).getSize());
           }
        }

        File f = new File("ReportMediaFile.txt");
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Raport bazat pe dimensiunea fisierelor" + "\n" + "\n");
            bufferedWriter.write("Fisiere cu dimensiunea sub 10MB:"+ "\n");

            for (Map.Entry<String, Float> mediaFile : smallMediaFiles.entrySet()) {
                    String key = mediaFile.getKey();
                    Float value = mediaFile.getValue();

                    bufferedWriter.write(key + " - " + value + "MB" + "\n");
            }

            bufferedWriter.write("\n" + "Fisiere cu dimensiunea peste 10MB:"+ "\n");

            for (Map.Entry<String, Float> mediaFile : bigMediaFiles.entrySet()) {
                String key = mediaFile.getKey();
                Float value = mediaFile.getValue();

                bufferedWriter.write(key + " - " + value + "MB" + "\n");
            }
            bufferedWriter.write("\n" + "Media dimensiunilor per categorie" + "\n");
            float[] averageSize = new float[3];
            float sum1 = 0;
            int nr1 = 0;
            float sum2 = 0;
            int nr2 = 0;

            for(int i = 0; i < mediaFiles.size(); i++) {
                if(mediaFiles.get(i) instanceof Audio) {
                    sum1 += mediaFiles.get(i).getSize();
                    nr1++;
                } else {
                    sum2 += mediaFiles.get(i).getSize();
                    nr2++;
                }
            }
            if(nr1 != 0 ) {
                averageSize[0] = sum1/nr1;
            } else averageSize[0] = sum1;

            if(nr2 != 0) {
                averageSize[1] = sum2/nr2;
            } else {
                averageSize[0] = sum2;
            }

            if(mediaFiles.size() != 0) {
                averageSize[2] = (sum1+sum2)/mediaFiles.size();
            } else {
                averageSize[2] = (sum1+sum2);
            }


            bufferedWriter.write("Audio - " + averageSize[0] + "MB" + "\n");
            bufferedWriter.write("Photo - " + averageSize[1] + "MB" + "\n");
            bufferedWriter.write("Total - " + averageSize[2] + "MB" + "\n");

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
