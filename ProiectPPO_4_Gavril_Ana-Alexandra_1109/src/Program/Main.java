package Program;

import Media.Audio;
import Media.MediaCollection;
import Media.Photo;
import utils.InvalidFilePathException;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("\nBuna, Alexandra!\n");

        MediaCollection mediaFiles = MediaCollection.getInstance(Utils.readFromFile());

        int option;
        do {
            System.out.println("\nPentru a gestiona fisierele multimedia introduceti numarul corespunzator meniului de mai jos:\n");
            System.out.println("Introduceti 1 pentru a vizualiza fisierele multimedia.");
            System.out.println("Introduceti 2 pentru a adauga un fisier multimedia.");
            System.out.println("Introduceti 3 pentru a sterge un fisier multimedia.");
            System.out.println("Introduceti 4 pentru a genera raportul bazat pe dimensiunea fisierelor.");
            System.out.println("Introduceti 0 pentru inchiderea aplicatiei.\n");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduceti nr: ");
            option= scanner.nextInt();

            switch(option) {
                case 1:
                    System.out.println("Fisiere multimedia:");
                    System.out.println(mediaFiles.getMediaFiles());
                    break;
                case 2:
                    System.out.println("Introdu calea fisierului: ");

                    try {
                        String path = scanner.next();

                        if (!path.contains("/")) {
                            throw new InvalidFilePathException("Cale fisier gresita!");
                        }

                        float size = Utils.getFileSize(path.trim());
                        String extension = path.substring(path.length() - 3);

                        System.out.println(extension);

                        if (extension.equals("jpg") || extension.equals("png")) {
                            String resolution = Utils.getResolution(path.trim());
                            mediaFiles.addMediaObject(new Photo(path, size, resolution));

                            System.out.println("\nFisier multimedia adaugat!");
                        } else if (extension.equals("wav") || extension.equals("mp3")) {
                            float duration = Utils.getDuration(path);
                            mediaFiles.addMediaObject(new Audio(path, size, duration));

                            System.out.println("\nFisier multimedia adaugat!");
                        } else {
                            System.out.println("\n Fisier respins.");
                        }

                        System.out.println(mediaFiles.toString());
                    } catch(InvalidFilePathException e) {
                        System.out.println(e);
                    }
                    break;
                case 3:
                    System.out.println("Introdu id-ul fisierului dorit:");
                    int id = scanner.nextInt();
                    if(mediaFiles.removeMediaObject(id)) {
                        System.out.println("Fisier media eliminat!");
                    } else {
                        System.out.println("Nu exista ID.");
                    }
                    break;
                case 4:
                    Utils.writeReport(mediaFiles.getMediaFiles());
                    System.out.println("Raport generat!");
                    break;
                case 0:
                    Utils.writeInFile(mediaFiles.getMediaFiles());
                    System.out.println("Activitate incheiata.");
                    scanner.close();
                    break;
                default:
                    System.out.println("Valoare invalida.");
            }
        } while(option != 0);

    }
}
