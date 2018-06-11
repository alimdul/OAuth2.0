package dao;

import connection.ConnectionPool;
import connection.ProxyConnection;
import entity.Entity;

public abstract class AbstractDAO<T extends Entity> implements AutoCloseable {

    protected ProxyConnection connection;

    @Override
    public void close() {
        ConnectionPool.getInstance().releaseConnection(connection);
    }
}
