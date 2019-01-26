package com.josamuna.smartmanagerest.classes;

import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryJava {
    private static final FactoryJava staticFact = new FactoryJava();

    public static FactoryJava getInstance() {
        return staticFact;
    }

    private FactoryJava() {
    }

    public void executeConnection(ConnectionClass connexion) throws SQLException, ClassNotFoundException{
//        String strConnect = null;
//        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String url = String.format("jdbc:jtds:sqlserver://{0};{1};{2};{3}",connexion.getServerName(), connexion.getDatabase(), connexion.getUserID(), connexion.getPassword());
            DriverManager.getConnection(url);
//            strConnect = "Login Succeced !!!";
//        }catch (SQLException ex) {
//            Log.e("Error", ex.getMessage());
//            strConnect = "Failed to connect to SQL Server DataBase";
//        }catch (ClassNotFoundException ex){
//            Log.e("Error", ex.getMessage());
//            strConnect = "Failed to find SQL Server Driver";
//        }catch (Exception ex){
//            Log.e("Error", ex.getMessage());
//            strConnect = "Failed to connect to SQL Server DataBase";
//        }
//        return strConnect;
    }
}
