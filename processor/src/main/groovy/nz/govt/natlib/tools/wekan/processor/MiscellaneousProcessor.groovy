package nz.govt.natlib.tools.wekan.processor

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentTypes
import groovyx.net.http.HttpBuilder
import nz.govt.natlib.m11n.tools.automation.logging.Timekeeper

import java.nio.charset.Charset

@Slf4j
class MiscellaneousProcessor {
    Timekeeper timekeeper
    String boardUri
    Map<String, Object> loginToken

    MiscellaneousProcessor(String boardUri, Map<String, Object> loginToken, Timekeeper timekeeper) {
        this.boardUri = boardUri
        this.loginToken = loginToken
        this.timekeeper = timekeeper
    }

    void listBoards() {
        log.info("STARTING listBoards")

        List<Object> getResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.get {
            String id = loginToken.get("id")
            request.uri.path = "/api/users/${id}/boards"
        }
        log.info("result=${getResult}, class=${getResult.getClass().getName()}")

        log.info("Boards list=${getResult}")
        getResult.each { Object boardItem ->
            Map<String, Object> boardMap = (Map<String, Object>) boardItem
            String boardId = (String) boardMap.get("_id")
            String boardTitle = (String) boardMap.get("title")
            log.info("boardId=${boardId}, boardTitle=${boardTitle}")
            List<Object> boardLists = listBoardLists(boardId)
            boardLists.each { Object singleList ->
                println("singleList=${singleList}, class=${singleList.getClass().getName()}")
            }
        }

        log.info("ENDING listBoards")
        timekeeper.logElapsed()
    }

    void listBoard(String boardName) {
        log.info("STARTING listBoard=${boardName}")


        log.info("ENDING listBoard=${boardName}")
        timekeeper.logElapsed()
    }

    List<Object> listBoardLists(String boardId) {
        log.info("STARTING listBoardLists for boardId=${boardId}")
        List<Object> getResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.get {
            request.uri.path = "/api/boards/${boardId}/lists"
        }
        println("result=${getResult}, class=${getResult.getClass().getName()}")

        getResult.each { Object listItem ->
            Map<String, Object> listMap = (Map<String, Object>) listItem
            String listId = (String) listMap.get("_id")
            String listTitle = (String) listMap.get("title")
            log.info("listId=${listId}, listTitle=${listTitle}")
            Map<String, Object> listLists = listBoardList(boardId, listId)
            listLists.each { String key, Object value ->
                println("listList key=${key}, value=${value}, value class=${value.getClass().getName()}")
            }
        }
        log.info("ENDING listBoardLists for boardId=${boardId}")
        return getResult
    }

    Map<String, Object> listBoardList(String boardId, String listId) {
        log.info("STARTING listBoardLists for boardId=${boardId}")
        Map<String, Object> getResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.get {
            request.uri.path = "/api/boards/${boardId}/lists/${listId}"
        }
        println("result=${getResult}, class=${getResult.getClass().getName()}")

        log.info("ENDING listBoardLists for boardId=${boardId}")
        return getResult
    }

    void addCard(String title, String description) {
        log.info("STARTING addCard")


        timekeeper.logElapsed()
    }

    void removeCard(String title) {
        log.info("STARTING removeCard")


        timekeeper.logElapsed()
    }

}
