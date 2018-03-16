# NearIt-UI for notification inbox

Showing the history of the user near notifications and content, is a common feature of apps integrated with NearIT.
With NearIt-UI you can launch an activity or get a fragment that automatically fetches and displays notifications and content ordered by received date.

## Basic example
With these few lines of code

```java
JAVA
startActivity(NearITUIBindings.getInstance(this)
                .createInboxListIntentBuilder()
                .build())
```

```kotlin
KOTLIN
startActivity(NearITUIBindings.getInstance(this@YourActivity)
                .createInboxListIntentBuilder()
                .build())
```

you can show the user notification history with our proposed filter:
- no coupons (see [the coupon list section](COUPON_LIST.md))
- no custom jsons

The included content is simple notifications, content with attachments and feedback requests.