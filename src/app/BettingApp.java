package app;

import model.SystemUser;

public class BettingApp {
    private static final String appName = "Betting App v1.0";
    public static void main(String[] args) {
        System.out.println(appName);
        LoggingControl loggingControl = new LoggingControl();
        AppControl appControl = new AppControl();
        SystemUser user = loggingControl.loggingLoop();
        if (user != null)
            appControl.appLoop(user);
    }
}
