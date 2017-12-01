package entities.produto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as=Livro.class)
public interface IProduto {
	
	public Integer getId();
}
