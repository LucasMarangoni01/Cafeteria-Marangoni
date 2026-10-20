package senai499.com.br.Cafe_Marangoni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import senai499.com.br.Cafe_Marangoni.model.Produto;
import senai499.com.br.Cafe_Marangoni.model.Usuario;
import senai499.com.br.Cafe_Marangoni.repository.ProdutoRepository;
import senai499.com.br.Cafe_Marangoni.repository.UsuarioRepository;

import java.math.BigDecimal;

@Component
public class DadosIniciais implements CommandLineRunner {

    private final ProdutoRepository produtos;
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    // Defina ADMIN_SENHA no ambiente; o valor padrão serve só para testar localmente.
    private final String senhaAdmin;

    public DadosIniciais(ProdutoRepository produtos, UsuarioRepository usuarios, PasswordEncoder encoder,
                         @Value("${ADMIN_SENHA:admin12345}") String senhaAdmin) {
        this.produtos = produtos;
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.senhaAdmin = senhaAdmin;
    }

    @Override
    public void run(String... args) {
        if (!usuarios.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setUsername("admin");
            admin.setEmail("admin@cafemarangoni.local");
            admin.setSenhaHash(encoder.encode(senhaAdmin));
            admin.setPerfil("ADMIN");
            usuarios.save(admin);
        }
        if (produtos.count() > 0) return;

        String q = "bebidas-quentes", qt = "Bebidas quentes", qd = "Clássicos preparados na hora.";
        add(q, qt, qd, "espresso", "Café expresso", "Extração curta, aroma intenso e finalização cremosa.", "8.90", "imagem_representacao_Cafe_expresso.jpg");
        add(q, qt, qd, "cappuccino", "Cappuccino", "Café, leite vaporizado e uma delicada camada de espuma.", "15.90", "imagem_representacao_Cappuccino_Gourmet.jpg");
        add(q, qt, qd, "latte", "Latte", "Espresso equilibrado com leite vaporizado e textura aveludada.", "16.90", "imagem_representacao_Latte_Caramelo.jpg");

        q = "bebidas-geladas"; qt = "Bebidas geladas"; qd = "Receitas refrescantes para qualquer momento.";
        add(q, qt, qd, "iced-coffee", "Iced Coffee", "Café gelado servido com gelo para uma pausa refrescante.", "14.90", "imagem_representacao_Iced_Coffee.jpg");
        add(q, qt, qd, "cold-brew", "Cold Brew", "Café extraído a frio, suave e naturalmente aromático.", "16.50", "imagem_representacao_Cold_Brew.jpg");
        add(q, qt, qd, "smoothie", "Smoothie de frutas", "Bebida cremosa de frutas batidas, servida bem gelada.", "18.90", "imagem_representacao_Smoothies.jpg");

        q = "padaria-confeitaria"; qt = "Padaria e confeitaria"; qd = "Receitas assadas e doces para acompanhar o café.";
        add(q, qt, qd, "pao-de-queijo", "Pão de queijo", "Porção de pães de queijo dourados, assados no dia.", "9.50", "imagem_representacao_Pao_de_queijo.jpg");
        add(q, qt, qd, "croissant", "Croissant de manteiga", "Massa folhada em camadas delicadas e crocantes.", "12.00", "imagem_representacao_Croissant_Manteiga.jpg");
        add(q, qt, qd, "brownie", "Brownie", "Bolo de chocolate macio com casquinha delicada.", "11.90", "imagem_representacao_Brownie.jpg");

        q = "lanches-salgados"; qt = "Lanches salgados"; qd = "Opções salgadas preparadas para a sua pausa.";
        add(q, qt, qd, "misto-quente", "Misto quente", "Pão tostado com queijo derretido e presunto.", "18.90", "imagem_representacao_misto_quente.jpg");
        add(q, qt, qd, "quiche", "Quiche do dia", "Massa leve com recheio cremoso preparado na casa.", "17.50", "imagem_representacao_Quiche_do_dia.jpg");
        add(q, qt, qd, "empada", "Empada artesanal", "Massa amanteigada com recheio salgado do dia.", "10.90", "imagem_representacao_empada_artesanal.jpg");

        q = "sobremesas"; qt = "Sobremesas"; qd = "Um toque doce para encerrar bem a experiência.";
        add(q, qt, qd, "pudim", "Pudim", "Sobremesa cremosa com calda de caramelo.", "12.90", "imagem_representacao_pudim.jpg");
        add(q, qt, qd, "brigadeiro-gourmet", "Brigadeiro gourmet", "Brigadeiro de chocolate com finalização artesanal.", "6.50", "imagem_representacao_brigadeiro-gourmet.jpg");
        add(q, qt, qd, "tiramisu", "Tiramisù", "Camadas delicadas de creme, café e cacau.", "19.90", "imagem_representacao_TIramisu.jpg");

        q = "opcoes-saudaveis"; qt = "Opções saudáveis"; qd = "Combinações leves para diferentes preferências.";
        add(q, qt, qd, "salada-frutas", "Salada de frutas", "Seleção de frutas frescas cortadas na hora.", "14.90", "imagem_representacao_Salada-frutas.jpg");
        add(q, qt, qd, "iogurte-granola", "Iogurte com granola", "Iogurte servido com granola crocante e frutas.", "16.50", "imagem_representacao_Iogurte-granola.jpg");
        add(q, qt, qd, "tapioca", "Tapioca", "Tapioca preparada na hora com recheio à escolha.", "15.90", "imagem_representacao_Tapioca.jpg");

        q = "produtos-venda"; qt = "Produtos para venda"; qd = "Leve um pouco da experiência Café Marangoni para casa.";
        add(q, qt, qd, "canecas-personalizadas", "Canecas personalizadas da nossa cafeteria· 350 ml", "Caneca de cerâmica exclusiva com a marca Café Marangoni, perfeita para saborear o seu café em casa.", "29.90", "imagem_representacao_Canecas_personalizadas.jpg");
        add(q, qt, qd, "cafe-moido", "Café moído · 250 g", "Café moído para facilitar o preparo no dia a dia.", "34.90", "imagem_representacao_cafe_muido.jpg");
        add(q, qt, qd, "capsulas-cafe", "Cápsulas de café · caixa", "Cápsulas práticas para uma xícara rápida.", "29.90", "imagem_representacao_Capsula_cafe.jpg");
    }

    private void add(String catSlug, String catTitulo, String catDescricao,
                     String slug, String nome, String descricao, String preco, String imagem) {
        Produto p = new Produto();
        p.setCategoriaSlug(catSlug);
        p.setCategoriaTitulo(catTitulo);
        p.setCategoriaDescricao(catDescricao);
        p.setSlug(slug);
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(new BigDecimal(preco));
        p.setImagem(imagem);
        produtos.save(p);
    }
}