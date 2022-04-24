import org.json.JSONObject;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main
{
    private static String token;

    public static void doRequest(String path, Boolean isPost, Boolean isSignIn, String data) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        if (isPost)
        {
            connection.setRequestMethod("POST");
            if (!isSignIn)
            {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }
        }
        else
        {
            connection.setRequestMethod("GET");
        }
        connection.connect();
        if (isPost)
        {
            try(OutputStream writer = connection.getOutputStream())
            {
                byte[] input = data.getBytes(StandardCharsets.UTF_8);
                writer.write(input, 0, input.length);
            }
        }
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
        {
            Optional<String> response = Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
            System.out.println(response.get());
            if (isSignIn)
            {
                JSONObject object = new JSONObject(response.get());
                token = object.getString("token");
            }
        }
    }



    public static void main(String[] args)
    {
        String req = "";
        while (!req.equals("exit"))
        {
            try
            {
                System.out.println("Command: ");
                Scanner in = new Scanner(System.in);
                req = in.nextLine();
                String path = "http://localhost:8080/" + req;
                switch (req) {
                    case "auth/signin" -> {
                        System.out.println("Name: ");
                        String name = in.nextLine();
                        System.out.println("Password: ");
                        String password = in.nextLine();
                        doRequest(path, true, true, "{\"userName\": \"" + name + "\", \"password\": \"" + password + "\"}");
                    }
                    case "articles/show", "balance/show", "balance/show/amount/ascending", "balance/show/amount/descending", "balance/show/date/ascending",
                            "balance/show/date/descending", "operations/show", "operations/show/date/ascending", "operations/show/date/descending",
                            "operations/show/balance/amount/ascending", "operations/show/balance/amount/descending", "operations/show/balance/date/ascending",
                            "operations/show/balance/date/descending" -> doRequest(path, false, false, "");
                    case "articles/show/id", "balance/show/id", "operations/show/id", "operations/show/balance-id", "operations/show/article-id" -> {
                        System.out.println("Id: ");
                        int id = in.nextInt();
                        doRequest(path + "/" + id, false, false, "");
                    }
                    case "articles/show/name" -> {
                        System.out.println("Name: ");
                        String name = in.nextLine();
                        doRequest(path + "/" + name, false, false, "");
                    }
                    case "balance/show/amount", "operations/show/balance/amount" -> {
                        System.out.println("Amount: ");
                        BigDecimal amount = in.nextBigDecimal();
                        doRequest(path + "/" + amount, false, false, "");
                    }
                    case "articles/add" -> {
                        System.out.println("Article name: ");
                        String name = in.nextLine();
                        doRequest(path, true, false, "{\"name\": \"" + name + "\"}");
                    }
                    case "balance/add" -> doRequest(path, true, false, "");
                    case "operations/add" -> {
                        System.out.println("Article id: ");
                        int articleId = in.nextInt();
                        System.out.println("Debit: ");
                        BigDecimal debit = in.nextBigDecimal();
                        System.out.println("Credit: ");
                        BigDecimal credit = in.nextBigDecimal();
                        System.out.println("Balance id: ");
                        int balanceId = in.nextInt();
                        doRequest(path, true, false, "{\"articleId\": \"" + articleId + "\", \"debit\": \"" + debit + "\", \"credit\": \""
                                + credit + "\", \"balanceId\": \"" + balanceId + "\"}");
                    }
                    case "articles/change" -> {
                        System.out.println("Name: ");
                        String name = in.nextLine();
                        System.out.println("Id: ");
                        int id = in.nextInt();
                        doRequest(path + "/" + id + "?name=" + name, true, false, "");
                    }
                    case "operations/change/debit" -> {
                        System.out.println("Id: ");
                        int id = in.nextInt();
                        System.out.println("Debit: ");
                        BigDecimal debit = in.nextBigDecimal();
                        doRequest(path + "/" + id + "?debit=" + debit, true, false, "");
                    }
                    case "operations/change/credit" -> {
                        System.out.println("Id: ");
                        int id = in.nextInt();
                        System.out.println("Credit: ");
                        BigDecimal credit = in.nextBigDecimal();
                        doRequest(path + "/" + id + "?credit=" + credit, true, false, "");
                    }
                    case "articles/remove", "balance/remove", "operations/remove/id", "operations/remove/article-id", "operations/remove/balance-id" -> {
                        System.out.println("Id: ");
                        int id = in.nextInt();
                        doRequest(path + "/" + id, true, false, "");
                    }
                    case "exit" -> {
                    }
                    default -> System.out.println("Command not found");
                }
            }
            catch (IOException | InputMismatchException e)
                    {
                        System.out.println("Error!");
                    }
        }
    }
}
