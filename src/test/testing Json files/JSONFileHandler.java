import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
class JSONFileHandler {
    // Method to load JSON data from file
    public static JSONObject loadData(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            StringBuilder jsonData = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                jsonData.append((char) character);
            }
            return new JSONObject(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to save JSON data to file
    public static void saveData(JSONObject data, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data.toString(4)); // Indentation of 4 spaces
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
