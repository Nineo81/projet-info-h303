package model;


import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CSVParser {

    private static List<String[]> readCSV(String fileName){
        List<String[]> allData =null;
        try{
            FileReader fileReader = new FileReader(fileName);
            CSVReader csvReader = new CSVReader(fileReader);
            allData = csvReader.readAll();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return allData;
    }

    private static ArrayList<HashMap<String,String>> parseCSV(List<String[]> allData){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        String[] header = new String[0];
        HashMap<String,String> hmap = new HashMap<>();

        for (String[] line : allData){
            if (line == allData.get(0)){
                header = line;
            }
            else {
                int i = 0;
                for (String element : line){
                    hmap.put(header[i],element);
                    i++;
                }
                list.add((HashMap<String, String>) hmap.clone());
                hmap.clear();
            }
        }
        return list;
    }

    public static ArrayList<HashMap<String,String>> parsing(String fileName){
        return parseCSV(readCSV(fileName));
    }
}
