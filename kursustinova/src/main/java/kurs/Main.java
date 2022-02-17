package kurs;

import java.sql.*;

public class Main {
    public static void main(String[] args)
    {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/budget", "postgres", "parol");
                Statement statement = connection.createStatement();
        )
        {
            String sql = """
                    CREATE TABLE articles (
                        id INTEGER PRIMARY KEY,
                        name VARCHAR(50)
                    );""";
            statement.execute(sql);
            sql = """
                    CREATE TABLE balance (
                        id INTEGER PRIMARY KEY,
                        create_date TIMESTAMP(3),
                        debit NUMERIC(18, 2),
                        credit NUMERIC(18, 2),
                        amount NUMERIC(18, 2)
                    );""";
            statement.execute(sql);
            sql = """
                    CREATE TABLE operations (
                        id INTEGER PRIMARY KEY,
                        article_id INTEGER REFERENCES articles(id),
                        debit NUMERIC(18, 2),
                        credit NUMERIC(18, 2),
                        create_date TIMESTAMP(3),
                        balance_id INTEGER REFERENCES balance(id)
                    );""";
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            System.out.println("Something is wrong");
        }
    }
}
