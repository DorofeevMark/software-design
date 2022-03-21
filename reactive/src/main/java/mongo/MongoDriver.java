package mongo;


import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.Success;
import model.Product;
import model.User;
import rx.Observable;

public class MongoDriver {
    private final MongoClient client;

    private static final String DATABASE = "reactive";
    private static final String USERS_COLLECTION = "users";
    private static final String PRODUCTS_COLLECTION = "products";

    public MongoDriver(MongoClient client) {
        this.client = client;
    }

    public Observable<Success> createUser(User user) {
        return client.getDatabase(DATABASE)
                .getCollection(USERS_COLLECTION)
                .insertOne(user.toDocument());
    }

    public Observable<User> getUserById(long id) {
        return client.getDatabase(DATABASE)
                .getCollection(USERS_COLLECTION)
                .find(Filters.eq("id", id))
                .toObservable()
                .map(User::new);
    }

    public Observable<Success> createProduct(Product product) {
        return client.getDatabase(DATABASE)
                .getCollection(PRODUCTS_COLLECTION)
                .insertOne(product.toDocument());
    }

    public Observable<Product> getAllProducts() {
        return client.getDatabase(DATABASE)
                .getCollection(PRODUCTS_COLLECTION)
                .find()
                .toObservable()
                .map(Product::new);
    }
}
