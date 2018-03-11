# Simple Product Service

### REST API

* **GET /products** - get all products
* **POST /products/apply** - get all products applicable for given client info. Format is described in **Client info format** section. 
* **GET /products/{productId}/rules** - get product rules
* **POST /products/{productId}/rules** - add rule to a product. Format is described in **Rule creation format** section.
* **DELETE /products/{productId}/rules/{ruleId}** - delete rule

####  Client info format
Example: {"salary": 50000, "claim": 50000, "debtor": false}

####  Rule creation format
Rule is defined as Spring Expression Language (SpEL) expression. Such expression must calculate boolean value using client info properties: salary, claim, debtor (and new properties if added).

Example: {"expression": "salary > 50000 and not debtor"}.

### Build

Requirements: JDK 8, JAVA_HOME environment variable set.

Linux: `./mvnw clean package`

Windows: `mvnw clean package`