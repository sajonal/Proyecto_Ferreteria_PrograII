/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.QueryParam;



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
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuarios> listar(){
        return (Consultar());
    }
    
    @POST
    @Path("/agregar")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> agregarFact(Facturas f) {
        Map<String,Object> response = new HashMap<String,Object>();
        String sql = "INSERT INTO facturas (id_usuario, numero_factura, cliente, forma_pago, subtotal, total) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, f.getId_usuario());
            ps.setString(2, f.getNumero_factura());
            ps.setString(3, f.getCliente());
            ps.setString(4, f.getForma_pago());
            ps.setDouble(5, f.getSubtotal());
            ps.setDouble(6, f.getTotal());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            int genId = -1;
            if (gk != null && gk.next()) {
                genId = gk.getInt(1);
            }
            if(gk != null) gk.close();
            response.put("id_factura", genId);
            response.put("mensaje", "Factura agregada con éxito");
            return response;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return response;
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
        try {
            con = cn.getConnection();
            // borrar detalles primero
            PreparedStatement psDelDet = con.prepareStatement("DELETE FROM detalle_factura WHERE id_factura = ?");
            psDelDet.setInt(1, id);
            psDelDet.executeUpdate();
            psDelDet.close();

            // borrar factura
            PreparedStatement psDelFact = con.prepareStatement("DELETE FROM facturas WHERE id_factura = ?");
            psDelFact.setInt(1, id);
            int affected = psDelFact.executeUpdate();
            psDelFact.close();

            if (affected > 0) {
                return "Factura y sus detalles eliminados con éxito";
            } else {
                return "No se encontró la factura a eliminar";
            }
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
        // seleccionar desde la tabla correcta ingreso_almacen
        String sql = "select * from ingresos_almacen";
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
                i.setTotal(rs.getFloat("total"));
                 lista.add(i);
             }
             return lista;
         }catch(Exception e){
             return lista;
         }
     }
     
     @GET
     @Path("/listaA")
     @Produces(MediaType.APPLICATION_JSON)
     public List<ingresos_almacen> listarI(){
         return (ConsultarI());
     }
     
     @POST
     @Path("/agregarA")
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
     public Map<String,Object> agregarI(ingresos_almacen i){
        Map<String,Object> resp = new HashMap<String,Object>();
        String sql="INSERT INTO ingresos_almacen (id_usuario, proveedor, factura_proveedor, total) VALUES (?, ?, ?, 0)";
        try{
            con = cn.getConnection();
            // Crear el ingreso y obtener ID
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, i.getId_usuario());
            ps.setString(2, i.getProveedor());
            ps.setString(3, i.getFactura_proveedor());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("No se pudo crear el ingreso");
            }

            ResultSet gk = ps.getGeneratedKeys();
            int genId = -1;
            if (gk.next()) {
                genId = gk.getInt(1);
                System.out.println("ID generado para ingreso: " + genId); // Debug
            } else {
                throw new Exception("No se pudo obtener el ID generado");
            }
            gk.close();
            
            resp.put("id_ingreso", genId);
            resp.put("mensaje", "Ingreso #" + genId + " creado con éxito");
            return resp;
        }catch(Exception e){
            System.out.println("Error en agregarI: " + e.getMessage()); // Debug
            resp.put("error", e.getMessage());
            return resp;
        }
    }
    
     @PUT
    @Path("/modificarA")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modificarI(ingresos_almacen i){
        // incluir 'total' en el UPDATE y parámetros en orden correcto (último parámetro es id_ingreso)
        String sql="update ingresos_almacen set fecha_ingreso=?, proveedor=?, factura_proveedor=?, total=? where id_ingreso=?";
        try{
            con = cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setTimestamp(1, i.getFecha_ingreso());
            ps.setString(2, i.getProveedor());
            ps.setString(3, i.getFactura_proveedor());
            ps.setFloat(4, i.getTotal());
            ps.setInt(5, i.getId_ingreso()); // WHERE id_ingreso=?
            ps.executeUpdate();
            return "Modificacion realizada con exito";
        }catch(Exception e){
             return "Modificacion fallida "+e.getMessage();
        }
     }
     
     
     @DELETE
     @Path("/EliminarA/{id}")
     public String EliminarI(@PathParam("id") String id){
         try{
             con = cn.getConnection();
             // borrar detalles del ingreso primero
             PreparedStatement psDelDet = con.prepareStatement("DELETE FROM detalle_ingreso WHERE id_ingreso = ?");
             psDelDet.setString(1, id);
             psDelDet.executeUpdate();
             psDelDet.close();

             // luego borrar el ingreso
             PreparedStatement psDel = con.prepareStatement("DELETE FROM ingresos_almacen WHERE id_ingreso = ?");
             psDel.setString(1, id);
             int affected = psDel.executeUpdate();
             psDel.close();

             if (affected > 0) {
                 return "Ingreso y sus detalles eliminados con éxito";
             } else {
                 return "No se encontró el ingreso a eliminar";
             }
         }catch(Exception e){
              return "Eliminacion fallida "+e.getMessage();
         }
     }
     
     
     @GET 
     @Path("/ConsultarxA/{id}")
     public List<ingresos_almacen> ConsultarxI(@PathParam("id") String id){
         List<ingresos_almacen> Lista = new ArrayList<ingresos_almacen>();
         String sql = "select * from ingresos_almacen where id_ingreso=?";
         try {
             con = cn.getConnection();
             ps = con.prepareStatement(sql);
             ps.setString(1, id);
             rs = ps.executeQuery();
             while ( rs.next()) {
                 ingresos_almacen i = new ingresos_almacen();
                 i.setId_ingreso(rs.getInt("id_ingreso"));
                 i.setId_usuario(rs.getInt("id_usuario"));
                i.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                i.setProveedor(rs.getString("proveedor"));
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
     @Produces(MediaType.APPLICATION_JSON)
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
            ps.setInt(3, d.getId_detalle()); // faltaba el parámetro id_detalle
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<detalle_ingreso> ConsultarxDI(@PathParam("id") String id){
        List<detalle_ingreso> Lista = new ArrayList<detalle_ingreso>();
        // Filtrar por id_ingreso para obtener todos los detalles pertenecientes a ese ingreso
        String sql = "SELECT id_detalle, id_ingreso, id_producto, cantidad, precio_compra FROM detalle_ingreso WHERE id_ingreso = ?";
         try {
             con = cn.getConnection();
             ps = con.prepareStatement(sql);
             ps.setString(1, id);
             rs = ps.executeQuery();
             while (rs.next()) {
                 detalle_ingreso d = new detalle_ingreso();
                 d.setId_detalle(rs.getInt("id_detalle"));
                 d.setId_ingreso(rs.getInt("id_ingreso"));
                 d.setId_producto(rs.getInt("id_producto"));
                 d.setCantidad(rs.getInt("cantidad"));
                 d.setPrecio_compra(rs.getFloat("precio_compra"));
                 Lista.add(d);
             }
         } catch (Exception e) {
             System.out.println("Error al listar detalle_ingreso por id_ingreso: " + e.getMessage());
         }
         return Lista;
     }
    
    
    // --- NUEVO: detalle de factura ---
    @POST
    @Path("/agregarDetalleFactura")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarDetalleFactura(detalle_factura d){
        // Se insertan los campos según la clase que indicaste: id_detalle,id_factura,id_producto,cantidad,precio
        String sql = "INSERT INTO detalle_factura(id_detalle,id_factura,id_producto,cantidad,precio) VALUES (?,?,?,?,?)";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, d.getId_detalle());
            ps.setInt(2, d.getId_factura());
            ps.setInt(3, d.getId_producto());
            ps.setInt(4, d.getCantidad());
            ps.setFloat(5, d.getPrecio());
            ps.executeUpdate();

            // calcular monto y actualizar stock (resta por venta)
            int cantidad = d.getCantidad();
            float precio = d.getPrecio();
            float monto = cantidad * precio;

            PreparedStatement psUpd = con.prepareStatement("UPDATE productos SET existencia = existencia - ? WHERE id_producto = ?");
            psUpd.setInt(1, cantidad);
            psUpd.setInt(2, d.getId_producto());
            psUpd.executeUpdate();
            psUpd.close();

            // actualizar totales en facturas (subtotal y total)
            PreparedStatement psSel = con.prepareStatement("SELECT subtotal FROM facturas WHERE id_factura = ?");
            psSel.setInt(1, d.getId_factura());
            ResultSet rsSel = psSel.executeQuery();
            float currSubtotal = 0f;
            if(rsSel.next()){
                currSubtotal = rsSel.getFloat("subtotal");
            }
            rsSel.close();
            psSel.close();

            float newSubtotal = currSubtotal + monto;
            PreparedStatement psUpdFact = con.prepareStatement("UPDATE facturas SET subtotal = ?, total = ? WHERE id_factura = ?");
            psUpdFact.setFloat(1, newSubtotal);
            psUpdFact.setFloat(2, newSubtotal); // ajustar si hay impuestos
            psUpdFact.setInt(3, d.getId_factura());
            psUpdFact.executeUpdate();
            psUpdFact.close();

            return "Detalle factura agregado con éxito";
        }catch(Exception e){
            return "Error al agregar detalle factura: " + e.getMessage();
        }
    }

    @GET
    @Path("/listaDetalleFactura/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<detalle_factura> listarDetalleFactura(@PathParam("id") int idFactura){
        List<detalle_factura> lista = new ArrayList<detalle_factura>();
        String sql = "SELECT id_detalle,id_factura,id_producto,cantidad,precio FROM detalle_factura WHERE id_factura = ?";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idFactura);
            rs = ps.executeQuery();
            while(rs.next()){
                detalle_factura df = new detalle_factura();
                df.setId_detalle(rs.getInt("id_detalle"));
                df.setId_factura(rs.getInt("id_factura"));
                df.setId_producto(rs.getInt("id_producto"));
                df.setCantidad(rs.getInt("cantidad"));
                df.setPrecio(rs.getFloat("precio"));
                lista.add(df);
            }
        }catch(Exception e){
            System.out.println("Error listar detalle factura: " + e.getMessage());
        }
        return lista;
    }

    // --- NUEVO: detalle de ingreso con tipo (entrada/salida) ---
    @POST
    @Path("/agregarINMovimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarDIConTipo(detalle_ingreso d){
        System.out.println("Recibiendo detalle con id_ingreso: " + d.getId_ingreso()); // Debug
        
        // Primero verificar que existe el ingreso
        try {
            con = cn.getConnection();
            PreparedStatement psCheck = con.prepareStatement("SELECT id_ingreso FROM ingresos_almacen WHERE id_ingreso = ?");
            psCheck.setInt(1, d.getId_ingreso());
            ResultSet rsCheck = psCheck.executeQuery();
            if (!rsCheck.next()) {
                return "Error: El ingreso #" + d.getId_ingreso() + " no existe";
            }
            rsCheck.close();
            psCheck.close();
            
            // Si existe, proceder con la inserción
            String sql = "insert into detalle_ingreso(id_ingreso,id_producto,cantidad,precio_compra) values(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, d.getId_ingreso());
            ps.setInt(2, d.getId_producto());
            ps.setInt(3, d.getCantidad());
            ps.setFloat(4, d.getPrecio_compra());
            ps.executeUpdate();

            // Actualizar stock
            PreparedStatement psUpd = con.prepareStatement(
                "UPDATE productos SET existencia = existencia + ? WHERE id_producto = ?");
            psUpd.setInt(1, d.getCantidad());
            psUpd.setInt(2, d.getId_producto());
            psUpd.executeUpdate();
            psUpd.close();

            // Actualizar total
            float subtotal = d.getPrecio_compra() * d.getCantidad();
            PreparedStatement psUpdTotal = con.prepareStatement(
                "UPDATE ingresos_almacen SET total = total + ? WHERE id_ingreso = ?");
            psUpdTotal.setFloat(1, subtotal);
            psUpdTotal.setInt(2, d.getId_ingreso());
            psUpdTotal.executeUpdate();
            psUpdTotal.close();

            return "Detalle agregado exitosamente al ingreso #" + d.getId_ingreso();
        }catch(Exception e){
            System.out.println("Error en agregarDIConTipo: " + e.getMessage()); // Debug
            return "Error al registrar detalle: " + e.getMessage();
        }
    }

    @GET
    @Path("/listaClientes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String,Object>> listarClientes() {
        List<Map<String,Object>> lista = new ArrayList<Map<String,Object>>();
        String sql = "SELECT * FROM clientes";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id_cliente", rs.getInt("id_cliente"));
                map.put("nombre", rs.getString("nombre"));
                map.put("documento", rs.getString("documento"));
                lista.add(map);
            }
        } catch(Exception e) {
            System.out.println("Error listando clientes: " + e.getMessage());
        }
        return lista;
    }
    
    // eliminar una línea de detalle de factura por id_detalle
    @DELETE
    @Path("/EliminarDF/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String EliminarDF(@PathParam("id") int id){
        String sql="DELETE FROM detalle_factura WHERE id_detalle=?";
        try{
            con = cn.getConnection();
            PreparedStatement psd = con.prepareStatement(sql);
            psd.setInt(1, id);
            int affected = psd.executeUpdate();
            psd.close();
            if (affected > 0) {
                return "Detalle de factura eliminado con éxito";
            } else {
                return "No se encontró el detalle de factura";
            }
        }catch(Exception e){
             return "Eliminacion fallida "+e.getMessage();
        }
    }
}
