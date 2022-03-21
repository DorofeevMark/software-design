package mongo;

import org.bson.Document;

public interface MongoEntity {
    Document toDocument();
}

