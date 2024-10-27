package browserhistory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class file {
    private Queue<String> fileParts;

    public file() {
        fileParts = new LinkedList<>();
    }

    // Membaca file dan memotong isinya
    public void readFileAndSplit(String filePath, int partSize) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder currentPart = new StringBuilder();
        String line;
        int currentLength = 0;
        int partCount = 1;

        while ((line = reader.readLine()) != null) {
            // Tambahkan line ke bagian saat ini
            if (currentLength + line.length() + 1 > partSize) {
                // Simpan bagian saat ini ke dalam queue
                fileParts.add(currentPart.toString());
                currentPart.setLength(0); // Reset bagian saat ini
                currentLength = 0; // Reset panjang
                partCount++;
            }
            currentPart.append(line).append("\n"); // Tambahkan line dan newline
            currentLength += line.length() + 1; // Tambahkan panjang line ke total
        }
        // Tambahkan bagian terakhir jika ada
        if (currentPart.length() > 0) {
            fileParts.add(currentPart.toString());
        }
        reader.close();
    }

    // Menyimpan bagian ke file baru
    public void savePartsToFiles() throws IOException {
        int partNumber = 1;

        while (!fileParts.isEmpty()) {
            String part = fileParts.poll(); // Ambil bagian dari queue
            try (FileWriter writer = new FileWriter("part" + partNumber + ".txt")) {
                writer.write(part);
            }
            System.out.println("Bagian " + partNumber + " disimpan.");
            partNumber++;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        file fileSplitter = new file();

        System.out.print("Masukkan path file yang akan dibaca: ");
        String filePath = scanner.nextLine();
        System.out.print("Masukkan ukuran bagian (dalam karakter): ");
        int partSize = scanner.nextInt();

        try {
            fileSplitter.readFileAndSplit(filePath, partSize);
            fileSplitter.savePartsToFiles();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

