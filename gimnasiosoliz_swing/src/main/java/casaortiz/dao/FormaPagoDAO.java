/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casaortiz.dao;

import casaortiz.db.Conector;
import casaortiz.model.FormaPago;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class FormaPagoDAO {
    
    private Conector conector = new Conector();
    
    public boolean guardar(FormaPago item){
        Connection connect = null;
        try {
            connect = conector.getConexion();
            PreparedStatement st = connect.prepareStatement("insert into forma_pago (nombre, descripcion) values (?,?)");
            st.setString(1, item.getNombre());
            st.setString(2, item.getDescripcion());
            st.execute();
            conector.close(connect);
            return true;
        } catch (SQLException ex) { 
            System.err.println(ex.getMessage());
            conector.close(connect);
            return false;
        }
    }
    
    public FormaPago getFormaPago(int id){
        Connection connect = null;
        ResultSet result = null;
        connect = conector.getConexion();
        FormaPago item = null;
        try {
            connect = conector.getConexion();
            PreparedStatement st = connect.prepareStatement("select * from forma_pago fp where fp.id = "+id);
            result = st.executeQuery();
            item = new FormaPago();
            item.setId(result.getInt("id"));
            item.setNombre(result.getString("nombre"));
            item.setDescripcion(result.getString("descripcion"));
            conector.close(connect);
            return item;
        } catch (SQLException ex) { 
            System.err.println(ex.getMessage());
            conector.close(connect);
            return null;
        }
    }
    
    public boolean actualizar(FormaPago item){
        Connection connect = null;
        ResultSet result = null;
        connect = conector.getConexion();
        try {
            connect = conector.getConexion();
            PreparedStatement st = connect.prepareStatement("update forma_pago set nombre = ?, descripcion = ? where id = ?");
            st.setString(1, item.getNombre());
            st.setString(2, item.getDescripcion());
            st.setInt(3, item.getId());
            st.execute();
            conector.close(connect);
            return true;            
        } catch (SQLException ex) { 
            System.err.println(ex.getMessage());
            conector.close(connect);
            return false;
        }
    }
    
    public boolean eliminar(int id){
        Connection connect = null;
        ResultSet result = null;
        connect = conector.getConexion();
        try {
            System.out.println(id);
            connect = conector.getConexion();
            PreparedStatement st = connect.prepareStatement("delete from forma_pago where id = "+id);
            st.executeUpdate();
            conector.close(connect);
            return true;            
        } catch (SQLException ex) { 
            System.err.println(ex.getMessage());
            conector.close(connect);
            return false;
        }
    }
    
    public List<FormaPago> getFormaPagos() throws SQLException{
        Connection connect = null;
        ResultSet result = null;
        connect = conector.getConexion();
        try{
            PreparedStatement st = connect.prepareStatement("select * from forma_pago");
            result = st.executeQuery();
            List<FormaPago> items = new ArrayList<FormaPago>();
            while(result.next()){
                FormaPago item = new FormaPago();
                item.setId(result.getInt("id"));
                item.setNombre(result.getString("nombre"));
                item.setDescripcion(result.getString("descripcion"));
                items.add(item);
            }
            connect.close();
            return items;
        }catch(SQLException ex){
            connect.close();
            return null;
                
        }
        
    }
    
}
