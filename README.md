# time-service
A service where users can register to receive time updates at a user-defined frequency.

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

Here is a cURL request targeting the `localhost`:

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
  "url": "https://my-callback-url.com"
}
```

Here is a cURL request targeting localhost:

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
