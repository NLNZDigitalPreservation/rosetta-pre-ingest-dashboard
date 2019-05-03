package nz.govt.natlib.tools.wekan.core

class WekanList {
    static final String ID_KEY = "_id"

    String id

    List<WekanCard> wekanCards = new ArrayList<>()

    WekanList(String id) {
        this.id = id
    }

    WekanList(String id, Map<String, String> wekanListJson) {
        this.id = id
        println("wekanListJson=${wekanListJson}")
    }
}
