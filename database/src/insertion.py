import json
import random
from pymongo import MongoClient, InsertOne

from datetime import datetime

mongoUri: str = "mongodb://localhost:27017"
client = MongoClient(mongoUri)
db = client.hardwarestore


# Add data to database Collections using JSON files
def addJsonToDB(path: str, collection, type: str = '') -> None:
    requesting = []
    with open(rf"{path}") as f:
        data = json.load(f)
        for dictObject in data:
            if type == "tool":
                addCityAndBrandToTool(dictObject)
            if type == "user":
                #Bogota timezone
                # tz = datetime.now().astimezone().tzinfo
                # dictObject["last_login"] = datetime.utcnow()
                # dictObject["birthday"] = datetime.fromisoformat(dictObject["birthday"])
                addCredentialsToUser(dictObject)
            if type == "brand":
                pass
                # dictObject["foundation_date"] = datetime.fromisoformat(dictObject["foundation_date"])
            requesting.append(InsertOne(dictObject))
    return collection.bulk_write(requesting)


# Add random cities and brands to tools
def addCityAndBrandToTool(tool: dict) -> dict:
    tool["brand"] = random.choice(brand_ids)
    num_cities = random.randint(1, len(city_ids))
    tool["cities"] = random.sample(city_ids, num_cities)


def addCredentialsToUser(tool: dict) -> dict:
    sample = random.sample(credentials_ids, 1)
    # delete that sample from the credentials_id array
    credentials_ids.remove(sample[0])
    tool["credentials"] = sample


# Add data to database Collections that are not dependent on other collections
addJsonToDB("../db/cities.json", db.cities)
addJsonToDB("../db/brands.json", db.brands, "brand")
addJsonToDB("../db/credentials.json", db.credentials)
# Add data to database Collections that are dependent on other collections
city_ids = [(city["_id"]) for city in db.cities.find({}, {"_id": 1})]
brand_ids = [(brand["_id"]) for brand in db.brands.find({}, {"_id": 1})]
credentials_ids = [(credential["_id"]) for credential in db.credentials.find({}, {"_id": 1})]

addJsonToDB("../db/tools.json", db.tools, "tool")
addJsonToDB("../db/users.json", db.users, "user")
client.close()
