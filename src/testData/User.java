package testData;

import utils.Reader;

public class User {

    private String validUsername;
    private String validPassword;
    private String invalidUsername;
    private String invalidPassword;

    public User(String fileName) {
        this.validUsername = Reader.json(fileName).get("validUsername").toString();
        this.validPassword = Reader.json(fileName).get("validPassword").toString();
        this.invalidUsername = Reader.json(fileName).get("invalidUsername").toString();
        this.invalidPassword = Reader.json(fileName).get("invalidPassword").toString();
    }

    public String getValidUsername() {
        return validUsername;
    }
    public String getValidPassword() {
        return validPassword;
    }
    public String getInvalidUsername() {
        return invalidUsername;
    }
    public String getInvalidPassword() {
        return invalidPassword;
    }
}
