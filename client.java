package proj1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
	
	public static Integer port;
	
    public static void main(String[] args) throws Exception {
    	System.out.println(" -- ftp client statred -- \n");
    	System.out.println("To get started enter ftpclient <port>");
    	
	    while(true) {
	    	System.out.println("\nEnter command - ");
	    	
	    	//Standard input
	    	Scanner sc = new Scanner(System.in);
	    	String cmd_line = sc.nextLine();
	    	if(cmd_line.equals("exit")) break;
	    	String[] cmd = cmd_line.split(" ");
	    	
	    	//Handle empty inputs
	    	if(cmd.length != 2) {
	    		System.out.println("~ Enter valid command ~");
	    		continue;
	    	}
	    	
	    	if(cmd[0].equals("ftpclient")) port = Integer.valueOf(cmd[1]);
	    	if(port == null) {
	    		System.out.println("Re-enter port no");
	    		continue;
	    	}
	    	try {
	    	//Boilerplate setup
	        Socket clientSocket = new Socket("localhost", port);
	        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
	        DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
	        
	        //Match input command
	        if(cmd[0].equals("ftpclient")) {
	        	//Cross check port with server
	        	outputStream.writeUTF("ftpclient " + String.valueOf(port));
	        	String response = inputStream.readUTF();
	        	if(response.equals("OK")) System.out.println("Connected to server on port " + port);
	        	else if(response.equals("WRONG PORT")) System.out.println("Invalid port no " + port);
	        	else System.out.println("Error connecting to server");
	        	continue;
	        } 
			
			else if (cmd[0].equals("get")) {
	        	//Get the file from server
	            outputStream.writeUTF("get " + cmd[1]);
	            String response = inputStream.readUTF();
	            if (response.startsWith("FOUND")) {
	            	//If found by server
	                long size = Long.parseLong(response.split(" ")[1]);
	                FileOutputStream fileOut = new FileOutputStream("new"+cmd[1]);
	                byte[] byteArray = new byte[1024];
	                int inputBytes;
	                while (size > 0 && (inputBytes = inputStream.read(byteArray, 0, (int) Math.min(byteArray.length, size))) != -1) {
	                    fileOut.write(byteArray, 0, inputBytes);
	                    size -= inputBytes;
	                }
	                fileOut.close();
	                System.out.println("Download successful!");
	            } else {
	                System.out.println("Unable to find the file");
	            }
	        } 
			
			else if (cmd[0].equals("upload")) {
	        	//Send file to server
				File file = new File(cmd[1]);
				if (file.exists() && !file.isDirectory()) {
	            	outputStream.writeUTF("upload " + cmd[1]);
	        		String response = inputStream.readUTF();
	            	if (response.equals("READY")) {
	                    FileInputStream fileIn = new FileInputStream(file);
						//send files in chunk
	                    byte[] byteArray = new byte[1024];
	                    int inputBytes;
	                    while ((inputBytes = fileIn.read(byteArray)) > 0) {
	                        outputStream.write(byteArray, 0, inputBytes);
	                    }
	                    fileIn.close();
	                    System.out.println("Upload successful!");
					}
	            }else {
					outputStream.writeUTF("no_file_exists");
	                System.out.println("Could not locate the file");
	            }
	        } 
			
			else {
	        	System.out.println("Invalid command");
	        	continue;
	        }
	        	clientSocket.close();
	    }
	    
	    catch(ConnectException ce) {
        	System.out.println("Invalid port no");
        	continue;
        	}	
	    catch(Exception e) {
	    	System.out.println("Program terminated due to exception - " + e);
	    	break;
    	}
	    }
    }
}