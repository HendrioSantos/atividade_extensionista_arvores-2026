package atividade_extensionista.projeto_ambiental.components;

import atividade_extensionista.projeto_ambiental.infra.exception.InvalidoException;
import org.springframework.stereotype.Component;

@Component
public class FiltradorSlug {

    public String filtrarSlug(String nome) {
        if (nome == null){
            throw new InvalidoException("Nome está nulo");
        }
        return nome.toLowerCase()
                .replaceAll("[áàâãä]", "a")
                .replaceAll("[éèêë]", "e")
                .replaceAll("[íìîï]", "i")
                .replaceAll("[óòôõö]", "o")
                .replaceAll("[úùûü]", "u")
                .replaceAll("ç", "c")
                .replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }

}
