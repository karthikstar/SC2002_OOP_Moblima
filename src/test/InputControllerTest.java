package test;
import controllers.InputController;

public class InputControllerTest {
    public static void main(String[] args) {
        int a;
        a = InputController.getUserInt();
        System.out.println(a);

        int b;
        b = InputController.getUserInt(0,3);
        System.out.println(b);
    }
}
