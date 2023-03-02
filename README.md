# TCPFileSharing
TCP's client-server based architecture file sharing application. Uploads/downloads large files by breaking them in 1kb chunks for faster communication.

## How to run:

1) Unzip the files to you local directory
2) Open terminal and navigate to the directory where it was extracted
3) Start the server using the following command:
        java ftpserver.java
4) Once server starts open a new terminal and navigate to same directory
5) Start the client using the following command:
        java ftpclient.java


## Server side:

1) After running the file wait for server to start, when below output is displayed 

![image](https://user-images.githubusercontent.com/68017211/222375864-e30eaa12-9daa-42ab-81f6-fa812274f97b.png)


## Client side:

1) Once client starts wait for the enter command prompt to be displayed

![image](https://user-images.githubusercontent.com/68017211/222376021-b5d2b82c-5a1d-497b-9437-2dff807cd6a6.png)

2) To connect to server (defaulted to port 1108) enter the command:
        ftpclient <port>

3) Once confirmation is received from server, as follows:
        Connected to server on port 1108

4)	After successful connection you can use below commands:
    a.	get <filename> - Downloads the file from server to client
    b.	upload <filename> - Uploads the file to server

5)	To terminate client enter “exit”


## Error handling:

1) If empty command is entered anytime during execution
Enter valid command
2) If port no doesn’t match with server’s port
Invalid port no
3) If file to be downloaded doesn’t exist on server
Unable to locate the file
4) If file to upload doesn’t exist with client
Could not locate file
  

## Success messages:

![image](https://user-images.githubusercontent.com/68017211/222376861-4e1da131-f40f-4c1d-9168-921e2c519f36.png)


