package com.context.hierarchy;
import java.sql.*;
import java.util.*;

public class HierarchyOutput {
    public static void main(String[] args) {

        String name = "Деканат ИТИС";

        Connection connection = getConnection();
        Map<String, String> data = getData(connection);
        String code = data.get(name);
        List<String> list = new ArrayList<>();
        String[] array = code.split("\\.");
        int iteration = 1;
        while (!array[0].equals("00")) {
            StringBuilder value = new StringBuilder();
            for (String s : array) {
                value.append(s).append(".");
            }
            list.add(value.substring(0, value.length() - 1));
            while (array[array.length - iteration].equals("00")) {
                iteration++;
            }
            array[array.length - iteration] = "00";
            iteration++;
        }
        Collections.reverse(list);
        StringBuilder sequence = new StringBuilder();
        for (String num : list) {
            if (sequence.length() > 1) {
                sequence.append(" -> ");
            }
            sequence.append(getNameByCode(connection, num));
        }
        System.out.println(sequence);
    }
    public static Map<String, String> getData(Connection connection) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "select code, name from organizational_structure"
            );

            ResultSet resultSet = statement.executeQuery();
            Map<String, String> map = new HashMap<>();
            while (resultSet.next()) {
                map.put(resultSet.getString("name"), resultSet.getString("code"));
            }
            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getNameByCode(Connection connection, String code) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "select name from organizational_structure where code = ?"
            );
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            String val = null;
            while (resultSet.next()) {
                val = resultSet.getString("name");
            }
            return val;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/demo", "itis", "itis");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}