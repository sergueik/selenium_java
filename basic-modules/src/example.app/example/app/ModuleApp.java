package example.app;

import example.greetings.Greeting;

public class ModuleApp {

    public static void main(String[] args) {
        System.out.println(new Greeting().regular("World"));
    }


}
