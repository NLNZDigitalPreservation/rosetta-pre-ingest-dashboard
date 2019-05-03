package nz.govt.natlib.tools.wekan.processor

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import groovy.util.logging.Slf4j
import nz.govt.natlib.m11n.tools.automation.logging.Timekeeper

import java.util.concurrent.Callable

@Slf4j
@Command(description = 'Runs different processors based on command-line options.', name = 'processorRunner')
class ProcessorRunner implements Callable<Void>{
    final static String DEFAULT_BOARD_URI = "http://127.0.0.1"

    @Option(names = [ "-l", "--listBoard" ], description = """List the entire board.
Optional boardName.
If 'boardName' has not been set then all boards are listed.""")
    boolean listBoard = false

    @Option(names = [ "-h", "--help" ], usageHelp = true, description = 'Display a help message.')
    boolean helpRequested = false

    @Option(names = [ "-i", "--boardUri" ], paramLabel = "BOARD_URI", description = """Board URI.
Default is 'http://127.0.0.1'.""")
    String boardUri

    @Option(names = [ "-u", "--username" ], paramLabel = "USERNAME", description = """Board username.
Required parameter. There is no default.""")
    String username

    @Option(names = [ " -p", "--password" ], paramLabel = "PASSWORD", description = """Board password.
Required parameter. There is no default.""")
    String password

    @Option(names = [ "--boardName" ], paramLabel = "BOARD_NAME", description = """Board name.
There is no default.""")
    String boardName

    @Option(names = [ "--title" ], paramLabel = "TITLE", description = """Title.
There is no default.""")
    String title

    @Option(names = [ "--description" ], paramLabel = "DESCRIPTION", description = """Description.
There is no default.""")
    String description

    @Option(names = [ "--userId" ], paramLabel = "USER_ID", description = """The user id""")
    String userId

    @Option(names = [ "--boardId" ], paramLabel = "BOARD_ID", description = """The board id""")
    String boardId

    @Option(names = [ "--listId" ], paramLabel = "LIST_ID", description = """The list id""")
    String listId

    @Option(names = [ "--cardId" ], paramLabel = "CARD_ID", description = """The card id""")
    String cardId

    @Option(names = [ "--cardDetails" ], paramLabel = "CARD_DETAILS", description = """A JSON string representing the card details.
For example, '{ blah-blah: 'value' }'.""")
    String cardDetails

    @Option(names = [ "--currentUserDetails" ], description = """Current user details.
Requires --userId.""")
    boolean currentUserDetails = false

    @Option(names = [ "--listAllBoards" ], description = """List all boards.""")
    boolean listAllBoards

    @Option(names = [ "--listAllListsForBoard" ], description = """List all lists for a given board.
Requires --boardId.""")
    boolean listAllListsForBoard = false

    @Option(names = [ "--displayList" ], description = """Display list.
Requires --boardId, --listId.""")
    boolean displayList = false

    @Option(names = [ "--addList" ], description = """Add list.
Requires --boardId, --title.""")
    boolean addList = false

    @Option(names = [ "--deleteList" ], description = """Delete a list.
Requires --listId.""")
    boolean deleteList = false

    @Option(names = [ "--listAllCardsForBoard" ], description = """List all cards for board.
Requires --boardId.""")
    boolean listAllCardsForBoard = false

    @Option(names = [ "--addCard" ], description = """Add a card.
Requires --title, --description (--author?).""")
    boolean addCard = false

    @Option(names = [ "--deleteCard"], description = """Delete a card.
Requires --boardId, --listId, --cardId.""")
    boolean removeCard = false

    @Option(names = [ "--updateCard" ], description = """Update card.
Requires --boardId, --listId, --cardId, --cardDetails.""")
    boolean updateCard = false

    Timekeeper timekeeper = new Timekeeper()

    static void main(String[] args) {
        ProcessorRunner processorRunner = new ProcessorRunner()
        CommandLine.call(processorRunner, args)
    }

    @Override
    Void call() throws Exception {
        process()
        return null
    }

    void process() {
        if (username == null || username.isEmpty()) {
            String message = "username=${username} must be set."
            log.error(message)
            throw new ProcessorException(message)
        }
        if (password == null || password.isEmpty()) {
            String message = "password must be set."
            log.error(message)
            throw new ProcessorException(message)
        }
        timekeeper.start()
        Map<String, Object> loginToken = ProcessorUtils.getLoginToken(boardUri, username, password)

        // Do the non-destructive options first
        if (listBoard) {
            MiscellaneousProcessor miscellaneousProcessor = new MiscellaneousProcessor(boardUri, loginToken, timekeeper)
            if (this.boardName == null || this.boardName.isEmpty()) {
                miscellaneousProcessor.listBoards()
            } else {
                miscellaneousProcessor.listBoard(this.boardName)
            }
        }
        if (addCard) {
            if (title == null) {
                String message = "addCard requires title"
                log.error(message)
                throw new ProcessorException(message)
            }
            MiscellaneousProcessor miscellaneousProcessor = new MiscellaneousProcessor(boardUri, loginToken, timekeeper)
            miscellaneousProcessor.addCard(this.title, this.description)
        }
        if (removeCard) {
            if (title == null) {
                String message = "addCard requires title"
                log.error(message)
                throw new ProcessorException(message)
            }
            MiscellaneousProcessor miscellaneousProcessor = new MiscellaneousProcessor(boardUri, loginToken, timekeeper)
            miscellaneousProcessor.removeCard(this.title)
        }
    }
}
