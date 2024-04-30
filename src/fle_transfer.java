import javax.swing.JOptionPane;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class fle_transfer {
    private static final int FILE_TRANSFER_PORT = 12346;
    private static final int ACKNOWLEDGEMENT_PORT = 12347;
    private static final int FILENAME_TRANSFER_PORT = 12348;
    static String senderIpAddress = "";

    // Sender side for file transfer
    static boolean sendFile(String filePath, String serverAddress) {
        try (Socket socket = new Socket(serverAddress, FILE_TRANSFER_PORT);
             FileInputStream fileInputStream = new FileInputStream(filePath);
             OutputStream outputStream = socket.getOutputStream()) {

            // Send file content
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Receiver side for file transfer
    static boolean receiveFile(String filename, String saveDirectory) {
        try (ServerSocket serverSocket = new ServerSocket(FILE_TRANSFER_PORT);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            // Receive filename
            System.out.println("Received filename: " + filename);

                // Receive file content
                File file = new File(saveDirectory+File.separator+ filename);
                System.out.println(file.exists()+file.getPath()+file.getName());
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    System.out.println("File received successfully and saved to: " + file.getAbsolutePath());
                }
                return true;
            } 
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } 
    

    // Method to send filename
    static boolean sendFilename(String ipadd, String filename) {
        try {
            filename = new File(filename).getName();
            try (Socket socket = new Socket(ipadd, FILENAME_TRANSFER_PORT)) {
                // Send filename length
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(filename.length());

                // Send filename
                outputStream.write(filename.getBytes());
                outputStream.flush();
            }

            System.out.println("Filename sent: " + filename);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to receive filename
    static String receiveFilename() {
        try {
            ServerSocket serverSocket = new ServerSocket(FILENAME_TRANSFER_PORT);
            Socket socket = serverSocket.accept();
            // Receive filename length
            senderIpAddress = socket.getInetAddress().getHostAddress();
            System.out.println(senderIpAddress);
            InputStream inputStream = socket.getInputStream();
            int filenameLength = inputStream.read();

            // Receive filename bytes
            byte[] filenameBytes = new byte[filenameLength];
            int totalBytesRead = 0;
            while (totalBytesRead < filenameLength) {
                int bytesRead = inputStream.read(filenameBytes, totalBytesRead, filenameLength - totalBytesRead);
                if (bytesRead == -1) {
                    // End of stream reached prematurely
                    System.out.println("Failed to receive full filename.");
                    return null;
                }
                totalBytesRead += bytesRead;
            }
            // Convert filename bytes to string
            String filename = new String(filenameBytes);
            System.out.println("Received filename: " + filename);

            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to wait for receiver's acknowledgment
    static boolean waitForReceiverAck() {
        try (ServerSocket serverSocket = new ServerSocket(ACKNOWLEDGEMENT_PORT);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream()) {
    		System.out.println("Waiting for receivers ack");
            byte[] acknowledgment = new byte[5];
            int bytesRead = inputStream.read(acknowledgment);
            String acknowledgmentStr = new String(acknowledgment, 0, bytesRead);
            System.out.println("Line 128"+acknowledgmentStr);
            boolean love=acknowledgmentStr.equals("y");
            System.out.println(love);
            return love;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to send acknowledgment from receiver to sender
    static void sendReceiverAck(boolean accepted) {
        try (Socket socket = new Socket(senderIpAddress, ACKNOWLEDGEMENT_PORT)) {
            String acknowledgment = accepted ? "y" : "n";
            socket.getOutputStream().write(acknowledgment.getBytes());
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
