import java.io.*;
import java.net.*;

public class MyServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running");

            try (Socket clientSocket = serverSocket.accept();
                 ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

                System.out.println("Client connected");

                while (true) {
                    Object obj = input.readObject();

                    if (obj instanceof String && ((String) obj).equalsIgnoreCase("Q")) {
                        System.out.println("Closing connection");
                        break;
                    }

                    if (obj instanceof GeometricObject) {
                        GeometricObject shape = (GeometricObject) obj;
                        double area = shape.calculateArea();
                        output.writeObject("Answer is: " + area);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

