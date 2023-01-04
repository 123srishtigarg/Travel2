package com.travel.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class JsonUtility {

    private static Map<String, String> dataMap = new HashMap<>();
    public static final String EMPTY_STRING = "";
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String SRC_DIR = USER_DIR + File.separator;
    private static final String JSON_EXTN = ".json";
    private static final String PARSE_ERR_MSG = "Can't parse the JSON file ";
    private static final String JSON_FILE_MSG = "The required JSON file ";
    private static final String JSON_FILE_NOT_FOUND_MSG = " is not found in the given path: ";
    //public static final String OR_RESOURCE_PATH = USER_DIR + File.separator + "resources" + File.separator + "resourcelocators" + File.separator;

    /**
     * Initializes the Object Repository json files.
     *
     * @param JsonFileName Name of the json file
     */
    public static Object initObjectRepository(String JsonFileName) {
        String jsonFile = addFileExtension(EMPTY_STRING,JsonFileName);
        return initJsonFile(jsonFile, SRC_DIR, dataMap);
    }

    /**
     * Adds file extension as .json if missing.
     *
     * @param fileCategory Category of file
     * @param filename Name of the file
     * @return File name with .json extension
     */
    public static String addFileExtension(String fileCategory,String filename) {
        return filename.contains(JSON_EXTN) ? fileCategory.concat(filename) : fileCategory.concat(filename) + JSON_EXTN;
    }

    /**
     * To read the json files and stores them in object map.
     *
     * @param filename File name to read
     * @param filepath file path where it is located
     * @param objectMap Name of the map for object locators
     */
    public static Object initJsonFile(String filename, String filepath, Object objectMap) {
        Gson gsonObj = new Gson();

        try {
            FileReader reader = new FileReader(filepath + filename);
            objectMap = gsonObj.fromJson(reader, Map.class);
        } catch (JsonSyntaxException e) {
            System.out.println(PARSE_ERR_MSG + filename);
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(JSON_FILE_MSG + filename + JSON_FILE_NOT_FOUND_MSG + filepath);
            System.out.println(e.getMessage());
        }

        return objectMap;
    }

}
