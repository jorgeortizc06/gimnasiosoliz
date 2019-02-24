package fusionsystem.jorgeortiz.gimnasiosoliz.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import fusionsystem.jorgeortiz.gimnasiosoliz.model.Factura;

@Stateless
public class FacturaDAO {
	
	@Inject
	private EntityManager em;
	
	//Inserta en DB
	public void insert(Factura factura) {
		em.persist(factura);
	}
	
	//Select en DB recuperando la informacion con base del id
	public Factura read(int id) {
		Factura factura = em.find(Factura.class, id);
		//Factura.getDetalleIngresos().size();
		return factura;
	}
	
	//Update en DB
	public void update(Factura factura) {
		em.merge(factura);
	}
	
	//Delete en DB
	public void delete(int id) {
		em.remove(read(id));
	}
	
	//Select en DB
	public List<Factura> getFacturas() {
		String jpql = "SELECT a FROM Factura a";
		Query query = em.createQuery(jpql, Factura.class);
		//query.setMaxResults(100);
		List<Factura> listado = query.getResultList();
		return listado;
	}

}
