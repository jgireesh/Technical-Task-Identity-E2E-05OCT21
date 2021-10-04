package com.technicalTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Text file Reader class
 *
 * @author gireeshajeera
 */
public class InputReader {
    /**
     * The constant filePath.
     */
    public static String filePath = System.getProperty("user.dir") + "/src/test/resources/";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        InputReader obj = new InputReader();
        String a = obj.carInpuRead(obj.filePath + "car_input.txt");
        System.out.println(a);
        obj.findTheMatchedPatternCarPlateNumbers(a);
        obj.carOutputRead("car_output.txt");
    }

    /**
     * Read the text file
     *
     * @param fileName the file name
     * @return file string
     * @throws IOException the io exception
     * @author gireesha jeera
     */
    String carInpuRead(String fileName) throws IOException {
        System.out.println("File Located : " + filePath);
        String fileStr = null;
        if (fileName.contains("txt")) {
            fileStr = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        } else {
            System.out.println("Invalid file extension type, use only .txt files or check files are kept in /src/test/resources/ location");
        }
        return fileStr;
    }

    /**
     * Find the matched pattern car plate numbers from the string
     * Example: there are 2 groups (1 with space and other without space in the number plates)
     * "([A-Za-z]{2}[0-9]{2}[A-Za-z]{3})   |    ([A-Za-z]{2}[0-9]{2}[^a-z0-9][A-Za-z]{3})"
     * first group (AB11YGT)               |   second group (BM07 BGM)
     * This is case-insensitive
     *
     * @param fileString the file string
     * @return listsOfmatchedStrings list
     * @throws IOException the io exception
     * @author gireeshajeera
     */
    public List<String> findTheMatchedPatternCarPlateNumbers(String fileString) throws IOException {
        Pattern pattern = Pattern.compile("([A-Za-z]{2}[0-9]{2}[A-Za-z]{3})|([A-Za-z]{2}[0-9]{2}[^a-z0-9][A-Za-z]{3})");
        Matcher matcher = pattern.matcher(fileString);
        List<String> strings = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                strings.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                strings.add(matcher.group(2));
            } else {
                System.out.println("Check Regex, none of the group matches criteria");
            }
        }
        System.out.println("Matched Strings " + strings);
        return strings;
    }

    /**
     * Reading the car Output file and creating HashMap for validation as expected values
     *
     * @param fileName the file name
     * @return hash map
     * @throws IOException the io exception
     */
    public HashMap<String, List<String>> carOutputRead(String fileName) throws IOException {
        //reading the file
        BufferedReader br = new BufferedReader(new FileReader(filePath + fileName));
        String line;
        int lineCnt = 0;
        List<String> headersLst = new ArrayList<>();
        List<String> valuesLst = new ArrayList<>();
        HashMap<String, List<String>> carOutputMap = new HashMap<>();
        while ((line = br.readLine()) != null)
            if (lineCnt == 0) {
                headersLst.addAll(Arrays.asList(line.split(",")));
                lineCnt += 1;
            } else {
                valuesLst.addAll(Arrays.asList(line.split(",")));
                lineCnt += 1;
            }
//        System.out.println(headersLst);
//        System.out.println(valuesLst);
        br.close();

        for (int i = 0; i < valuesLst.size(); i += 5) {
            carOutputMap.put(valuesLst.get(i), valuesLst.subList(i + 1, i + 5));
        }
        System.out.println(carOutputMap);
        return carOutputMap;
    }

}