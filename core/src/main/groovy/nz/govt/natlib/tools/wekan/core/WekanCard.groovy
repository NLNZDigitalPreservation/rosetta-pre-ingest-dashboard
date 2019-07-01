package nz.govt.natlib.tools.wekan.core

class WekanCard {
    static final String ID_KEY = "_id"

    String id

    WekanCard(String id) {
        this.id = id

    }

    WekanCard(String id, Map<String, String> wekanCardJson) {
        this.id = id
        update(wekanCardJson)
    }

    void update(Map<String, String> wekanCardJson) {
        println("wekanCardJson=${wekanCardJson}")
    }
}
