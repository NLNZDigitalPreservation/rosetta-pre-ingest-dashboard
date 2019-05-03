# National Library of New Zealand Wekan Groovy SDK

Provides a groovy-based SDK for accessing the open-source Wekan kanban boards.

## Synopsis

This repository contains classes used to manage Wekan kanban boards (see https://github.com/wekan/wekan).

## Motivation

Wekan kanban boards can be a useful way to visualize and manage certain types of dashboard.

## Important

At this time there is no important information to impart.

## Versioning

See the `build.gradle` file for the current jar version that will be generated.

## Installation

The artifacts are built using gradle and will deploy to a maven repository.

### Complete build
To do a complete build (including unit and code tests and javadoc):
```
gradle [clean] build
```

### Complete build with upgrade-preparation warnings
When gradle 5.x is release, some gradle features and certain build scripts will not work. In order to prepare for
this eventuality, builds can include the `warning-mode` to notify in advance of changes that will need to happen.
```
 gradle [clean] build --warning-mode all
```

## Processor usage

Command-line processing can be completed using various command-line options.

### Command-line options:

Running the jar with the option `-h` or `--help` will produce the output:
```
Usage: processorRunner [-hl] [--addCard] [--addList] [--currentUserDetails]
                       [--deleteCard] [--deleteList] [--displayList]
                       [--listAllBoards] [--listAllCardsForBoard]
                       [--listAllListsForBoard ] [--updateCard] [ -p=PASSWORD]
                       [--boardId=BOARD_ID] [--boardName=BOARD_NAME]
                       [--cardDetails=CARD_DETAILS] [--cardId=CARD_ID]
                       [--description=DESCRIPTION] [--listId=LIST_ID]
                       [--title=TITLE] [--userId=USER_ID] [-i=BOARD_URI]
                       [-u=USERNAME]
Runs different processors based on command-line options.
       -p, --password=PASSWORD
                             Board password.
                             Required parameter. There is no default.
      --addCard              Add a card.
                             Requires --title, --description (--author?).
      --addList              Add list.
                             Requires --boardId, --title.
      --boardId=BOARD_ID     The board id
      --boardName=BOARD_NAME Board name.
                             There is no default.
      --cardDetails=CARD_DETAILS
                             A JSON string representing the card details.
                             For example, '{ blah-blah: 'value' }'.
      --cardId=CARD_ID       The card id
      --currentUserDetails   Current user details.
                             Requires --userId.
      --deleteCard           Delete a card.
                             Requires --boardId, --listId, --cardId.
      --deleteList           Delete a list.
                             Requires --listId.
      --description=DESCRIPTION
                             Description.
                             There is no default.
      --displayList          Display list.
                             Requires --boardId, --listId.
      --listAllBoards        List all boards.
      --listAllCardsForBoard List all cards for board.
                             Requires --boardId.
      --listAllListsForBoard
                             List all lists for a given board.
                             Requires --boardId.
      --listId=LIST_ID       The list id
      --title=TITLE          Title.
                             There is no default.
      --updateCard           Update card.
                             Requires --boardId, --listId, --cardId, --cardDetails.
      --userId=USER_ID       The user id
  -h, --help                 Display a help message.
  -i, --boardUri=BOARD_URI   Board URI.
                             Default is 'http://127.0.0.1'.
  -l, --listBoard            List the entire board.
                             Optional boardName.
                             If 'boardName' has not been set then all boards are listed.
  -u, --username=USERNAME    Board username.
                             Required parameter. There is no default.

```

### Command-line examples

#### List a board
List all boards:
```
java -jar wekan-groovy-sdk-fat-all-<VERSION>.jar \
    --boardUri="http-or-https://<url-or-ip-address>" \
    --username="<username>" \
    --password="<password>" \
    --listBoard
```

List a board:
```
java -jar wekan-groovy-sdk-fat-all-<VERSION>.jar \
    --boardUri="http-or-https://<url-or-ip-address>" \
    --username="<username>" \
    --password="<password>" \
    --listBoard \
    --boardId="<boardId>
```

#### Add a card to a board
```
java -jar wekan-groovy-sdk-fat-all-<VERSION>.jar \
    --boardUri="http-or-https://<url-or-ip-address>" \
    --username="<username>" \
    --password="<password>" \
    --addCard --title="<card-title>" --description="<card-description>"
```

#### Delete a card from a board
```
java -jar wekan-groovy-sdk-fat-all-<VERSION>.jar \
    --boardUri="http-or-https://<url-or-ip-address>" \
    --username="<username>" \
    --password="<password>" \
    --deleteCard \
    --cardId="<card-id>"
```

## API Reference

To generate javadoc:
```
gradle javadoc
```

## Tests

To run unit and other tests:
```
gradle test
```

## Reports

### Unit test reports
Unit test coverage will be produced by
```
gradle test
```

### Jacoco code coverage
While the jacoco plugin is included in builds, there isn't currently any tasks associated with jacoco.
TODO Add jacoco code coverage tasks.

### check
Running `gradle check` runs both findBugs and PMD source code analyzer.

### findBugs
Normally `gradle check` will only run a findBugs report on the main portion of the source code. findBugs can also run on the test code.
```
gradle findBugsMain
gradle findBugsTest
```

### PMD source code analyzer
Normally `gradle check` will only run a PMD report on the main portion of the source code. PMD can also run on the test code.
```
gradle pmdMain
gradle pmdTest
```

## Contributors

See git commits to see who contributors are. Issues are tracked through the git repository issue tracker.

## License

&copy; 2019 National Library of New Zealand. All rights reserved. MIT license.
