package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {

    private static final String[][] DIREITO_ACESSO = {
        {"admin",
            "/faces/p0.xhtml",
            "/faces/paginasAdmin/p1Admin.xhtml",
            "/faces/paginasAdmin/p2Admin.xhtml",
            "/faces/paginasAdmin/manutencaoUsuarios.xhtml",
            "/faces/paginasAdmin/manutencaoRacas.xhtml",
            "/faces/paginasAdmin/cadastroRacas.xhtml",
            "/faces/paginasAdmin/manutencaoTipoEvento.xhtml",
            "/faces/paginasAdmin/cadastroTipoEvento.xhtml"},
        {"comum",
            "/faces/p0.xhtml",
            "/faces/paginasComum/p2Comum.xhtml",
            "/faces/paginasComum/p1Comum.xhtml",
            "/faces/paginasComum/manutencaoFazenda.xhtml",
            "/faces/paginasComum/cadastroFazenda.xhtml",
            "/faces/paginasComum/manutencaoAnimais.xhtml",
            "/faces/paginasComum/cadastroAnimal.xhtml",
            "/faces/paginasComum/manutencaoEvento.xhtml",
            "/faces/paginasComum/cadastroEvento.xhtml",
            "/faces/paginasComum/manutencaoPeso.xhtml",
            "/faces/paginasComum/loteAnimal.xhtml" }
            
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        Usuario user = null;

        HttpSession sess = ((HttpServletRequest) req).getSession();

        if (sess != null) {
            user = (Usuario) sess.getAttribute("usuario");

        }

        if ((user == null)
                && !request.getRequestURI().endsWith("/faces/login.xhtml")
                && !request.getRequestURI().endsWith("/faces/cadastroUsuario.xhtml")
                && !request.getRequestURI().endsWith("/faces/index.xhtml")
                && !request.getRequestURI().endsWith("/faces/index2.xhtml")
                && !request.getRequestURI().endsWith("/faces/loteAnimal.xhtml")
                && !request.getRequestURI().contains("/javax.faces.resource/")) {
            response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
        } else {
            try {
                boolean foi = false;
                if (user.getTipoUsuario().equals("admin")) {
                    for (int i = 1; i < DIREITO_ACESSO[0].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[0][i])) {
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                    }
                } else if (user.getTipoUsuario().equals("comum")) {
                    for (int i = 1; i < DIREITO_ACESSO[1].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[1][i])) {
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                    }

                }
            } catch (NullPointerException e) {
                chain.doFilter(req, res);
            }

        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
