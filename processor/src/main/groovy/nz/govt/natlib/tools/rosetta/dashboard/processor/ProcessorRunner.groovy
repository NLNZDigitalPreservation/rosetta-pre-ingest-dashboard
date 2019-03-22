package nz.govt.natlib.tools.rosetta.dashboard.processor

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

    @Option(names = ["-a", "--addCard"], description = """Add card.
Requires title.
Optional description.""")
    boolean addCard = false

    @Option(names = ["-r", "--removeCard"], description = """Process the source files.
Output is ready for ingestion by Rosetta.
Requires sourceFolder, targetFolder, forReviewFolder.
Uses startingDate, endingDate.
Optional createDestination, moveFiles.""")
    boolean removeCard = false

    @Option(names = ["-l", "--listBoard" ], description = """List the entire board.
Optional boardName.
If 'boardName' has not been set then all boards are listed.""")
    boolean listBoard = false

    @Option(names = ["-h", "--help" ], usageHelp = true, description = 'Display a help message.')
    boolean helpRequested = false

    @Option(names = ["-i", "--boardUri"], paramLabel = "BOARD_URI", description = """Board URI.
Default is 'http://127.0.0.1'.""")
    String boardUri

    @Option(names = ["-u", "--username"], paramLabel = "USERNAME", description = """Board username.
Required parameter. There is no default.""")
    String username

    @Option(names = ["-p", "--password"], paramLabel = "PASSWORD", description = """Board password.
Required parameter. There is no default.""")
    String password

    @Option(names = ["-n", "--boardName"], paramLabel = "BOARD_NAME", description = """Board name.
There is no default.""")
    String boardName

    @Option(names = ["-t", "--title"], paramLabel = "TITLE", description = """Title.
There is no default.""")
    String title

    @Option(names = ["-d", "--description"], paramLabel = "DESCRIPTION", description = """Description.
There is no default.""")
    String description

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
