package User;

import OrmMiddleware.Core;

public class Execute {
    public static void main(String[] args) {

        try {
            new Core().persist();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
