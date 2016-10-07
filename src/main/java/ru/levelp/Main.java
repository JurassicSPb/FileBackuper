package ru.levelp;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.presenter.ConsolePresenter;
import ru.levelp.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsolePresenter presenter = new ConsolePresenter();
        ConsoleView view = new ConsoleView(presenter);
        presenter.setView(view);
        presenter.start();
//        for (int i = 0; i < 100; i++) {
//            view.showProgress(Math.random() * 100);
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}