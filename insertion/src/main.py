import json
import random
from pymongo import MongoClient, InsertOne 

mongoUri: str = "mongodb://localhost:27017"
client = MongoClient(mongoUri)
db = client.hardwarestore


def addCityAndBrandToTool(tool: dict) -> dict:
    tool["brand"] = random.choice(brand_ids)
    num_cities = random.randint(1, len(city_ids))
    tool["cities"] = random.sample(city_ids, num_cities)
    
def addJsonToDB( path: str, collection, isTool: bool = False):
    requesting = []
    with open(rf"{path}") as f:
        data = json.load(f)
        for dictObject in data:
            if isTool:
                addCityAndBrandToTool(dictObject)
            requesting.append(InsertOne(dictObject))
    return collection.bulk_write(requesting)


addJsonToDB("../db/cities.json", db.cities)
addJsonToDB("../db/brands.json", db.brands)
city_ids = [(city["_id"]) for city in db.cities.find({}, {"_id": 1})]
brand_ids = [(city["_id"]) for city in db.brands.find({}, {"_id": 1})]
addJsonToDB("../db/tools.json", db.tools, True)

client.close()