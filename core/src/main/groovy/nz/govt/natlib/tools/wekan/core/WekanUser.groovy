package nz.govt.natlib.tools.wekan.core

class WekanUser {
    static final String ID_KEY = "_id"
    static final String USERNAME_KEY = "username"
    static final String PASSWORD_KEY = "password"
    static final String EMAIL_KEY = "email"

    String id
    String username
    String password
    String email

    WekanUser(String id, String username, String password, String email) {
        this.id = id
        this.username = username
        this.password = password
        this.email = email
    }

    WekanUser(String id, Map<String, String> wekanUserJson) {
        this.id = id
        username = wekanUserJson.get(USERNAME_KEY)
        password = wekanUserJson.get(PASSWORD_KEY)
        email = wekanUserJson.get(EMAIL_KEY)

        println("wekanUserJson=${wekanUserJson}")
    }
}
