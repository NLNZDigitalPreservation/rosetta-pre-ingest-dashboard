package nz.govt.natlib.tools.wekan.core

class WekanBoard {
    static final String ID_KEY = "_id"

    String id
    List<WekanList> wekanLists = new ArrayList<>()

    WekanBoard(String id) {
        this.id = id
    }

    WekanBoard(String id, List<Map<String, String>> wekanListsJson) {
        this.id = id
        updateWekanLists(wekanListsJson)
    }

    void updateWekanLists(List<Map<String, String>> wekanListsJson) {
        wekanLists = new ArrayList<>()
        wekanListsJson.each { Map<String, String> wekanListJson ->
            WekanList wekanList = new WekanList(wekanListJson)
            wekanLists.add(wekanList)
        }
    }
}
