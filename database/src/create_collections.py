from pymongo import MongoClient

mongo_uri = "mongodb://localhost:27017"
client = MongoClient(mongo_uri)  # assuming MongoDB is running on localhost
db = client.hardwarestore

# create a collection with validation rules
db.create_collection("users", validator={
    "$jsonSchema": {
        "bsonType": "object",
        "required": ["name", "last_name", "username", "birthday", "city_birth", "status", "credentials"],
        "properties": {
            "name": {
                "bsonType": "string",
                "description": "must be a string and is required"
            },
            "last_name": {
                "bsonType": "string",
                "description": "must be a string and is required"
            },
            "username": {
                "bsonType": "string",
                "description": "must be a string and is required",
            },
            "birthday": {
                "bsonType": "string",
                "description": "must be a date and is required"
            },
            "city_birth": {
                "bsonType": "string",
                "description": "must be a string and is required"
            },
            "status": {
                "bsonType": "string",
                "description": "must be a string",
            },
            "credentials": {
                "type": "array",
                "items": {
                    "bsonType": "objectId"
                },
                "uniqueItems": True,
                "minItems": 1,  # require at least one element in the array
                "description": "must be a non-empty array of ObjectIds"
            }
        }
    }
})
db.users.create_index("username", unique=True)
db.create_collection("credentials", validator={
    "$jsonSchema": {
        "bsonType": "object",
        "required": ["email", "password"],
        "properties": {
            "email": {
                "bsonType": "string",
                "description": "must be a string and is required",
            },
            "password": {
                "bsonType": "string",
                "description": "must be a string and is required",
            }
        }
    }
})
db.credentials.create_index("email", unique=True)
db.create_collection("tools", validator={
    "$jsonSchema": {
        "bsonType": "object",
        "required": ["name", "description", "brand", "price", "amount", "cities"],
        "properties": {
            "name": {
                "bsonType": "string",
                "description": "must be a string and is required",
            },
            "description": {
                "bsonType": "string",
                "description": "must be a string and is required",
            },
            "brand_id": {
                "bsonType": "objectId",
                "description": "must be a objectId and is required",
            },
            "price": {
                "bsonType": "double",
                "description": "must be a double and is required"
            },
            "amount": {
                "bsonType": "int",
                "description": "must be a int and is required"
            },
            "cities": {
                "type": "array",
                "items": {
                    "bsonType": "objectId",
                },
                "description": "must be an array of ObjectIds"
            }
        }
    }
})
# db.orders.aggregate([
#     {
#         "$lookup": {
#             "from": "customers",
#             "localField": "customer_id",
#             "foreignField": "_id",
#             "as": "customer"
#         }
#     },
#     {
#         "$match": {
#             "customer": {"$ne": []}
#         }
#     }
# ])

db.create_collection("brands", validator={
    "$jsonSchema": {
        "bsonType": "object",
        "required": ["name", "description", "foundation_year"],
        "properties": {
            "name": {
                "bsonType": "string",
                "description": "must be a string and is required",
            },
            "description": {
                "bsonType": "string",
                "description": "must be a string and is required"
            },
            "foundation_year": {
                "bsonType": "string",
                "description": "must be a string and is required"
            }
        }
    }
})
db.brands.create_index("name", unique=True)
db.create_collection("cities", validator={
    "$jsonSchema": {
        "bsonType": "object",
        "required": ["name"],
        "properties": {
            "name": {
                "bsonType": "string",
                "description": "must be a string and is required",
            }
        }
    }
})
db.cities.create_index("name", unique=True)
