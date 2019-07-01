package nz.govt.natlib.tools.wekan.rest

class ApiCaller {
    BasicApiCaller basicApiCaller

    ApiCaller(String boardUri, String username, String password) {
        this.basicApiCaller = new BasicApiCaller(boardUri, username, password)
    }

    // Users

    Object registerUser(String username, String password, String email) {
        String uriPath = "/users/register"

        // returns the '_id' of the newly created user
        return this.basicApiCaller.doPostObject(uriPath,
                [ username: username, password: password, email: email, fromAdmin: "false" ])
    }

    Object createUser(String username, String password, String email) {
        String uriPath = "/api/users"

        // returns the '_id' of the newly created user
        return this.basicApiCaller.doPostObject(uriPath,
                [ username: username, password: password, email: email, fromAdmin: "true" ])
    }

    Object deleteUser(String userId) {
        String uriPath = "/api/users/${userId}"

        // returns the '_id' of the deleted user
        return this.basicApiCaller.doDeleteObject(uriPath)
    }

    Map<String, Object> getUser(String userId) {
        String uriPath = "/api/users/${userId}"

        return this.basicApiCaller.doGetMap(uriPath, null)
    }

    List<Map<String, Object>> listUsers() {
        String uriPath = "/api/users"

        return this.basicApiCaller.doGetList(uriPath, null)
    }

    Map<String, Object> getCurrentUser() {
        String uriPath = "/api/user"

        return this.basicApiCaller.doGetMap(uriPath, null)
    }

    Object disableUser(String userId) {
        String uriPath = "/api/users/${userId}"

        return this.basicApiCaller.doPutObject(uriPath, { [ action: "disableLogin" ]})
    }

    Object adminTakesUserOwnership(String userId) {
        String uriPath = "/api/users/${userId}"

        return this.basicApiCaller.doPutObject(uriPath, { [ action: "takeOwnership" ]})
    }

    Object enableUser(String userId) {
        String uriPath = "/api/users/${userId}"

        return this.basicApiCaller.doPutObject(uriPath, { [ action: "enableLogin" ]})
    }

    // Boards and lists

    List<Map<String, Object>> listAllBoards() {
        String uriPath = "/api/users/${this.basicApiCaller.getId()}/boards"

        return this.basicApiCaller.doGetList(uriPath, null)
    }

    List<Map<String, Object>> listAllListsForBoard(String boardId) {
        String uriPath = "/api/boards/${boardId}/lists"

        return this.basicApiCaller.doGetList(uriPath, null)
    }

    Map<String, Object> getList(String boardId, String listId) {
        String uriPath = "/api/boards/${boardId}/lists/${listId}"

        return this.basicApiCaller.doGetMap(uriPath, null)
    }

    Object addList(String boardId, String title) {
        String uriPath = "/api/boards/${boardId}/lists"

        return this.basicApiCaller.doPostObject(uriPath, [boardId: boardId, title: title])
    }

    Object deleteList(String boardId, String listId) {
        String uriPath = "/api/boards/${boardId}/lists/${listId}"

        return this.basicApiCaller.doDeleteObject(uriPath, [_id: listId, boardId: boardId])
    }

    List<Map<String, Object>> listAllCardsForBoard(String boardId) {
        String uriPath = "/api/boards/${boardId}/cards"

        return this.basicApiCaller.doGetList(uriPath, null)
    }


    // Cards

    Object addCard(String boardId, String listId, String title, String description, String authorId) {
        String uriPath = "/api/boards/${boardId}/lists/${listId}/cards"

        return this.basicApiCaller.doPostObject(uriPath, [ title: title, description: description, authorId: authorId ])
    }

    Object updateCard(String boardId, String listId, Map<String, String> jsonParameters) {
        String uriPath = "/api/boards/${boardId}/lists/${listId}/cards"

        return this.basicApiCaller.doPutObject(uriPath, jsonParameters)
    }

    Object deleteCard(String boardId, String listId, String cardId, Map<String, String> jsonParameters) {
        String uriPath = "/api/boards/${boardId}/lists/${listId}/cards/${cardId}"

        return this.basicApiCaller.doDeleteObject(uriPath, jsonParameters)
    }

}
