# Embedded Document Example in Java
An example java application to demonstrate how flexible "document APIs" are and how documents can be evaluated by inspecting them. The original clojure version is: [github.com/cpbirch/document-pattern](https://github.com/cpbirch/document-pattern)

In financial institutions or in government, there is a common user-journey pattern:

APPLY -> ASSESS -> PAY -> REPAY (financial institutions usually ask their customers to repay loans, repay is not usually a government concern)

Consider the example of a mortgage, banks vary their terms and conditions over periods of time and have different demands on evidence, verification etc.  In a relational database, ORM and fine grained object world, the application form is closely coupled to storage and to APIs for evaluating mortgage applications.  This means a change in policy requires a change in all of the steps of the user-journey above.

This code base serves as a generic example to show how the right level of encapsulation allows applications to be decoupled from their processing.

I'd like to thank Jim Barrit, Shodan Seth for defining this approach and to Martin Fowler for giving me the courage to do this at a client.

## Getting Started

Checkout the source, then on the command line enter

    gradle test

To run the code, try

    gradle run

## NONE Flexible Data Creation Examples

First, here's the antipattern which is implemented following the traditional pattern of mapping JSoN to a Java object model (POJOs).

Create a ship document with an equipment ID of 1

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "create", "equipmentId": "1", "type": "Ship"}' http://localhost:8080/equipment


Now add additional information about the ship

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "update", "equipmentId": "1", "name": "RRS Boaty McBoatface"}' http://localhost:8080/equipment

To see the full document, try

    curl http://localhost:8080/equipment/1

Now try to add a couple of extra fields

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "update", "equipmentId": "1", "length": 129, "width": 24}' http://localhost:8080/equipment

Do these exist in the store document?  (Spoiler, length and width are missing)

    curl http://localhost:8080/equipment/1

The simple method for more complex types is to create a new, more specific interface, so try the following steps to create a specific SHIP equipment object.

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "create", "equipmentId": "1", "type": "Ship"}' http://localhost:8080/equipment/ship

You'll notice that the returned object includes fields that weren't in the json posted.

We'll add a bit more detail to update the null or zero values returned, plus we'll throw in some extra data for fun.

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "create", "equipmentId": "1", "name": "Boat Marley and the Whalers", "sub-type": "Research Vessel", "length": 129, "width": 24, "tonnage": 15000}' http://localhost:8080/equipment/ship

Fetch the object back and see if "sub-type" which we posted exists.  Spoiler: it doesn't.

    curl http://localhost:8080/equipment/ship/1

The above examples are a laborious and simplistic example of the difficulties that arise when disregarding the right level of encapsulation.  Generally, if you have a deep object model with just getters and setters, then your encapsulation of the interface for that data is possibly wrong.  Often, some java frameworks offer validation on fields via annotations which people think forces them to use this broken pattern - inspection using XPath or an implementation of JSoN Schema is a possible flexible alternative.


## Flexible Data Creation Examples

Just like the original Clojure example, a simple type of encapsulation can be achieved (with a little more boiler plate code than Clojure!)

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "create", "equipmentId": "1", "type": "Ship"}' http://localhost:8080/equipment/doc

Checking the response shows that no extra random fields are returned.

Try the following example to add more fields or complex JSoN types.  Feel free to alter the example and make further posts.

    curl -H "Content-Type: application/json" -X POST -d '{"eventType": "update", "equipmentId": "1", "name": "RRS Boaty McBoatface", "sub-type": "Research Vessel", "length": 129, "helicopters": 2}' http://localhost:8080/equipment/doc

Fetch the aggregated document, all your fields should be displayed.

    curl http://localhost:8080/equipment/doc/1

Feel free to carry on posting more data and adding fields.


## Policy Inspections

Now you're free to pipe your stored equipment definitions to a URL to evaluate for insurance purposes.  Try the following:

    curl http://localhost:8080/equipment/doc/1 | curl -H "Content-Type: application/json" -X POST -d @- http://localhost:8080/insure/ship

Or if you'd prefer to insure a spacecraft:

    curl http://localhost:8080/equipment/doc/1 | curl -H "Content-Type: application/json" -X POST -d @- http://localhost:8080/insure/spacecraft

