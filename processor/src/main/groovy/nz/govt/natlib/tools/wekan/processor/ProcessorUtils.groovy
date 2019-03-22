package nz.govt.natlib.tools.wekan.processor

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentTypes
import groovyx.net.http.HttpBuilder

import java.nio.charset.Charset

@Slf4j
class ProcessorUtils {

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
}
