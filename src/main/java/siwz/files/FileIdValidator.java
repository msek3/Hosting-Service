package siwz.files;

public class FileIdValidator {
    public static boolean validate(String id){
        return id.matches("\\d+");
    }
}
