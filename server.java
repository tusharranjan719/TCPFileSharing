package proj1;

//ftpserver.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class server {
 public static void main(String[] args) throws Exception {
	 //Set server's port here
	 int server_port = 1108;
	 ServerSocket socket = new ServerSocket(server_port);
     System.out.println("Server started on port " + server_port);
     while (true) {
    	 Socket serverSocket = socket.accept();
         DataInputStream inputStream = new DataInputStream(serverSocket.getInputStream());
         //Read input from client
         String command = inputStream.readUTF();
         if (command.startsWith("get")) {
        	 //Fetch the file
             String fileName = command.split(" ")[1];
             File file = new File(fileName);
             if (file.exists() && !file.isDirectory()) {
                 DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());
                 outputStream.writeUTF("FOUND " + file.length());   
                 FileInputStream fileIn = new FileInputStream(file);
                 //send files in chunk
                 byte[] byteArray = new byte[1024];
                 int inputBytes;
                 while ((inputBytes = fileIn.read(byteArray)) > 0) {
                     outputStream.write(byteArray, 0, inputBytes);
                 }
                 fileIn.close();
             } else {
                 DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());
                 outputStream.writeUTF("ERROR");
             }
         } else if (command.startsWith("upload")) {
        	 //Store the file
             String fileName = command.split(" ")[1];
             DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());
             outputStream.writeUTF("READY");
             FileOutputStream fileOut = new FileOutputStream(new File("new"+fileName));
             //send files in chunk
             byte[] byteArray = new byte[1024];
             int inputBytes;
             while ((inputBytes = inputStream.read(byteArray)) > 0) {
                 fileOut.write(byteArray, 0, inputBytes);
             }
             fileOut.close();
         } else if (command.startsWith("ftpclient")){
        	 //Check is port is correct
        	 String port = command.split(" ")[1];
        	 DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());
        	 if(!port.equals(String.valueOf(server_port))) {
        		outputStream.writeUTF("WRONG PORT");
        	 } else {
        		outputStream.writeUTF("OK");
        		System.out.println("\nListening..");
        	 }
        	 
         }
         serverSocket.close();
     }
 }
}