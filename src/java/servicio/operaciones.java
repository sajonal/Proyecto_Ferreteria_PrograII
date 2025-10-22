/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.print.attribute.standard.PDLOverrideSupported;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;



/**
 * REST Web Service
 *
 * @author jona
 */
@Path("generic")
public class operaciones {
Conexion cn = new Conexion();
Connection con;
PreparedStatement ps;
ResultSet rs;
    @Context
    private UriInfo context;

    public List<Usuarios> Consultar(){
        List<Usuarios> lista = new ArrayList<Usuarios>();
        String sql = "select * from usuarios";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                Usuarios u = new Usuarios();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setNombre(rs.getString("nombre"));
                u.setTipo(rs.getString("tipo"));
                lista.add(u);
            }
            return lista;
        }catch(Exception e){
            return lista;
        }
    }
    
    @GET
    @Path("/lista")
    public List<Usuarios> listar(){
        return (Consultar());
    }
    
    @POST
    @Path("/agregar")
    @Produces("application/json")
    @Consumes("application/json")
    public String agregar(Usuarios u){
        String sql="insert into usuarios(id_usuario,usuario,clave,nombre,tipo) values(?,?,?,?,?)";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, u.getId_usuario());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getClave());
            ps.setString(4, u.getNombre());
            ps.setString(5, u.getTipo());
            ps.executeUpdate();
            return "Insercion realizada con exito";
        }catch(Exception e){
             return "Insercion fallida "+e.getMessage();
        }
    }
    
    @PUT
    @Path("/modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modificar(Usuarios u){
        String sql="update usuarios set usuario=?,clave=? , nombre=?, tipo=? where id_usuario=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getTipo());
            ps.setInt(5, u.getId_usuario());
            ps.executeUpdate();
            return "Modificacion realizada con exito";
        }catch(Exception e){
             return "Modificacion fallida "+e.getMessage();
        }
    }
    
    
    @DELETE
    @Path("/Eliminar/{id}")
    public String Eliminar(@PathParam("id") String id){
        String sql="delete from usuarios where id_usuario=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            return "Eliminacion realizada con exito";
        }catch(Exception e){
             return "Eliminacion fallida "+e.getMessage();
        }
    }
    
    
    @GET 
    @Path("/Consultarx/{id}")
    public List<Usuarios> Consultarx(@PathParam("id") String id){
        List<Usuarios> Lista = new ArrayList<Usuarios>();
        String sql = "select * from usuarios where id_usuario=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while ( rs.next()) {
                Usuarios u = new Usuarios();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setNombre(rs.getString("nombre"));
                u.setTipo(rs.getString("tipo"));
                Lista.add(u);
                
                
            }
        } catch (Exception e) {
        }
        return Lista;
    }
//}



// PRODUCTOSSSSSSSS


    //Conexion cn = new Conexion();
    //Connection con;
    //PreparedStatement ps;
    //ResultSet rs;

    //@Context
    //private UriInfo context;

    public List<productos> ConsultarProdct() {
        List<productos> lista = new ArrayList<productos>();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                productos p = new productos();
                p.setId_producto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getFloat("precio"));
                p.setExistencia(rs.getInt("existencia"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error consultando productos: " + e.getMessage());
        }
        return lista;
    }

    @GET
    @Path("/listaProductos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<productos> listarProd() {
        return ConsultarProdct();
    }

    @POST
    @Path("/agregaProductos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarProd(productos p) {
        String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio, existencia) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getExistencia());
            ps.executeUpdate();
            return "Producto agregado con éxito";
        } catch (Exception e) {
            return "Error al agregar producto: " + e.getMessage();
        }
    }

    @PUT
    @Path("/modificarProductos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modificarProd(productos p) {
        String sql = "UPDATE productos SET codigo=?, nombre=?, descripcion=?, precio=?, existencia=? WHERE id_producto=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getExistencia());
            ps.setInt(6, p.getId_producto());
            ps.executeUpdate();
            return "Producto modificado con éxito";
        } catch (Exception e) {
            return "Error al modificar producto: " + e.getMessage();
        }
    }

    @DELETE
    @Path("/eliminarProducto/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String eliminarProd(@PathParam("id") int id) {
        String sql = "DELETE FROM productos WHERE id_producto=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return "Producto eliminado con éxito";
        } catch (Exception e) {
            return "Error al eliminar producto: " + e.getMessage();
        }
    }

    @GET
    @Path("/consultarProducto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public productos consultarPorIdProd(@PathParam("id") int id) {
        productos p = new productos();
        String sql = "SELECT * FROM productos WHERE id_producto=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId_producto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getFloat("precio"));
                p.setExistencia(rs.getInt("existencia"));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar producto: " + e.getMessage());
        }
        return p;
    }
//}



///// FACTURAS 

//@Path("facturas")
//public class FacturasService {

    //Conexion cn = new Conexion();
    //Connection con;
    //PreparedStatement ps;
    //ResultSet rs;

    //@Context
    //private UriInfo context;

    public List<Facturas> ConsultarFact() {
        List<Facturas> lista = new ArrayList<Facturas>();
        String sql = "SELECT * FROM facturas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Facturas f = new Facturas();
                f.setId_factura(rs.getInt("id_factura"));
                f.setId_usuario(rs.getInt("id_usuario"));
                f.setNumero_factura(rs.getString("numero_factura"));
                f.setFecha(rs.getTimestamp("fecha"));
                f.setCliente(rs.getString("cliente"));
                f.setForma_pago(rs.getString("forma_pago"));
                f.setSubtotal(rs.getFloat("subtotal"));
                f.setTotal(rs.getFloat("total"));
                lista.add(f);
            }
        } catch (Exception e) {
            System.out.println("Error consultando facturas: " + e.getMessage());
        }
        return lista;
    }

    @GET
    @Path("/listaFacturas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Facturas> listarFact() {
        return ConsultarFact();
    }

    @POST
    @Path("/agregarFactura")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarFact(Facturas f) {
        String sql = "INSERT INTO facturas (id_usuario, numero_factura, cliente, forma_pago, subtotal, total) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, f.getId_usuario());
            ps.setString(2, f.getNumero_factura());
            ps.setString(3, f.getCliente());
            ps.setString(4, f.getForma_pago());
            ps.setDouble(5, f.getSubtotal());
            ps.setDouble(6, f.getTotal());
            ps.executeUpdate();
            return "Factura agregada con éxito";
        } catch (Exception e) {
            return "Error al agregar factura: " + e.getMessage();
        }
    }

    @PUT
    @Path("/modificarFactura")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modificarFact(Facturas f) {
        String sql = "UPDATE facturas SET id_usuario=?, numero_factura=?, cliente=?, forma_pago=?, subtotal=?, total=? WHERE id_factura=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, f.getId_usuario());
            ps.setString(2, f.getNumero_factura());
            ps.setString(3, f.getCliente());
            ps.setString(4, f.getForma_pago());
            ps.setDouble(5, f.getSubtotal());
            ps.setDouble(6, f.getTotal());
            ps.setInt(7, f.getId_factura());
            ps.executeUpdate();
            return "Factura modificada con éxito";
        } catch (Exception e) {
            return "Error al modificar factura: " + e.getMessage();
        }
    }

    @DELETE
    @Path("/eliminarFactura/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String eliminarFact(@PathParam("id") int id) {
        String sql = "DELETE FROM facturas WHERE id_factura=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return "Factura eliminada con éxito";
        } catch (Exception e) {
            return "Error al eliminar factura: " + e.getMessage();
        }
    }

    @GET
    @Path("/consultarFactura/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Facturas consultarPorIdFact(@PathParam("id") int id) {
        Facturas f = new Facturas();
        String sql = "SELECT * FROM facturas WHERE id_factura=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                f.setId_factura(rs.getInt("id_factura"));
                f.setId_usuario(rs.getInt("id_usuario"));
                f.setNumero_factura(rs.getString("numero_factura"));
                f.setFecha(rs.getTimestamp("fecha"));
                f.setCliente(rs.getString("cliente"));
                f.setForma_pago(rs.getString("forma_pago"));
                f.setSubtotal(rs.getFloat("subtotal"));
                f.setTotal(rs.getFloat("total"));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar factura: " + e.getMessage());
        }
        return f;
    }
//}
    
    
    
    
    // OTROS METODOS -- ESTE ES INGRESO ALMACEN 
    
    public List<ingresos_almacen> ConsultarI(){
        List<ingresos_almacen> lista = new ArrayList<ingresos_almacen>();
        String sql = "select * from detalle_ingreso";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                ingresos_almacen i = new ingresos_almacen();
                i.setId_ingreso(rs.getInt("id_ingreso"));
                i.setId_usuario(rs.getInt("id_usuario"));
                i.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                i.setProveedor(rs.getString("proveedor"));
                i.setFactura_proveedor(rs.getString("factura_proveedor"));
                i.setTotal(rs.getFloat("Total"));
                lista.add(i);
            }
            return lista;
        }catch(Exception e){
            return lista;
        }
    }
    
    @GET
    @Path("/listaA")
    public List<ingresos_almacen> listarI(){
        return (ConsultarI());
    }
    
    @POST
    @Path("/agregarA")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarI(ingresos_almacen i){
        String sql="insert into ingreso_almacen(id_ingreso,id_usuario,fecha_ingreso,proveedor,factura_proveedor,total) values(?,?,?,?,?,?)";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, i.getId_ingreso());
            ps.setInt(2, i.getId_usuario());
            ps.setTimestamp(3, i.getFecha_ingreso());
            ps.setString(4, i.getProveedor());
            ps.setString(5, i.getFactura_proveedor());
            ps.setFloat(5, i.getTotal());
            ps.executeUpdate();
            return "Insercion realizada con exito";
        }catch(Exception e){
             return "Insercion fallida "+e.getMessage();
        }
    }
    
    @PUT
    @Path("/modificarA")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modificarI(ingresos_almacen i){
        String sql="update ingreso_almacen set fecha_ingreso=?, proveedor=?, factura_proveedor=? where id_ingreso=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setTimestamp(1, i.getFecha_ingreso());
            ps.setString(2, i.getProveedor());
            ps.setString(3, i.getFactura_proveedor());
            ps.setFloat(4, i.getTotal());
            ps.executeUpdate();
            return "Modificacion realizada con exito";
        }catch(Exception e){
             return "Modificacion fallida "+e.getMessage();
        }
    }
    
    
    @DELETE
    @Path("/EliminarA/{id}")
    public String EliminarI(@PathParam("id") String id){
        String sql="delete from ingreso_almacen where id_ingreso=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            return "Eliminacion realizada con exito";
        }catch(Exception e){
             return "Eliminacion fallida "+e.getMessage();
        }
    }
    
    
    @GET 
    @Path("/ConsultarxA/{id}")
    public List<ingresos_almacen> ConsultarxI(@PathParam("id") String id){
        List<ingresos_almacen> Lista = new ArrayList<ingresos_almacen>();
        String sql = "select * from ingreso_almacen where id_ingreso=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while ( rs.next()) {
                ingresos_almacen i = new ingresos_almacen();
                i.setId_ingreso(rs.getInt("id_ingreso"));
                i.setId_usuario(rs.getInt("id_usuario"));
                i.setFecha_ingreso(rs.getTimestamp("fewcha_ingreso"));
                i.setProveedor(rs.getString("Proveedor"));
                i.setFactura_proveedor(rs.getString("factura_proveedor"));
                i.setTotal(rs.getFloat("total"));
                Lista.add(i);
                
                
            }
        } catch (Exception e) {
        }
        return Lista;
    }
    
    
    
    
    // AHORA SE VIENE DETALLE INGRESOS 
    
    public List<detalle_ingreso> ConsultarDI(){
        List<detalle_ingreso> lista = new ArrayList<detalle_ingreso>();
        String sql = "select * from detalle_ingreso";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                detalle_ingreso d = new detalle_ingreso();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ingreso(rs.getInt("id_ingreso"));
                d.setId_producto(rs.getInt("id_producto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio_compra(rs.getFloat("precio_compra"));
                lista.add(d);
            }
            return lista;
        }catch(Exception e){
            return lista;
        }
    }
    
    @GET
    @Path("/listaIN")
    public List<detalle_ingreso> listarDI(){
        return (ConsultarDI());
    }
    
    @POST
    @Path("/agregarIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarDI(detalle_ingreso d){
        String sql="insert into detalle_ingreso(id_detalle,id_ingreso,id_producto,cantidad,precio_compra) values(?,?,?,?,?)";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, d.getId_detalle());
            ps.setInt(2, d.getId_ingreso());
            ps.setInt(3, d.getId_producto());
            ps.setInt(4, d.getCantidad());
            ps.setFloat(5, d.getPrecio_compra());
            ps.executeUpdate();
            return "Insercion realizada con exito";
        }catch(Exception e){
             return "Insercion fallida "+e.getMessage();
        }
    }
    
    @PUT
    @Path("/modificarIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modificarDI(detalle_ingreso d){
        String sql="update detalle_ingreso set cantidad=?, precio_compra=? where id_detalle=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, d.getCantidad());
            ps.setFloat(2, d.getPrecio_compra());
            ps.executeUpdate();
            return "Modificacion realizada con exito";
        }catch(Exception e){
             return "Modificacion fallida "+e.getMessage();
        }
    }
    
    
    @DELETE
    @Path("/EliminarIN/{id}")
    public String EliminarDI(@PathParam("id") String id){
        String sql="delete from detalle_ingreso where id_detalle=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            return "Eliminacion realizada con exito";
        }catch(Exception e){
             return "Eliminacion fallida "+e.getMessage();
        }
    }
    
    
    @GET 
    @Path("/ConsultarxIN/{id}")
    public List<detalle_ingreso> ConsultarxDI(@PathParam("id") String id){
        List<detalle_ingreso> Lista = new ArrayList<detalle_ingreso>();
        String sql = "select * from detalle_ingreso where id_detalle=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while ( rs.next()) {
                detalle_ingreso d = new detalle_ingreso();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ingreso(rs.getInt("id_ingreso"));
                d.setId_producto(rs.getInt("id_producto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio_compra(rs.getFloat("precio_compra"));
                Lista.add(d);
                
                
            }
        } catch (Exception e) {
        }
        return Lista;
    }
    
    
    

}
