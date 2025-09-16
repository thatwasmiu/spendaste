db = db.getSiblingDB("daste"); // create/use the daste DB

db.createUser({
    user: "daste",
    pwd: "daste",
    roles: [
        { role: "readWrite", db: "daste" }
    ]
});

// Optionally, create an initial collection with a doc
db.testCollection.insertOne({ init: true });
