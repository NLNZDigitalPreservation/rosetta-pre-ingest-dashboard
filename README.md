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

For example, to list a board:
```
java -jar wekan-groovy-sdk-fat-all-<VERSION>.jar \
    --boardUri="http://192.168.200.1" \
    --username="username" \
    --password="password" \
    --listBoard
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
