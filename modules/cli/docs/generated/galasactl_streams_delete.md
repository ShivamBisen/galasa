## galasactl streams delete

Deletes a test stream by name

### Synopsis

Deletes a single test stream with the given name from the Galasa service

```
galasactl streams delete [flags]
```

### Options

```
  -h, --help          Displays the options for the 'streams delete' command.
      --name string   A mandatory field indicating the name of a test stream.
```

### Options inherited from parent commands

```
  -b, --bootstrap string                      Bootstrap URL. Should start with 'http://' or 'file://'. If it starts with neither, it is assumed to be a fully-qualified path. If missing, it defaults to use the 'bootstrap.properties' file in your GALASA_HOME. Example: http://example.com/bootstrap, file:///user/myuserid/.galasa/bootstrap.properties , file://C:/Users/myuserid/.galasa/bootstrap.properties
      --galasahome string                     Path to a folder where Galasa will read and write files and configuration settings. The default is '${HOME}/.galasa'. This overrides the GALASA_HOME environment variable which may be set instead.
  -l, --log string                            File to which log information will be sent. Any folder referred to must exist. An existing file will be overwritten. Specify "-" to log to stderr. Defaults to not logging.
      --rate-limit-retries int                The maximum number of retries that should be made when requests to the Galasa Service fail due to rate limits being exceeded. Must be a whole number. Defaults to 3 retries (default 3)
      --rate-limit-retry-backoff-secs float   The amount of time in seconds to wait before retrying a command if it failed due to rate limits being exceeded. Defaults to 1 second. (default 1)
```

### SEE ALSO

* [galasactl streams](galasactl_streams.md)	 - Manages test streams in a Galasa service

