import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Enter 3D object name : ");
                String shapeType = scanner.nextLine();

                if (shapeType.equalsIgnoreCase("Q")) {
                    output.writeObject("Q");
                    System.out.println("Closing connection");
                    break;
                }

                GeometricObject shape = null;

                switch (shapeType.toLowerCase()) {
                    case "circle":
                        System.out.print("Enter radius: ");
                        double radius = scanner.nextDouble();
                        shape = new Circle(radius);
                        break;
                    case "rectangle":
                        System.out.print("Enter width: ");
                        double width = scanner.nextDouble();
                        System.out.print("Enter height: ");
                        double height = scanner.nextDouble();
                        shape = new Rectangle(width, height);
                        break;
                    default:
                        System.out.println("Invalid shape type.");
                        scanner.nextLine();
                        continue;
                }

                scanner.nextLine();
                output.writeObject(shape);

                String response = (String) input.readObject();
                System.out.println("Server message: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

