package model;

import lombok.AllArgsConstructor;
import lombok.Value;
import mongo.MongoEntity;
import mongo.MongoFields;
import org.bson.Document;

@Value
@AllArgsConstructor
public class Product implements MongoEntity {
    String name;
    double price;
    Currency currency;

    public Product(Document document) {
        name = document.getString(MongoFields.name);
        price = document.getDouble(MongoFields.price);
        currency = Currency.valueOf(document.getString(MongoFields.currency));
    }


    @Override
    public Document toDocument() {
        return new Document().append(MongoFields.name, name).append(MongoFields.price, price).append(MongoFields.currency, currency.toString());
    }
}
