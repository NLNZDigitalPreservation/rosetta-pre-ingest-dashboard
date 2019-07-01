package nz.govt.natlib.tools.wekan.processor

import groovy.json.JsonOutput
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

    List<Object> listBoards() {
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
        String jsonString = JsonOutput.toJson(getResult)
        log.info("Boards list=\n${JsonOutput.prettyPrint(jsonString)}")

        log.info("ENDING listBoards")
        timekeeper.logElapsed()

        return getResult
    }

    String idForBoardTitle(String boardTitle) {
        log.info("STARTING idForBoardName=${boardTitle}")

        List<Object> getResult = listBoards()

        String foundId = null
        Object matchingBoard = getResult.find { Object singleBoard ->
            Map<String, Object> singleBoardMap = (Map<String, Object>) singleBoard
            String candidateTitle = (String) singleBoardMap.get("title")
            boardTitle.equals(candidateTitle)
        }
        if (matchingBoard != null) {
            Map<String, Object> boardMap = (Map<String, Object>) matchingBoard
            foundId = (String) boardMap.get("_id")
        }
        log.info("ENDING idForBoardName=${boardTitle}")
        return foundId
    }

    void listBoardByTitle(String boardTitle) {
        String boardId = idForBoardTitle(boardTitle)
        if (boardId != null) {
            listBoardById(boardId)
        } else {
            log.warn("Cannot find boardId for title=[${boardTitle}]")
        }
    }

    void listBoardById(String boardId) {
        log.info("STARTING listBoardById for id=${boardId}")

        if (boardId != null) {
            Object getResult = HttpBuilder.configure {
                request.uri = this.boardUri
                request.contentType = ContentTypes.JSON[0] // "application/json"
                request.charset = Charset.forName("UTF-8")
                String authorizationValue = "Bearer ${loginToken.get('token')}"
                log.info("authorizationValue='${authorizationValue}'")
                request.headers['Authorization'] = authorizationValue
            }.get {
                String id = loginToken.get("id")
                request.uri.path = "/api/boards/${boardId}"
            }

            log.info("result=${getResult}, class=${getResult.getClass().getName()}")

            String jsonString = JsonOutput.toJson(getResult)
            log.info("board JSON=")
            log.info("\n${JsonOutput.prettyPrint(jsonString)}")
        }
        log.info("ENDING listBoardById for id=${boardId}")
        timekeeper.logElapsed()
    }

    List<Object> listBoardsContents() {
        log.info("STARTING listBoardsContents")

        List<Object> getResult = listBoards()

        log.info("Boards list=${getResult}")
        getResult.each { Object boardItem ->
            Map<String, Object> boardMap = (Map<String, Object>) boardItem
            String boardId = (String) boardMap.get("_id")
            String boardTitle = (String) boardMap.get("title")
            log.info("boardId=${boardId}, boardTitle=${boardTitle}")
            String jsonString = JsonOutput.toJson(boardMap)
            log.info("board JSON=")
            log.info("\n" + JsonOutput.prettyPrint(jsonString))
            List<Object> boardLists = listBoardLists(boardId)
            boardLists.each { Object singleList ->
                println("singleList=${singleList}, class=${singleList.getClass().getName()}")
            }
        }

        log.info("ENDING listBoardsContents")
        timekeeper.logElapsed()

        return getResult
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
            String jsonString = JsonOutput.toJson(listLists)
            log.info("list JSON=")
            log.info("\n" + JsonOutput.prettyPrint(jsonString))
            //listLists.each { String key, Object value ->
            //    println("listList key=${key}, value=${value}, value class=${value.getClass().getName()}")
            //}
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

    List<Map<String, Object>> listBoardListCards(String boardId, String listId) {
        log.info("STARTING listBoardLists for boardId=${boardId}")
        List<Map<String, Object>> getResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.get {
            request.uri.path = "/api/boards/${boardId}/lists/${listId}/cards"
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
