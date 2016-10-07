package ru.levelp.model.api;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.ProgressListener;
import ru.levelp.model.utils.JsonUtil;

import java.io.*;
import java.net.Socket;

public class ApiManager {
    private static final String IP = "localhost";
    private static final int PORT = 8686;
    private static final ApiManager instance = new ApiManager();

    private ApiManager() {

    }

    public static ApiManager getInstance() {
        return instance;
    }

    public boolean uploadBackup(String pathToZip, String backupName, ProgressListener listener) {
        Socket socket = initConnection();
        if (socket != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                File zip = new File(pathToZip);
                RequestContainer request = new RequestContainer(Methods.UPLOAD);
                request.setBackupName(backupName);
                //отправить jsonRequest через writer
                String jsonRequest = JsonUtil.getInstance().requestToJson(request);
                writer.println(jsonRequest);
                writer.flush();
                //отправить файл
                //  inputStream - это pathToZip
                //  outputStream - socket.getOutputStream()
                FileInputStream inputStream = new FileInputStream(zip);
                int len;
                byte[] buffer = new byte[2048];
                long fileLength = zip.length();
                long uploaded = 0;
                while ((len = inputStream.read(buffer)) >= 0) {
                    RequestContainer filePart = new RequestContainer(Methods.FILE_PART);
                    if (len < 2048) {
                        byte[] bytes = new byte[len];
                        System.arraycopy(buffer, 0, bytes, 0, len);
                        filePart.setBytes(bytes);
                    } else {
                        filePart.setBytes(buffer);
                    }
                    writer.println(JsonUtil.getInstance().requestToJson(filePart));
                    writer.flush();
                    uploaded += len;
                    listener.onProgress((double) uploaded / fileLength * 100);
                }
                RequestContainer finalRequest = new RequestContainer(Methods.FILE_PART);
                writer.println(JsonUtil.getInstance().requestToJson(finalRequest));
                writer.flush();
                inputStream.close();
                //Ждем с помощью reader.readLine() ответа от сервера
                //получили jsonResponse -> ResponseContainer
                listener.onProgress(100);
                String jsonResponse = reader.readLine();
                ResponseContainer response = JsonUtil.getInstance().jsonToResponse(jsonResponse);
                //отключаем все потоки
                writer.close();
                reader.close();
                disconnect(socket);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    private Socket initConnection() {
        try {
            return new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void disconnect(Socket socket) {
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}