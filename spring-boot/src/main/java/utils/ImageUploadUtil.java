package utils;

public class ImageUploadUtil {
    public static final String AWS_ADDRESS = "https://s3.ap-northeast-2.amazonaws.com/wagltoktok";

    public static final String getImagePath(String className, String id, String imageName) {
        return AWS_ADDRESS + "/uploads/" + className.toLowerCase() + "/image/" + id + "/" + imageName;
    }

    public static final String saveImagePath(String className, String id, String imageName) {
        return "uploads/" + className.toLowerCase() + "/image/" + id + "/" + imageName;
    }

    public static final String replaceImagePath(String className, String id, String imageName) {
        return imageName.replace(AWS_ADDRESS + "/uploads/" + className.toLowerCase() + "/image/" + id + "/", "");
    }
}
