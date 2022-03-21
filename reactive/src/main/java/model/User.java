package model;

import lombok.AllArgsConstructor;
import lombok.Value;
import mongo.MongoEntity;
import mongo.MongoFields;
import org.bson.Document;

@Value
@AllArgsConstructor
public class User implements MongoEntity {
    long id;
    Currency currency;

    public User(Document document) {
        id = document.getLong(MongoFields.id);
        currency = Currency.valueOf(document.getString(MongoFields.currency));
    }

    @Override
    public Document toDocument() {
        return new Document().append(MongoFields.id, id).append(MongoFields.currency, currency.toString());
    }
}
