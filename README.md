# File-Transfer-Application

This application allows you to easily transfer files between two laptops connected to the same network (e.g., both devices connected to the same Wi-Fi network or mobile hotspot).

## Getting Started

### Prerequisites
- Both laptops must be connected to the same internet network (such as the same Wi-Fi or mobile hotspot).
- Java must be installed on both devices.

### Setup & Usage

1. **Compile and Run the Application**

   On both laptops, open a terminal or command prompt in the application directory and run:

   ```sh
   javac welcome.java
   java welcome
   ```

2. **Prepare to Receive**

   - On the laptop that will receive the file, click the **"Receive"** button in the application.
   - The receiver is now ready to accept incoming files.

3. **Send a File**

   - On the laptop that will send the file:
     1. Click the **"Select File"** button and choose the file you wish to transfer.
     2. Enter the IP address of the receiver laptop in the provided field. (To find the IP address, you can run `ipconfig` (Windows) or `ifconfig` / `ip a` (Linux/Mac) on the receiving device.)
     3. Click the **"Send"** button to start the transfer.

4. **File Transfer**

   - The file will be sent from the sender to the receiver over the local network.

## Notes

- Always click "Receive" on the receiving laptop before clicking "Send" on the sender.
- Ensure firewalls or antivirus software are not blocking the application on either device.
- The application only works if both devices are on the same local network.

## Troubleshooting

- If the connection fails, double-check that both laptops are on the same network and the correct IP address is entered.
- Make sure no firewall is blocking the application's network access.

---
Enjoy fast and easy file transfers across your local network!
