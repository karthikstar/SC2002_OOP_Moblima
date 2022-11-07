package test;
import controllers.InputController;

import java.util.Locale;

public class InputControllerTest {
    public static void main(String[] args) {
        int a;
        a = InputController.getUserInt();
        System.out.println(a);

        int b;
        b = InputController.getUserInt(0,3);
        System.out.println(b);

        String c;
        c = InputController.getUserString();
        System.out.println(c.toUpperCase(Locale.ROOT));
    }
}
