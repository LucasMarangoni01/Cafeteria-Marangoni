const productImageRoot = document.querySelector("[data-asset-root]")?.dataset.assetRoot ?? "../static/img/";
const catalog = [
    {
        id: "bebidas-quentes",
        title: "Bebidas quentes",
        description: "Clássicos preparados na hora.",
        image: "imagem_representacao_Cappuccino_Gourmet.jpg",
        products: [
            { id: "espresso", name: "Café expresso", description: "Extração curta, aroma intenso e finalização cremosa.", price: 8.9, image: "imagem_representacao_Cafe_expresso.jpg" },
            { id: "cappuccino", name: "Cappuccino", description: "Café, leite vaporizado e uma delicada camada de espuma.", price: 15.9, image: "imagem_representacao_Cappuccino_Gourmet.jpg" },
            { id: "latte", name: "Latte", description: "Espresso equilibrado com leite vaporizado e textura aveludada.", price: 16.9, image: "imagem_representacao_Latte_Caramelo.jpg" }
        ]
    },
    {
        id: "bebidas-geladas",
        title: "Bebidas geladas",
        description: "Receitas refrescantes para qualquer momento.",
        image: "imagem_representacao_Cold_Brew.jpg",
        products: [
            { id: "iced-coffee", name: "Iced Coffee", description: "Café gelado servido com gelo para uma pausa refrescante.", price: 14.9, image: "imagem_representacao_Iced_Coffee.jpg" },
            { id: "cold-brew", name: "Cold Brew", description: "Café extraído a frio, suave e naturalmente aromático.", price: 16.5, image: "imagem_representacao_Cold_Brew.jpg" },
            { id: "smoothie", name: "Smoothie de frutas", description: "Bebida cremosa de frutas batidas, servida bem gelada.", price: 18.9, image: "imagem_representacao_Smoothies.jpg" }
        ]
    },
    {
        id: "padaria-confeitaria",
        title: "Padaria e confeitaria",
        description: "Receitas assadas e doces para acompanhar o café.",
        image: "imagem_representacao_Croissant_Manteiga.jpg",
        products: [
            { id: "pao-de-queijo", name: "Pão de queijo", description: "Porção de pães de queijo dourados, assados no dia.", price: 9.5, image: "imagem_representacao_Pao_de_queijo.jpg" },
            { id: "croissant", name: "Croissant de manteiga", description: "Massa folhada em camadas delicadas e crocantes.", price: 12, image: "imagem_representacao_Croissant_Manteiga.jpg" },
            { id: "brownie", name: "Brownie", description: "Bolo de chocolate macio com casquinha delicada.", price: 11.9, image: "imagem_representacao_Brownie.jpg" }
        ]
    },
    {
        id: "lanches-salgados",
        title: "Lanches salgados",
        description: "Opções salgadas preparadas para a sua pausa.",
        image: "imagem2.jpg",
        products: [
            { id: "misto-quente", name: "Misto quente", description: "Pão tostado com queijo derretido e presunto.", price: 18.9, image: "imagem_representacao_misto_quente.jpg" },
            { id: "quiche", name: "Quiche do dia", description: "Massa leve com recheio cremoso preparado na casa.", price: 17.5, image: "imagem_representacao_Quiche_do_dia.jpg" },
            { id: "empada", name: "Empada artesanal", description: "Massa amanteigada com recheio salgado do dia.", price: 10.9, image: "imagem_representacao_empada_artesanal.jpg" }
        ]
    },
    {
        id: "sobremesas",
        title: "Sobremesas",
        description: "Um toque doce para encerrar bem a experiência.",
        image: "imagem1.jpg",
        products: [
            { id: "pudim", name: "Pudim", description: "Sobremesa cremosa com calda de caramelo.", price: 12.9, image: "imagem_representacao_pudim.jpg" },
            { id: "brigadeiro-gourmet", name: "Brigadeiro gourmet", description: "Brigadeiro de chocolate com finalização artesanal.", price: 6.5, image: "imagem_representacao_brigadeiro-gourmet.jpg" },
            { id: "tiramisu", name: "Tiramisù", description: "Camadas delicadas de creme, café e cacau.", price: 19.9, image: "imagem_representacao_TIramisu.jpg" }
        ]
    },
    {
        id: "opcoes-saudaveis",
        title: "Opções saudáveis",
        description: "Combinações leves para diferentes preferências.",
        image: "imagem3.jpg",
        products: [
            { id: "salada-frutas", name: "Salada de frutas", description: "Seleção de frutas frescas cortadas na hora.", price: 14.9, image: "imagem_representacao_Salada-frutas.jpg" },
            { id: "iogurte-granola", name: "Iogurte com granola", description: "Iogurte servido com granola crocante e frutas.", price: 16.5, image: "imagem_representacao_Iogurte-granola.jpg" },
            { id: "tapioca", name: "Tapioca", description: "Tapioca preparada na hora com recheio à escolha.", price: 15.9, image: "imagem_representacao_Tapioca.jpg" }
        ]
    },
    {
        id: "produtos-venda",
        title: "Produtos para venda",
        description: "Leve um pouco da experiência Café Marangoni para casa.",
        image: "imagem_representacao_Latte_Caramelo.jpg",
        products: [
            { id: "Canecas-personalizadas", name: "Canecas personalizadas da nossa cafeteria· 350 ml", description: "Caneca de cerâmica exclusiva com a marca Café Marangoni, perfeita para saborear o seu café em casa.", price: 29.9, image: "imagem_representacao_Canecas_personalizadas.jpg" },
            { id: "cafe-moido", name: "Café moído · 250 g", description: "Café moído para facilitar o preparo no dia a dia.", price: 34.9, image: "imagem_representacao_cafe_muido.jpg" },
            { id: "capsulas-cafe", name: "Cápsulas de café · caixa", description: "Cápsulas práticas para uma xícara rápida.", price: 29.9, image: "imagem_representacao_Capsula_cafe.jpg" }
        ]
    }
];

const currency = new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" });
const storageKeys = {
    cart: "cafemarangoni:cart",
    orders: "cafemarangoni:demo-orders",
    account: "cafemarangoni:demo-account",
    customer: "cafemarangoni:demo-customer"
};

function readStoredList(key) {
    const value = JSON.parse(localStorage.getItem(key) ?? "[]");
    return Array.isArray(value) ? value : [];
}

function findProduct(productId) {
    return catalog.flatMap((category) => category.products).find((product) => product.id === productId);
}

function escapeHtml(value) {
    return String(value).replace(/[&<>"']/g, (character) => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        "\"": "&quot;",
        "'": "&#39;"
    })[character]);
}

function renderCatalog() {
    const collection = document.querySelector("#catalogCollection");
    const categoryLinks = document.querySelector("#catalogCategoryLinks");
    if (!collection || !categoryLinks) return;

    categoryLinks.innerHTML = `
        <button class="catalog-category-filter is-active" type="button" data-category-filter="all" aria-pressed="true">
            Todas <span>${catalog.reduce((total, category) => total + category.products.length, 0)}</span>
        </button>
        ${catalog.map((category) => `
            <button class="catalog-category-filter" type="button" data-category-filter="${category.id}" aria-pressed="false">
                ${category.title} <span>${category.products.length}</span>
            </button>
        `).join("")}
    `;

    collection.innerHTML = catalog.map((category) => `
        <section class="catalog-group" id="${category.id}" data-category="${category.id}" aria-labelledby="${category.id}-title">
            <div class="catalog-group__heading">
                <h2 id="${category.id}-title">${category.title}</h2>
                <p>${category.description}</p>
            </div>
            <div class="catalog-products-grid">
                ${category.products.map((product) => {
        const productImage = product.image ?? category.image;
        return `
                        <article class="catalog-product">
                            <div class="catalog-product__image-wrap">
                                <img class="catalog-product__image"
                                     src="${productImageRoot}${productImage}"
                                     alt="Imagem ilustrativa de ${product.name}"
                                     loading="lazy">
                            </div>
                            <div class="catalog-product__body">
                                <span class="catalog-section__eyebrow">${category.title}</span>
                                <h3>${product.name}</h3>
                                <p>${product.description}</p>
                                <div class="catalog-product__meta">
                                    <span class="catalog-product__price">${currency.format(product.price)}</span>
                                    <button class="catalog-add-button" type="button" data-add-product="${product.id}">
                                        <i class="fa-solid fa-plus" aria-hidden="true"></i> Selecionar
                                    </button>
                                </div>
                            </div>
                        </article>
                    `;
    }).join("")}
            </div>
        </section>
    `).join("");

    categoryLinks.addEventListener("click", (event) => {
        const button = event.target.closest("[data-category-filter]");
        if (!button) return;

        const selectedCategory = button.dataset.categoryFilter;
        categoryLinks.querySelectorAll("[data-category-filter]").forEach((filter) => {
            const isActive = filter === button;
            filter.classList.toggle("is-active", isActive);
            filter.setAttribute("aria-pressed", String(isActive));
        });

        collection.querySelectorAll("[data-category]").forEach((group) => {
            group.hidden = selectedCategory !== "all" && group.dataset.category !== selectedCategory;
        });

        const visibleProducts = selectedCategory === "all"
            ? catalog.reduce((total, category) => total + category.products.length, 0)
            : catalog.find((category) => category.id === selectedCategory)?.products.length ?? 0;
        const resultCount = document.querySelector("[data-catalog-result-count]");
        if (resultCount) {
            resultCount.textContent = selectedCategory === "all"
                ? `${visibleProducts} produtos em todas as categorias`
                : `${visibleProducts} produtos nesta categoria`;
        }
    });

    collection.addEventListener("click", (event) => {
        const button = event.target.closest("[data-add-product]");
        if (!button) return;
        updateCart(button.dataset.addProduct, 1);
        const status = document.querySelector("[data-cart-status]");
        if (status) status.textContent = `${button.closest(".catalog-product").querySelector("h3").textContent} adicionado ao pedido.`;
    });
}

function updateCart(productId, quantityChange) {
    const cart = readStoredList(storageKeys.cart);
    const item = cart.find((entry) => entry.id === productId);
    if (item) {
        item.quantity += quantityChange;
    } else if (quantityChange > 0 && findProduct(productId)) {
        cart.push({ id: productId, quantity: quantityChange });
    }
    const updatedCart = cart.filter((entry) => entry.quantity > 0);
    localStorage.setItem(storageKeys.cart, JSON.stringify(updatedCart));
    renderOrderCart();
}

function renderOrderCart() {
    const cartRoot = document.querySelector("#orderCart");
    if (!cartRoot) return;

    const cart = readStoredList(storageKeys.cart);
    const validItems = cart
        .map((entry) => ({ ...entry, product: findProduct(entry.id) }))
        .filter((entry) => entry.product && Number.isInteger(entry.quantity) && entry.quantity > 0);
    const subtotal = validItems.reduce((sum, entry) => sum + entry.product.price * entry.quantity, 0);
    const fulfillment = document.querySelector("#fulfillment")?.value;
    const deliveryFee = fulfillment === "entrega" && validItems.length ? 8 : 0;

    cartRoot.innerHTML = validItems.length
        ? validItems.map(({ id, quantity, product }) => `
            <div class="order-cart-line">
                <div class="order-cart-line__info">
                    <strong>${product.name}</strong>
                    <small>${currency.format(product.price)} cada</small>
                </div>
                <div class="order-quantity" aria-label="Quantidade de ${product.name}">
                    <button type="button" data-cart-change="${id}" data-change="-1" aria-label="Remover uma unidade">−</button>
                    <span>${quantity}</span>
                    <button type="button" data-cart-change="${id}" data-change="1" aria-label="Adicionar uma unidade">+</button>
                </div>
            </div>
        `).join("")
        : '<p class="mb-0">Seu pedido está vazio. Escolha produtos no <a href="produtos.html">cardápio</a>.</p>';

    const itemCount = validItems.reduce((sum, entry) => sum + entry.quantity, 0);
    const count = document.querySelector("[data-cart-count]");
    if (count) count.textContent = `${itemCount} ${itemCount === 1 ? "item" : "itens"}`;
    const subtotalNode = document.querySelector("[data-cart-subtotal]");
    if (subtotalNode) subtotalNode.textContent = currency.format(subtotal);
    const feeNode = document.querySelector("[data-delivery-fee]");
    if (feeNode) feeNode.textContent = currency.format(deliveryFee);
    const totalNode = document.querySelector("[data-cart-total]");
    if (totalNode) totalNode.textContent = currency.format(subtotal + deliveryFee);

    const submit = document.querySelector("[data-checkout-form] button[type=submit]");
    if (submit) submit.disabled = validItems.length === 0;
}

function renderOrderHistory() {
    const list = document.querySelector("#orderHistory");
    if (!list) return;

    const customer = JSON.parse(localStorage.getItem(storageKeys.customer) ?? "null");
    const prompt = document.querySelector("[data-login-prompt]");
    const nameNode = document.querySelector("[data-customer-name]");
    const nameInput = document.querySelector("#customerName");
    const logout = document.querySelector("[data-demo-logout]");
    if (customer?.name) {
        if (nameNode) nameNode.textContent = customer.name;
        if (nameInput && !nameInput.value) nameInput.value = customer.name;
        if (prompt) prompt.hidden = true;
        if (logout) logout.hidden = false;
    } else {
        if (nameNode) nameNode.textContent = "visitante";
        if (prompt) prompt.hidden = false;
        if (logout) logout.hidden = true;
    }

    const orders = readStoredList(storageKeys.orders).filter((order) => order && Array.isArray(order.items));
    const count = document.querySelector("[data-order-count]");
    if (count) count.textContent = `${orders.length} ${orders.length === 1 ? "pedido" : "pedidos"}`;
    list.innerHTML = orders.length
        ? orders.map((order) => `
            <article class="order-item order-item--active">
                <div class="order-icon"><i class="fa-solid fa-receipt" aria-hidden="true"></i></div>
                <div class="order-content">
                    <div class="order-heading">
                        <h3>Prévia ${escapeHtml(order.number)}</h3>
                        <span class="status status--processing">Demonstração local</span>
                    </div>
                    <p>${order.items
                .filter((item) => item && findProduct(item.id) && Number.isInteger(item.quantity) && item.quantity > 0)
                .map((item) => `${item.quantity} × ${findProduct(item.id).name}`)
                .join(" + ")}</p>
                    <small>${escapeHtml(order.fulfillmentLabel)} · ${currency.format(order.total)} · ${escapeHtml(order.createdAt)}</small>
                </div>
            </article>
        `).join("")
        : '<div class="auth-demo-note">Ainda não há pedidos de demonstração salvos neste navegador.</div>';
}

function setupCheckout() {
    const form = document.querySelector("[data-checkout-form]");
    if (!form) return;

    const fulfillment = document.querySelector("#fulfillment");
    const tableField = document.querySelector("[data-table-field]");
    const tableSelect = document.querySelector("#tableNumber");
    const addressField = document.querySelector("[data-address-field]");
    const addressInput = document.querySelector("#deliveryAddress");
    const feedback = document.querySelector("[data-checkout-feedback]");

    fulfillment.addEventListener("change", () => {
        const tableMode = fulfillment.value === "mesa";
        const deliveryMode = fulfillment.value === "entrega";
        tableField.hidden = !tableMode;
        tableSelect.required = tableMode;
        addressField.hidden = !deliveryMode;
        addressInput.required = deliveryMode;
        renderOrderCart();
    });

    document.querySelector("#orderCart").addEventListener("click", (event) => {
        const button = event.target.closest("[data-cart-change]");
        if (button) updateCart(button.dataset.cartChange, Number(button.dataset.change));
    });

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const cart = readStoredList(storageKeys.cart);
        const items = cart.filter((entry) => findProduct(entry.id) && entry.quantity > 0);
        if (!items.length) return;

        const data = new FormData(form);
        const modeLabels = { balcao: "Retirada no balcão", mesa: `Mesa ${data.get("tableNumber")}`, entrega: "Delivery" };
        const subtotal = items.reduce((sum, entry) => sum + findProduct(entry.id).price * entry.quantity, 0);
        const total = subtotal + (data.get("fulfillment") === "entrega" ? 8 : 0);
        const orders = readStoredList(storageKeys.orders);
        const order = {
            number: `#${String(orders.length + 1).padStart(4, "0")}`,
            customerName: data.get("customerName"),
            items,
            fulfillmentLabel: modeLabels[data.get("fulfillment")],
            total,
            createdAt: new Intl.DateTimeFormat("pt-BR", { dateStyle: "short", timeStyle: "short" }).format(new Date())
        };

        orders.unshift(order);
        localStorage.setItem(storageKeys.orders, JSON.stringify(orders));
        localStorage.setItem(storageKeys.cart, "[]");
        form.reset();
        fulfillment.dispatchEvent(new Event("change"));
        feedback.textContent = "Prévia salva neste navegador. Nenhum pedido foi enviado à cafeteria.";
        feedback.hidden = false;
        renderOrderCart();
        renderOrderHistory();
    });
}

function setupDemoAccount() {
    document.querySelector("[data-demo-logout]")?.addEventListener("click", () => {
        localStorage.removeItem(storageKeys.customer);
        renderOrderHistory();
    });

    document.querySelector("[data-demo-register]")?.addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = new FormData(form);
        if (data.get("password") !== data.get("confirm-password")) {
            showAuthFeedback(form, "As senhas não conferem.", false);
            return;
        }
        const account = {
            name: data.get("nome"),
            username: data.get("username")
        };
        localStorage.setItem(storageKeys.account, JSON.stringify(account));
        showAuthFeedback(form, "Perfil de demonstração salvo neste navegador. Entre com o nome de usuário para continuar.", true);
    });

    document.querySelector("[data-demo-login]")?.addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = new FormData(form);
        const account = JSON.parse(localStorage.getItem(storageKeys.account) ?? "null");
        if (!account || account.username !== data.get("username")) {
            showAuthFeedback(form, "Não há perfil local com esse usuário. Crie um perfil de demonstração primeiro.", false);
            return;
        }
        localStorage.setItem(storageKeys.customer, JSON.stringify(account));
        showAuthFeedback(form, "Perfil local reconhecido. Redirecionando para a prévia dos pedidos...", true);
        window.location.href = "pedidos.html";
    });
}

function showAuthFeedback(form, message, success) {
    const feedback = form.querySelector("[data-auth-feedback]");
    if (!feedback) return;
    feedback.textContent = message;
    feedback.dataset.state = success ? "success" : "error";
    feedback.hidden = false;
}

function setupAdminDemo() {
    const buttons = document.querySelectorAll("[data-admin-filter]");
    const rows = document.querySelectorAll("[data-admin-order]");
    if (!buttons.length || !rows.length) return;

    buttons.forEach((button) => button.addEventListener("click", () => {
        buttons.forEach((item) => item.classList.toggle("active", item === button));
        rows.forEach((row) => {
            row.hidden = row.dataset.adminOrder !== button.dataset.adminFilter;
        });
    }));

    document.querySelectorAll("[data-finish-order]").forEach((button) => button.addEventListener("click", () => {
        const row = button.closest("[data-admin-order]");
        row.dataset.adminOrder = "finalizados";
        const status = row.querySelector(".status-pill");
        status.textContent = "Finalizado";
        status.classList.remove("status-pill--process");
        status.classList.add("status-pill--done");
        button.textContent = "Finalizado";
        button.disabled = true;
        row.hidden = !document.querySelector("[data-admin-filter].active")?.dataset.adminFilter?.includes("finalizados");
    }));
}

document.querySelectorAll("[data-nav-toggle]").forEach((button) => {
    button.addEventListener("click", () => {
        const navigation = document.getElementById(button.getAttribute("aria-controls"));
        const expanded = button.getAttribute("aria-expanded") === "true";
        button.setAttribute("aria-expanded", String(!expanded));
        button.setAttribute("aria-label", expanded ? "Abrir menu" : "Fechar menu");
        navigation?.classList.toggle("is-open", !expanded);
    });
});

renderCatalog();
renderOrderCart();
renderOrderHistory();
setupCheckout();
setupDemoAccount();
setupAdminDemo();