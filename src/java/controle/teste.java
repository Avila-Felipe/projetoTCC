package controle;

import javax.faces.bean.ManagedBean;
import modelo.Usuario;

@ManagedBean
public class teste {
        public String redirecionar(){
            return "manutencaoUsuarios.xhtml?faces-redirect=true";
        
    }

}
