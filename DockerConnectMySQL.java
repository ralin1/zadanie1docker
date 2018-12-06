import java.util.Scanner;
import java.sql.*;

public class DockerConnectMySQL {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/bd";
    static final String USER = "Filippov";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Boolean connect = false;
            do {
                try {
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    connect = true;
                } catch (Exception e) {
                    Thread.sleep(1000);
                }
            } while (!connect);
			System.out.println("Connecting to database");
            stmt = conn.createStatement();
            String sql;
            sql = "DROP TABLE IF EXISTS Persons";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Persons (ID int, FirstName varchar(255), LastName varchar(255), Country varchar(255) );";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Persons(ID, FirstName, LastName, Country) VALUES (1,'oleksandr', 'oleknder2', 'ukr'),(2, 'tomasz', 'toma', 'pl'),(3, 'imie', 'nazwisko', 'kraj');";
            stmt.executeUpdate(sql);
            Scanner menu = new Scanner(System.in);
            String i;
            do {
                System.out.println("");
                System.out.println("menu to sql");
                System.out.println("write only numbers");
                System.out.println("choose options:");
                System.out.println("(1) add entity");
                System.out.println("(2) show");
                System.out.println("(3) exit");
                i = (String) menu.next();
                switch (i) {
                    case "1": {
                        Scanner insert = new Scanner(System.in);
                        sql = "SELECT PersonID FROM Persons ORDER BY PersonID DESC LIMIT 1;";
                        ResultSet rs = stmt.executeQuery(sql);
                        int e = 0;
                        if (rs.next()) {
                            e = rs.getInt("PersonID");
                        }
                        rs.close();
                        e++;
                        sql = "INSERT INTO Persons (PersonID, LastName, FirstName, City) VALUES (" + e + ",'";
                        System.out.println("name:");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("last name:");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Country:");
                        sql += insert.nextLine();
                        sql += "');";
                        stmt.executeUpdate(sql);
                        break;
                    }
                    case "2": {
                        sql = "SELECT ID, FirstName, LastName, Country FROM Persons";
                        ResultSet rs = stmt.executeQuery(sql);
                        System.out.printf("|%5s|%15s|%15s|%15s|\n", "ID: ", "Imię: ", "Nazwisko: ", "Country: ");
                        while (rs.next()) {
                            int id = rs.getInt("ID");
                            String first = rs.getString("FirstName");
                            String last = rs.getString("LastName");
                            String city = rs.getString("Country");
                            System.out.printf("|%4d |%14s |%14s |%14s |\n", id, first, last, city);
                        }
                        rs.close();
                        break;
                    }
                    case "3": {
                        i = "3";
                        break;
                    }
                    default: {
						System.out.println("");
                        System.out.println("choose option!");
                        break;
                    }
                }
            } while (i != "3");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
