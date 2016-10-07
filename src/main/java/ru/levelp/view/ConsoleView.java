package ru.levelp.view;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.presenter.ConsolePresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView {
    public static final String PACKING = "Packing files...";
    public static final String UPLOADING = "Uploading...";
    private BufferedReader reader;
    private ConsolePresenter presenter;
    private boolean running = true;

    public ConsoleView(ConsolePresenter presenter) {
        this.presenter = presenter;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        while (running) {
            showMenu();
            try {
                String choice = reader.readLine();
                presenter.onUserAnswered(choice);
            } catch (IOException ignored) {
            }
        }
    }

    private void showMenu() {
        System.out.println("1 - upload");
        System.out.println("4 - quit");
    }

    public void quit() {
        running = false;
    }

    public void showWrongCommand(String command) {
        System.err.println("Wrong command: " + command);
    }

    public String requestFilePath() {
        return requestSomeInput("Input path to file or directory:");
    }

    public String requestBackupName() {
        return requestSomeInput("Input backup name:");
    }

    private String requestSomeInput(String printMessage) {
        System.out.println(printMessage);
        try {
            return reader.readLine();
        } catch (IOException ignored) {
        }
        return null;
    }

    public void showZipError() {
        System.err.println("Data packing error");
    }

    public void showDone() {
        System.out.println("\nComplete");
    }

    public void showUploadError() {
        System.out.println("Uploading error");
    }

    public void showInfo(String text) {
        System.out.println(text);
    }

    public void showProgress(double progress) {
        System.out.printf("\r%.3f", progress);
        System.out.print(" %");
    }
}