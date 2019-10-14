# cineplex-notifier

## Build instructions

Run the following:

```bash
export SENDGRID_API_KEY=<your api key>
./scripts/build-test-environment.sh  < /dev/null
mvn -P arq-wildfly-managed clean -Dmaven.test.failure.ignore=true test
```

Start wildfly in the usualy way (out of scope of this document)

## Run instructions

In the `webapp` director run the following

```bash
mvn wildfly:deploy
```

## Usage

Sever runs at http://localhost:8080/.

To Load data you can run `groovy scripts/scrape-cineplex.groovy` though the screen scraping may fail.

## Disclaimer

When this project was originally built [Cineplex's robots.txt](https://www.cineplex.com/robots.txt) did not block scraping of movie data.
It is up to you to know what data you should and should not be agregating when you use these tools.
