import javax.swing.*;

import java.awt.BorderLayout;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

class fle_transfer {
    private static final int FILE_TRANSFER_PORT = 12346;
    private static final int ACKNOWLEDGEMENT_PORT = 12347;
    private static final int FILENAME_TRANSFER_PORT = 12348;
    private static String senderIpAddress = "";
    private static JProgressBar progressBar;
    private static JFrame frame;

    // Show progress window
    private static void showProgressWindow() {
        frame = new JFrame("File Transfer Progress");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLocationRelativeTo(null);

        // Create and add progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        JLabel label = new JLabel("Transferring file...", SwingConstants.CENTER);

        // Add components to frame
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.NORTH);
        frame.add(progressBar, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Close progress window
    private static void closeProgressWindow() {
        frame.dispose();
    }

    // Sender side for file transfer using NIO
    static boolean sendFile(String filePath, String serverAddress) {
        showProgressWindow();
        File file = new File(filePath);
        long fileSize = file.length();

        try (SocketChannel socketChannel = SocketChannel.open(new java.net.InetSocketAddress(serverAddress, FILE_TRANSFER_PORT));
             FileChannel fileChannel = new FileInputStream(file).getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            long totalBytesSent = 0;

            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    totalBytesSent += socketChannel.write(buffer);
                }
                buffer.clear();

                // Update progress bar
                int percentSent = (int) ((totalBytesSent * 100) / fileSize);
                SwingUtilities.invokeLater(() -> progressBar.setValue(percentSent));
            }

            // Close progress window when done
            closeProgressWindow();
            System.out.println("File sent successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Receiver side for file transfer using NIO
    static boolean receiveFile(String filename, String saveDirectory) {
        try (ServerSocket serverSocket = new ServerSocket(FILE_TRANSFER_PORT);
             Socket socket = serverSocket.accept();
             SocketChannel socketChannel = socket.getChannel();
             FileOutputStream fos = new FileOutputStream(saveDirectory + File.separator + filename);
             FileChannel fileChannel = fos.getChannel()) {

            long fileSize = 10000000; // Replace with actual size negotiation, if needed
            long totalBytesReceived = 0;

            showProgressWindow();

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (socketChannel.read(buffer) > 0) {
                buffer.flip();
                totalBytesReceived += fileChannel.write(buffer);
                buffer.clear();

                // Update progress bar
                int percentReceived = (int) ((totalBytesReceived * 100) / fileSize);
                SwingUtilities.invokeLater(() -> progressBar.setValue(percentReceived));
            }

            closeProgressWindow();
            System.out.println("File received successfully and saved to: " + (saveDirectory + File.separator + filename));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to send filename (no changes)
    static boolean sendFilename(String ipadd, String filename) {
        try {
            filename = new File(filename).getName();
            try (Socket socket = new Socket(ipadd, FILENAME_TRANSFER_PORT)) {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(filename.length());
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

    // Method to receive filename (no changes)
    static String receiveFilename() {
        try {
            ServerSocket serverSocket = new ServerSocket(FILENAME_TRANSFER_PORT);
            Socket socket = serverSocket.accept();
            senderIpAddress = socket.getInetAddress().getHostAddress();
            InputStream inputStream = socket.getInputStream();
            int filenameLength = inputStream.read();

            byte[] filenameBytes = new byte[filenameLength];
            int totalBytesRead = 0;
            while (totalBytesRead < filenameLength) {
                int bytesRead = inputStream.read(filenameBytes, totalBytesRead, filenameLength - totalBytesRead);
                if (bytesRead == -1) {
                    System.out.println("Failed to receive full filename.");
                    return null;
                }
                totalBytesRead += bytesRead;
            }
            String filename = new String(filenameBytes);
            System.out.println("Received filename: " + filename);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to wait for receiver's acknowledgment (no changes)
    static boolean waitForReceiverAck() {
        try (ServerSocket serverSocket = new ServerSocket(ACKNOWLEDGEMENT_PORT);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream()) {
            byte[] acknowledgment = new byte[5];
            int bytesRead = inputStream.read(acknowledgment);
            String acknowledgmentStr = new String(acknowledgment, 0, bytesRead);
            return acknowledgmentStr.equals("y");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to send acknowledgment from receiver to sender (no changes)
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
