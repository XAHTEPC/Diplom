package com.example.diplom.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import static com.example.diplom.Client.Main_Client.generateSaltFromLogin;
import static com.example.diplom.Client.Main_Client.hashPasswordWithSalt;

public class UniqueComputerIdentifier {
    /* в этом классе мы узнаем серийный номер материнки и серийный номер диска после чего кодируем его в хэш */
    public static String getUniqueComputerIdentifier() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("os: " + os);
            String motherboardSerial = "";
            String macAddress = "";
            String diskSerial = "";
            String uuid = "";
            if (os.contains("win")) {
                motherboardSerial = executeCommand("wmic baseboard get serialnumber");
//                macAddress = executeCommand("getmac /fo csv /nh");
                diskSerial = executeCommand("wmic diskdrive get serialnumber");
                uuid = executeCommand("wmic path win32_computersystemproduct get uuid");
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                motherboardSerial = executeCommand("dmidecode -s baseboard-serial-number");
//                macAddress = executeCommand("cat /sys/class/net/$(ip route show default | awk '/default/ {print $5}')/address");
                diskSerial = executeCommand("udevadm info --query=property --name=$(lsblk -nd -o NAME | head -n 1) | grep ID_SERIAL= | cut -d'=' -f2");
            } else {
                throw new UnsupportedOperationException("Unsupported operating system: " + os);
            }

            System.out.println("Raw Motherboard Serial: " + motherboardSerial);
//            System.out.println("Raw MAC Address: " + macAddress);
            System.out.println("Raw Disk Serial: " + diskSerial);
            System.out.println("UUID MotherBoard: " + uuid);

            //String uniqueId = (motherboardSerial.trim() + macAddress.trim() + diskSerial.trim())
            String uniqueId = (motherboardSerial.trim() + diskSerial.trim() + uuid.trim())
                    .replaceAll("[^a-zA-Z0-9]", ""); // Убираем лишние символы
            System.out.println("UniqueId before hash: " + uniqueId);

            String salt = generateSaltFromLogin(Main_Client.login);
            String hashedId = hashPasswordWithSalt(uniqueId, salt);
//            String hashedId = hashString(uniqueId);
            System.out.println("Unique Identifier: " + hashedId);
//            LoginView.t = "Raw Motherboard Serial: " + motherboardSerial + "\n" + "Raw Disk Serial: " +
//                    diskSerial + "\n" + "UniqueId before hash: " + uniqueId + "\n" +
//                    "Unique Identifier: " + hashedId + "\nUUID MotherBoard: " + uuid +"\n";
//            Front.main(args);
            return hashedId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

    }

    private static String executeCommand(String command) throws Exception {
        Process process;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            process = Runtime.getRuntime().exec(command);
        } else {
            process = new ProcessBuilder("bash", "-c", command).start();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            // Удаляем строки, содержащие None или лишние символы
            if (!line.trim().equalsIgnoreCase("None") && !line.trim().isEmpty()) {
                result.append(line.trim());
            }
        }
        reader.close();
        return result.toString();
    }

    private static String hashString(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
