import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DropTables {
    public static void main(String[] args) {
        String[] dbs = {"fitness-service-activity", "fitness-service-ai"};
        for (String db : dbs) {
            String url = "jdbc:postgresql://localhost:5432/" + db;
            try (Connection conn = DriverManager.getConnection(url, "postgres", "@root@123@");
                 Statement stmt = conn.createStatement()) {
                System.out.println("Connected to " + db);
                if (db.equals("fitness-service-activity")) {
                    stmt.execute("DROP TABLE IF EXISTS activity CASCADE;");
                    System.out.println("Dropped activity table.");
                } else if (db.equals("fitness-service-ai")) {
                    stmt.execute("DROP TABLE IF EXISTS recommendations CASCADE;");
                    System.out.println("Dropped recommendations table.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
