package oop.ex6.main;

import oop.ex6.Operations.ValidityError;
import oop.ex6.Operations.analyzeLine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this is the main class that include the
 * main function to run the program
 */
public class Sjavac {
    /**
     * this function open the file and read the lines and put the
     * in a list then return it
     *
     * @param filePath the file path to open
     * @return array list of all the lines
     * @throws IOException in case of input output errors
     */
    private static ArrayList<String> readFile(String filePath) throws IOException {
        ArrayList<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
            return records;
        } catch (Exception e) {
            throw new IOException();
        }
    }

    /**
     * the main function to run the program
     *
     * @param args the argument that include the file path
     */
    public static void main(String[] args) {
        try {
            analyzeLine.newBeginning();
            ArrayList<String> records = readFile(args[0]);
            analyzeLine.checkLines(records);
            System.out.println(0);
        } catch (ValidityError e) {
            System.out.println(1);
        } catch (IOException e) {
            System.out.println(2);
        }
    }
}
