package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.mongodb.rx.client.MongoClients;
import model.Currency;
import model.Product;
import model.User;
import io.reactivex.netty.protocol.http.server.HttpServer;
import mongo.MongoDriver;
import mongo.MongoFields;
import rx.Observable;

public class Server {
    private final MongoDriver mongoDriver;
    private final Map<Action, Function<Map<String, List<String>>, Observable<String>>> actions;
    public enum Action {
        REGISTER, ADD, GET
    }

    public Server() {
        mongoDriver = new MongoDriver(MongoClients.create());

        actions = new HashMap<>();
        actions.put(Action.REGISTER, this::register);
        actions.put(Action.ADD, this::add);
        actions.put(Action.GET, this::get);
    }

    public void start(int port) {
        HttpServer
                .newServer(port)
                .start((req, resp) -> {
                    String action = req.getDecodedPath().substring(1);
                    try {
                        Observable<String> result =
                                actions.get(Action.valueOf(action.toUpperCase())).apply(req.getQueryParameters());
                        return resp.writeString(result);
                    } catch (Exception e) {
                        return resp.writeString(Observable.just(e.getMessage()));
                    }
                }).awaitShutdown();
    }

    public Observable<String> register(Map<String, List<String>> queryParams) {
        long id = Integer.parseInt(queryParams.get(MongoFields.id).get(0));
        Currency currency = Currency.valueOf(queryParams.get(MongoFields.currency).get(0).toUpperCase());

        return mongoDriver.createUser(new User(id, currency)).map(s -> String.format("User %s with currency %s was created", id, currency.name()));
    }

    public Observable<String> add(Map<String, List<String>> queryParams) {
        String name = queryParams.get(MongoFields.name).get(0);
        double price = Double.parseDouble(queryParams.get(MongoFields.price).get(0));
        Currency currency = Currency.valueOf(queryParams.get(MongoFields.currency).get(0).toUpperCase());

        return mongoDriver.createProduct(new Product(name, price, currency)).map(s -> String.format("Product %s with price %s %s was created", name, price, currency.name()));
    }

    public Observable<String> get(Map<String, List<String>> queryParams) {
        long id = Integer.parseInt(queryParams.get(MongoFields.id).get(0));

        return mongoDriver.getUserById(id)
                .map(User::getCurrency)
                .switchMap(userCurrency -> mongoDriver.getAllProducts()
                        .map(product -> String.format("%s = %s%s \n", product.getName(), product.getCurrency().convert(product.getPrice(), userCurrency), userCurrency.name())));
    }
}
