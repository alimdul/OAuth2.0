package connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger();

    private final static String PATH_TO_DATABASE_PROPERTIES = "property/database.properties";
    private final static String DATABASE_URL = "url";
    private final static String DATABASE_POOL_SIZE = "poolSize";

    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static ReentrantLock locker = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;
    private int poolSize;

    private ConnectionPool() {
        if(instance != null) {
            LOGGER.log(Level.WARN, "- Attempt to re-create an object of ConnectionPool"); // защита от reflection
        }
        registerDriver();
        initPool();
    }

    public static ConnectionPool getInstance() {
        if(!instanceCreated.get()) {
            locker.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    public void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "- Unable to load driver");
            throw new RuntimeException("Unable to load driver",e);
        }
    }

    public void initPool() {
        Properties properties = new Properties();
        String url = null;
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(PATH_TO_DATABASE_PROPERTIES));
            url = properties.getProperty(DATABASE_URL);
            poolSize = Integer.parseInt(properties.getProperty(DATABASE_POOL_SIZE));
        } catch (IOException e) {
            //log
        }

        connectionQueue = new LinkedBlockingQueue<>(poolSize);

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "- Fail with getting connection from driver manager");
            throw new RuntimeException("Fail with getting connection from driver manager", e);
        }

        for(int i=0; i<poolSize; i++) {
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            try {
                connectionQueue.put(proxyConnection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, "- Fail with put connection into connection pool");
            }
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "- Fail with take connection from connection pool");
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection proxyConnection) {
        try {
            if(!proxyConnection.getAutoCommit()) {
                proxyConnection.setAutoCommit(true);
            }
            try {
                connectionQueue.put(proxyConnection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, "- Fail with release connection into connection pool");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "- Connection can not be set to auto commit");
        }
    }

    public void destroyAllConnections() {
        ProxyConnection proxyConnection = null;
        for(int i=0; i<connectionQueue.size(); i++) {
            try {
                proxyConnection = connectionQueue.take();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, "- Fail with take connection from connection pool");
            } finally {
                if(proxyConnection != null) {
                    try {
                        proxyConnection.closeConnection();
                    } catch (SQLException e) {
                        LOGGER.log(Level.WARN, "- Connection can not be close");
                    }
                }
            }
        }

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while(drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOGGER.log(Level.WARN, "- Error deregister driver");
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        if (instance != null) {
            LOGGER.log(Level.WARN, "- Attempt to re-create an object of ConnectionPool");
        }
        return super.clone();
    }
}
