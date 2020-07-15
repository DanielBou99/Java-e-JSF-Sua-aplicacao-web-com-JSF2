package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private Integer autorId;
	
	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}
	
	public Autor getAutor() {
		return autor;
	}

	public RedirectView gravar() {
		
		if (this.autor.getId() == null) {
			System.out.println("Gravando autor " + this.autor.getNome());
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		
		
	    this.autor = new Autor();

	    return new RedirectView("livro");
	}
	
	public void carregar(Autor autor) {
		this.autor = autor;	
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void remover(Autor autor) {
		try {
			new DAO<Autor>(Autor.class).remove(autor);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("autor",
                    new FacesMessage(autor.getNome() + " contem livros associados a ele!"));
            System.out.println("Tentando remover autor!");
        }
	}
	
}
