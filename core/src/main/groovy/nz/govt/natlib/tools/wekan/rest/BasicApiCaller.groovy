package nz.govt.natlib.tools.wekan.rest

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentTypes
import groovyx.net.http.HttpBuilder

import java.nio.charset.Charset

@Slf4j
class BasicApiCaller {
    String boardUri
    Map<String, Object> loginToken

    static Map<String, Object> getLoginToken(String uri, String username, String password) {
        Map<String, Object> postResult = HttpBuilder.configure {
            request.uri = uri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
        }.post {
            request.uri.path = "/users/login"
            request.body = [username: username, password: password]
        }
        log.info("result=${postResult}, class=${postResult.getClass().getName()}")
        log.info("Login token=${postResult}")

        return postResult
    }

    BasicApiCaller(String boardUri, Map<String, Object> loginToken) {
        this.boardUri = boardUri
        this.loginToken = loginToken
    }

    BasicApiCaller(String boardUri, String username, String password) {
        this.boardUri = boardUri
        this.loginToken = getLoginToken(boardUri, username, password)
    }

    String getId() {
        return this.loginToken.get("id")
    }

    Object doGetObject(String uriPath, Map<String, String> requestBody = null) {
        log.info("STARTING doGetList")

        Object getResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.get {
            String id = loginToken.get("id")
            request.uri.path = uriPath
            if (requestBody != null) {
                request.body = requestBody
            }
        }
        log.info("result=${getResult}, class=${getResult.getClass().getName()}")
        log.info("ENDING doGetList")

        return getResult
    }

    List<Map<String, Object>> doGetList(String uriPath, Map<String, String> requestBody = null) {
        Object doGetObject = doGetObject(uriPath, requestBody)

        return (List<Map<String, Object>>) doGetObject
    }

    Map<String, Object> doGetMap(String uriPath, Map<String, String> requestBody = null) {
        Object doGetObject = doGetObject(uriPath, requestBody)

        return (Map<String, Object>) doGetObject
    }

    Object doPostObject(String uriPath, Map<String, String> requestBody = null) {
        log.info("STARTING doPostObject")

        Object postResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.post {
            request.uri.path = uriPath
            if (requestBody != null) {
                request.body = requestBody
            }
        }
        log.info("result=${postResult}, class=${postResult.getClass().getName()}")
        log.info("ENDING doPostObject")

        return postResult
    }

    Object doPutObject(String uriPath, Map<String, String> requestBody = null) {
        log.info("STARTING doPutObject")

        Object putResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.put {
            request.uri.path = uriPath
            if (requestBody != null) {
                request.body = requestBody
            }
        }
        log.info("result=${putResult}, class=${putResult.getClass().getName()}")
        log.info("ENDING doPostObject")

        return putResult
    }

    List<Map<String, Object>> doPostList(String uriPath, Map<String, String> requestBody = null) {
        Object doPostObject = doPostObject(uriPath, requestBody)

        return (List<Map<String, Object>>) doPostObject
    }

    Map<String, Object> doPostMap(String uriPath, Map<String, String> requestBody = null) {
        Object doPostObject = doPostObject(uriPath, requestBody)

        return (Map<String, Object>) doPostObject
    }

    Object doDeleteObject(String uriPath, Map<String, String> requestBody = null) {
        log.info("STARTING doDeleteObject")

        Object deleteResult = HttpBuilder.configure {
            request.uri = this.boardUri
            request.contentType = ContentTypes.JSON[0] // "application/json"
            request.charset = Charset.forName("UTF-8")
            String authorizationValue = "Bearer ${loginToken.get('token')}"
            log.info("authorizationValue='${authorizationValue}'")
            request.headers['Authorization'] = authorizationValue
        }.delete {
            request.uri.path = uriPath
            if (requestBody != null) {
                request.body = requestBody
            }
        }
        log.info("result=${postResult}, class=${postResult.getClass().getName()}")
        log.info("ENDING doDeleteObject")

        return deleteResult
    }

}
