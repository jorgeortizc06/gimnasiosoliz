package fusionsystem.jorgeortiz.gimnasiosoliz.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fusionsystem.jorgeortiz.gimnasiosoliz.bussiness.VentaBussiness;
import fusionsystem.jorgeortiz.gimnasiosoliz.model.Factura;
import fusionsystem.jorgeortiz.gimnasiosoliz.model.FormaPago;
import fusionsystem.jorgeortiz.gimnasiosoliz.model.Persona;
import fusionsystem.jorgeortiz.gimnasiosoliz.model.Producto;

@ManagedBean
@ViewScoped
public class VentaController {
	
	@Inject
	private VentaBussiness ventBuss;
	
	@Inject
	private FacesContext facesContext;
	
	private Factura newFactura;
	private List<Factura> facturas;
	private List<Factura> filterFacturas;
	
	private List<Persona> personas;
	private List<Producto> productos;
	private List<FormaPago> formaPagos;
	
	
	//Variable para verificar si estas editando
	private boolean vEditing;
	private int vIdFactura;
	private String vTitulo;
	private int vIdPersona;
	private int vIdProducto;
	private int vIdFormaPago;
	
	@PostConstruct
	public void init() {
		newFactura = new Factura();
		vEditing = false;
		vTitulo = "NUEVO";
		loadFacturas();
		personas = ventBuss.getPersonas();
		productos = ventBuss.getProductos();
		formaPagos = ventBuss.getFormaPagos();
	}
	
	//Guarda y actualiza Factura con control de exceptions
	public String guardarFactura() {
		try {
			if(vEditing)
				ventBuss.doActualizar(newFactura);
			else
				
				ventBuss.doGuardar(newFactura);
			return "list-facturas?faces-redirect=true";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error al guardar");
			e.printStackTrace();
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Error");
            facesContext.addMessage(null, m);

		}
		return null;
	}
	
	public void cargarFactura() {
		System.out.println("load data " + vIdFactura);
		if(vIdFactura==0)
			return;
		try {
			newFactura = ventBuss.getFactura(vIdFactura);
			System.out.println(newFactura);
			vEditing = true;
			vTitulo = "EDITAR";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					e.getMessage(), "Error");
            facesContext.addMessage(null, m);
		}

	}
	
	//Carga un objeto Factura en el formulario y se activa la edicion
	public String editarFactura(Factura tp) {
		//newFactura = ts;
		vEditing = true;
		vTitulo = "EDITAR";
		return "nueva-rutina?faces-redirect=true&id="+tp.getIdFactura();
	}
	
	//Elimina Factura con base id
	public String eliminarFactura(int id) {
		try {
			ventBuss.doEliminar(id);
			loadFacturas();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Error");
            facesContext.addMessage(null, m);

		}
		
		return null;
	}
	
	public void tipoFactura() {
		if (newFactura.getTipoComprobante().equals("FACTURA")) {
			newFactura.setIva(12.0);
		} else {
			newFactura.setIva(0.0);
		}
	}
	
	//Carga todos los Factura en el formulario
	public void loadFacturas() {
		facturas = ventBuss.getFacturas();
	}

	public Factura getNewFactura() {
		return newFactura;
	}

	public void setNewFactura(Factura newFactura) {
		this.newFactura = newFactura;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public List<Factura> getFilterFacturas() {
		return filterFacturas;
	}

	public void setFilterFacturas(List<Factura> filterFacturas) {
		this.filterFacturas = filterFacturas;
	}

	public boolean isvEditing() {
		return vEditing;
	}

	public void setvEditing(boolean vEditing) {
		this.vEditing = vEditing;
	}

	public int getvIdFactura() {
		return vIdFactura;
	}

	public void setvIdFactura(int vIdFactura) {
		this.vIdFactura = vIdFactura;
	}

	public String getvTitulo() {
		return vTitulo;
	}

	public void setvTitulo(String vTitulo) {
		this.vTitulo = vTitulo;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public int getvIdPersona() {
		return vIdPersona;
	}

	public void setvIdPersona(int vIdPersona) {
		this.vIdPersona = vIdPersona;
	}

	public int getvIdProducto() {
		return vIdProducto;
	}

	public void setvIdProducto(int vIdProducto) {
		this.vIdProducto = vIdProducto;
	}

	public List<FormaPago> getFormaPagos() {
		return formaPagos;
	}

	public void setFormaPagos(List<FormaPago> formaPagos) {
		this.formaPagos = formaPagos;
	}

	public int getvIdFormaPago() {
		return vIdFormaPago;
	}

	public void setvIdFormaPago(int vIdFormaPago) {
		this.vIdFormaPago = vIdFormaPago;
	}
	
	

}