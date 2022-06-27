# time-service
![CI status](https://github.com/RichardInnocent/time-service/workflows/ci/badge.svg)

A service where users can register to receive time updates at a user-defined frequency.

## Running the application

### Prerequisites
You'll need to install the following:
- Java (version 15 or later)
- Apache Maven

### Building the jar
The jar can be built with the following command:
```bash
mvn package
```

### Running the jar
The jar, typically located in the `target` directory, can be run with the following command:
```bash
java -jar time-service.jar
```

## How to use this service

### Registering a callback
To register a callback, you can make a `POST` request to `/frequency`. Ensure that the content type
is set to `application/json`. Here is an example payload:
```json
{
  "frequencySeconds": 30,
  "url": "https://my-callback-url.com"
}
```

Here is a cURL request targeting `localhost`:

```bash
curl \
-X POST \
-H "Content-Type: application/json" \
-d '{"frequencySeconds": 30, "url": "https://my-callback-url.com"}' \
http://localhost:8080/callbacks
```

### Modifying the frequency of updates
To modify the frequency of updates, you can make a `PUT` request to `/callbacks`, specifying the URL
of the callback you wish to edit as a request parameter. Ensure that the content type is set to
`application/json`. Here is an example payload:
```json
{
  "frequencySeconds": 15
}
```

Here is a cURL request targeting `localhost`:

```bash
curl \
-X PUT \
-H "Content-Type: application/json" \
-d '{"frequencySeconds": 30}' \
"http://localhost:8080/callbacks?url=https://my-callback-url.com"
```

### De-registering a callback
To stop receiving updates, you must de-register your callback. To do this, make a `DELETE` request
to `/callbacks`, specifying the URL of the callback you wish to delete as a request parameter.

Here is a cURL request targeting `localhost`:

```bash
curl -X DELETE "http://localhost:8080/callbacks?url=https://my-callback-url.com"
```

## Improvements

### Checking for reachable endpoints
It's currently possible to register a URL that is  unreachable by this service, provided that it is
a valid URL. In the long-term, this may drain resources if there are lots of registered callbacks
that are unreachable (and therefore useless). We could attempt to send the request first to see if
it's reachable, and return a bad request if not.

### Improving error messages
We have response codes like Bad Request returned from our API but the response bodies are not at all
helpful currently. This could greatly be improved.

## Make more RESTful
The URLs for the `PUT` and `DELETE` routes aren't particularly nice to interface with. To be more
RESTful, I'd rather have each registered callback receive an ID that could then be referenced
directly as a path variable. i.e., rather than
```bash
curl -X DELETE "http://localhost:8080/callbacks?url=https://my-callback-url.com"
```

it could be
```bash
curl -X DELETE "http://localhost:8080/callbacks/1a22b3c4d5e6f"
```
where `1a22b3c4d5e6f` is the ID of the callback.

I avoided this as the specification explicitly stated that the path should contain the URL of the
callback. I _could_ have used the URL as the ID itself, but this is a bad design decision for two
primary reasons:
1. You'd end up with URL-encoded IDs. Not sure if this would actually be a problem, but it would
look odd.
2. You'd be tied to only ever being able to have one registered scheduled update per URL. This is
clearly the intended case based off of this specification, but needlessly limits for extensibility
of the service.

Additionally, the `DELETE` route would ideally return the resource that was deleted.

As a small side-note, it may also be useful to provide a `GET` route which returns the current
frequency configuration for that URL/ID.

### Logging
This service would be currently very hard to monitor as there is no logging or metrics. This should
be woven into the application.

### Decide on what we want to do if a request fails
What do we want to do if a callback fails to send? Nothing? Retry? We currently swallow this error
which isn't great.
