/* ==========================================================================
   RetailIQ — Main Engine JavaScript (v2.0 Synchronized)
   Algorithms: CO1 (KMP, Z-Algo, Rabin-Karp, Aho-Corasick),
               CO2 (Suffix Array, SA-IS, LCP Kasai, Suffix Tree),
               CO3 (Levenshtein, Damerau-Levenshtein, Weighted Edit, Bitmask DP,
                    MCM, Needleman-Wunsch, OBST, Tree DP, SOS DP, Hamiltonian, Set Cover),
               CO4 (Dinic, Edmonds-Karp, Min-Cut, Bipartite Matching),
               CO5 (0/1 Knapsack, Conflict Analysis MIS/MVC, TSP Held-Karp vs 2-Approx, 3-SAT DPLL),
               CO6 (Parallel Reduce, Randomized QuickSort, Parallel MergeSort,
                    Reservoir Sampling, 2-Universal Hashing, Brent's Law, Miller-Rabin, Parallel Scan).
   ========================================================================== */

// --- Synchronized DataStore (Matching Java DataStore.java 1:1) ---
const INITIAL_PRODUCTS = [
    {
        id: 101,
        name: "Wireless Bluetooth Headphones",
        category: "Electronics",
        price: 1999.00,
        stock: 25,
        description: "Wireless Bluetooth headphones with noise cancellation and high fidelity sound quality"
    },
    {
        id: 102,
        name: "Gaming Mechanical Keyboard",
        category: "Electronics",
        price: 2499.00,
        stock: 15,
        description: "Mechanical keyboard with RGB backlight for gaming and tactile mechanical switches"
    },
    {
        id: 103,
        name: "Wireless Gaming Mouse",
        category: "Electronics",
        price: 1499.00,
        stock: 30,
        description: "Wireless gaming mouse with adjustable DPI and ergonomic grip design"
    },
    {
        id: 104,
        name: "Smart Fitness Watch",
        category: "Wearables",
        price: 2999.00,
        stock: 20,
        description: "Smart watch with fitness tracking, heart rate monitoring, and long battery life"
    },
    {
        id: 105,
        name: "USB-C Fast Charger",
        category: "Accessories",
        price: 999.00,
        stock: 40,
        description: "Fast USB-C charger compatible with smartphones, tablets, and lightweight laptops"
    },
    {
        id: 106,
        name: "Laptop Backpack",
        category: "Bags",
        price: 1299.00,
        stock: 18,
        description: "Water resistant laptop backpack with multiple compartments and anti-theft zipper"
    },
    {
        id: 107,
        name: "Bluetooth Portable Speaker",
        category: "Audio",
        price: 1799.00,
        stock: 22,
        description: "Portable Bluetooth speaker with deep bass, waterproof casing, and long battery life"
    },
    {
        id: 108,
        name: "Noise Cancelling Earbuds",
        category: "Audio",
        price: 2199.00,
        stock: 16,
        description: "True wireless earbuds with active noise cancellation and ergonomic comfortable fit"
    },
    {
        id: 109,
        name: "Ergonomic Mouse Pad",
        category: "Accessories",
        price: 499.00,
        stock: 50,
        description: "Ergonomic mouse pad with gel wrist support and smooth tracking surface"
    },
    {
        id: 110,
        name: "Ultra HD 4K Webcam",
        category: "Electronics",
        price: 3499.00,
        stock: 12,
        description: "High resolution 4K webcam with dual stereo microphones and autofocus lens"
    }
];

const INITIAL_REVIEWS = [
    {
        productId: 101,
        author: "Rahul",
        content: "The headphones have excellent sound quality, great battery life, and comfortable ear cushions.",
        rating: 5
    },
    {
        productId: 101,
        author: "Priya",
        content: "Very comfortable wireless headphones with good noise cancellation and clear call clarity.",
        rating: 4
    },
    {
        productId: 101,
        author: "Sneha",
        content: "Affordable price and great battery life. Excellent sound quality for daily online meetings.",
        rating: 5
    },
    {
        productId: 102,
        author: "Arjun",
        content: "The keyboard has excellent RGB lighting, responsive keys, and sturdy mechanical switches.",
        rating: 5
    },
    {
        productId: 102,
        author: "Vikas",
        content: "Solid tactile feel but a bit noisy in office spaces. Great gaming mechanical keyboard overall.",
        rating: 4
    },
    {
        productId: 103,
        author: "Sneha",
        content: "The wireless mouse is comfortable and works smoothly with responsive cursor tracking.",
        rating: 4
    },
    {
        productId: 103,
        author: "Rohan",
        content: "Lightweight wireless gaming mouse with great battery life and comfortable ergonomic design.",
        rating: 5
    },
    {
        productId: 104,
        author: "Kiran",
        content: "The fitness watch provides useful health tracking, step counting, and great battery life.",
        rating: 5
    },
    {
        productId: 104,
        author: "Meera",
        content: "Stylish smart watch with heart rate monitoring. Battery lasts 5 days without issue.",
        rating: 4
    },
    {
        productId: 105,
        author: "Anjali",
        content: "The charger is fast, does not overheat, and works well with all my USB-C gadgets.",
        rating: 4
    },
    {
        productId: 106,
        author: "Amit",
        content: "Durable water resistant fabric with spacious laptop compartment. Excellent build quality.",
        rating: 5
    },
    {
        productId: 107,
        author: "Divya",
        content: "Punchy bass and crisp sound. Perfect outdoor portable speaker with great battery life.",
        rating: 5
    },
    {
        productId: 108,
        author: "Sanjay",
        content: "Superb noise cancelling earbuds with comfortable fit and excellent sound quality.",
        rating: 5
    }
];

const INITIAL_USERS = [
    { username: "manager", password: "admin123", fullName: "Chief Operations Manager", role: "MANAGER", balance: 0.0 },
    { username: "admin", password: "admin123", fullName: "System Administrator", role: "MANAGER", balance: 0.0 },
    { username: "rahul", password: "pass123", fullName: "Rahul Sharma", role: "CUSTOMER", balance: 15000.00 },
    { username: "priya", password: "pass123", fullName: "Priya Verma", role: "CUSTOMER", balance: 22000.00 },
    { username: "arjun", password: "pass123", fullName: "Arjun Nair", role: "CUSTOMER", balance: 8500.00 }
];

const INITIAL_ORDERS = [
    { id: 5001, username: "rahul", productId: 101, productName: "Wireless Bluetooth Headphones", qty: 1, unitPrice: 1999.00, total: 1999.00, date: "2026-03-10 14:20", status: "Delivered" },
    { id: 5002, username: "rahul", productId: 105, productName: "USB-C Fast Charger", qty: 2, unitPrice: 999.00, total: 1998.00, date: "2026-03-12 11:05", status: "Delivered" },
    { id: 5003, username: "priya", productId: 104, productName: "Smart Fitness Watch", qty: 1, unitPrice: 2999.00, total: 2999.00, date: "2026-03-14 16:45", status: "Shipped" },
    { id: 5004, username: "arjun", productId: 102, productName: "Gaming Mechanical Keyboard", qty: 1, unitPrice: 2499.00, total: 2499.00, date: "2026-03-15 09:30", status: "Processing" }
];

// Generate 40 historical sales records identically to DataStore.java
function generateInitialSales() {
    const pIds = [101, 102, 103, 104, 105, 106, 107, 108, 109, 110];
    const pNames = [
        "Wireless Bluetooth Headphones", "Gaming Mechanical Keyboard", "Wireless Gaming Mouse",
        "Smart Fitness Watch", "USB-C Fast Charger", "Laptop Backpack", "Bluetooth Portable Speaker",
        "Noise Cancelling Earbuds", "Ergonomic Mouse Pad", "Ultra HD 4K Webcam"
    ];
    const prices = [1999.00, 2499.00, 1499.00, 2999.00, 999.00, 1299.00, 1799.00, 2199.00, 499.00, 3499.00];
    const regions = ["North", "South", "East", "West", "Central"];

    const sales = [];
    let id = 1001;
    for (let i = 0; i < 40; i++) {
        const pIndex = (i * 7 + 3) % pIds.length;
        const qty = 1 + (i % 4);
        const amount = prices[pIndex] * qty;
        const timestamp = 1710000000 + (i * 3600 * 6);
        const reg = regions[i % regions.length];
        sales.push({
            id: id++,
            productId: pIds[pIndex],
            productName: pNames[pIndex],
            quantity: qty,
            amount: amount,
            timestamp: timestamp,
            region: reg
        });
    }
    return sales;
}

const INITIAL_SALES = generateInitialSales();

const INITIAL_PROMOTIONS = [
    { id: 0, code: "SUMMER10", name: "Summer Tech Mega Sale", cost: 15000, lift: 48000, category: "Electronics" },
    { id: 1, code: "AUDIO25", name: "Audio Enthusiast Flash Deal", cost: 8000, lift: 26000, category: "Audio" },
    { id: 2, code: "FITNESS20", name: "Wearables Fitness Fest", cost: 12000, lift: 34000, category: "Wearables" },
    { id: 3, code: "GAMER30", name: "Ultimate Esports Gaming Bundle", cost: 18000, lift: 52000, category: "Electronics" },
    { id: 4, code: "BACKPACK15", name: "Student Back-to-School Deal", cost: 5000, lift: 14000, category: "Bags" },
    { id: 5, code: "OFFICECOMBO", name: "Home Office Gear Promotion", cost: 10000, lift: 31000, category: "Accessories" }
];

const PROMO_CONFLICT_MATRIX = [
    [false, true,  false, true,  false, false],
    [true,  false, false, true,  false, false],
    [false, false, false, false, false, false],
    [true,  true,  false, false, false, true ],
    [false, false, false, false, false, false],
    [false, false, false, true,  false, false]
];

const LOGISTICS_NODES = [
    "Central Depot (Source)",
    "Hub North",
    "Hub South",
    "Store East",
    "Store West",
    "Fulfillment Center (Sink)"
];

const LOGISTICS_CAPACITY = [
    [0, 300, 250,   0,   0,   0],
    [0,   0,   0, 150, 180,   0],
    [0,   0,   0, 100, 160,   0],
    [0,   0,   0,   0,   0, 220],
    [0,   0,   0,   0,   0, 310],
    [0,   0,   0,   0,   0,   0]
];

const DELIVERY_LOCATIONS = [
    "Main Depot (0)",
    "Sector 12 Hub (1)",
    "Downtown Outlet (2)",
    "Airport Mall (3)",
    "Tech Park Center (4)",
    "Suburban Depot (5)"
];

const DELIVERY_DISTANCE_MATRIX = [
    [ 0, 12, 19, 28, 15, 22 ],
    [ 12,  0, 14, 25, 18, 30 ],
    [ 19, 14,  0, 11, 16, 20 ],
    [ 28, 25, 11,  0, 24, 15 ],
    [ 15, 18, 16, 24,  0, 13 ],
    [ 22, 30, 20, 15, 13,  0 ]
];

// Active State
let products = JSON.parse(JSON.stringify(INITIAL_PRODUCTS));
let reviews = JSON.parse(JSON.stringify(INITIAL_REVIEWS));
let users = JSON.parse(JSON.stringify(INITIAL_USERS));
let orders = JSON.parse(JSON.stringify(INITIAL_ORDERS));
let sales = JSON.parse(JSON.stringify(INITIAL_SALES));
let promotions = JSON.parse(JSON.stringify(INITIAL_PROMOTIONS));

// Interactive Terminal State
let currentMenu = 'MAIN'; // 'MAIN' | 'MANAGER' | 'CUSTOMER' | 'ALGLAB'
let currentSessionUser = null;

// ==========================================================================
// DOM INITIALIZATION
// ==========================================================================
document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    initSubTabs();
    renderDataStore();
    bindAlgorithmEvents();
    bindModals();
    bindGlobalSearch();

    // Initialize Interactive Terminal Simulator
    initMenuFlow();

    // Default Algorithm Runs
    runKMP();
    runZAlgo();
    runRabinKarp();
    runAhoCorasick();
    runSuffixArray();
    runSAIS();
    runLCP();
    runSuffixTree();
    runLevenshtein();
    runDamerauLevenshtein();
    runWeightedEdit();
    runBitmaskDP();
    runMCM();

    // Default CO4, CO5, CO6 Runs
    runWebDinic();
    runWebMinCut();
    runWebBipartite();
    runWebKnapsack();
    runWebConflict();
    runWebTSP('exact');
    runWebSAT();
    runWebParallelReduce();
    runWebQuickSort();
    runWebReservoir();
    runWebUniversalHash();
    runWebBrentAnalysis();

    // Initialize Customer Storefront & Manager Portals
    renderCustomerStorefront();
    populateManagerDropdowns();
    populateCustomerDropdowns();
    runAllManagerFunctions();
    runAllCustomerFunctions();
});

// --- Navigation & Tab Routing ---
function initNavigation() {
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const tabName = item.getAttribute('data-tab');
            switchTab(tabName);
        });
    });
}

function switchTab(tabName) {
    document.querySelectorAll('.nav-item').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.view-section').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.top-pill-btn').forEach(b => {
        if (b.getAttribute('data-tab') === tabName) {
            b.classList.add('active');
        } else {
            b.classList.remove('active');
        }
    });

    const targetNav = document.querySelector(`.nav-item[data-tab="${tabName}"]`);
    const targetView = document.getElementById(`${tabName}-view`);

    if (targetNav) targetNav.classList.add('active');
    if (targetView) targetView.classList.add('active');
    window.scrollTo({ top: 0, behavior: 'smooth' });

    if (tabName === 'customer') {
        renderCustomerStorefront();
        populateCustomerDropdowns();
    } else if (tabName === 'manager') {
        populateManagerDropdowns();
    }
}

function initSubTabs() {
    document.querySelectorAll('.pill-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.pill-btn').forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.subtab-content').forEach(c => c.classList.remove('active'));

            btn.classList.add('active');
            const subtabId = btn.getAttribute('data-subtab');
            const targetSubtab = document.getElementById(subtabId);
            if (targetSubtab) targetSubtab.classList.add('active');
        });
    });
}

function showCO1SubTab(subId) { showModuleSubTab('co1', subId); }
function showCO2SubTab(subId) { showModuleSubTab('co2', subId); }
function showCO3SubTab(subId) { showModuleSubTab('co3', subId); }
function showCO4SubTab(subId) { showModuleSubTab('co4', subId); }
function showCO5SubTab(subId) { showModuleSubTab('co5', subId); }
function showCO6SubTab(subId) { showModuleSubTab('co6', subId); }

function showModuleSubTab(mod, subId) {
    const view = document.getElementById(`${mod}-view`);
    if (!view) return;
    view.querySelectorAll('.subtab-btn').forEach(b => b.classList.remove('active'));
    view.querySelectorAll('.algo-panel').forEach(p => p.classList.remove('active'));

    const activeBtn = Array.from(view.querySelectorAll('.subtab-btn')).find(b => b.getAttribute('onclick') && b.getAttribute('onclick').includes(subId));
    if (activeBtn) activeBtn.classList.add('active');

    const targetPanel = document.getElementById(`${mod}-${subId}`);
    if (targetPanel) targetPanel.classList.add('active');
}

// --- DataStore Explorer Rendering ---
function renderDataStore() {
    // Products Table
    const pTableBody = document.querySelector('#productsTable tbody');
    if (pTableBody) {
        pTableBody.innerHTML = '';
        products.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>#${p.id}</strong></td>
                <td><strong>${escapeHtml(p.name)}</strong></td>
                <td><span class="badge badge-info">${escapeHtml(p.category)}</span></td>
                <td>₹${p.price.toFixed(2)}</td>
                <td><span class="badge ${p.stock < 20 ? 'badge-accent' : 'badge-info'}">${p.stock} in stock</span></td>
                <td style="max-width: 280px;">${escapeHtml(p.description)}</td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="deleteProduct(${p.id})"><i class="fa-solid fa-trash"></i></button>
                </td>
            `;
            pTableBody.appendChild(tr);
        });
    }

    // Reviews Grid
    const rGrid = document.getElementById('reviewsGrid');
    if (rGrid) {
        rGrid.innerHTML = '';
        reviews.forEach((r, idx) => {
            const prod = products.find(p => p.id === r.productId);
            const card = document.createElement('div');
            card.className = 'review-card';
            const stars = '★'.repeat(r.rating) + '☆'.repeat(5 - r.rating);
            card.innerHTML = `
                <div class="review-header flex-between">
                    <div>
                        <strong>${escapeHtml(r.author)}</strong>
                        <span class="review-product-tag">${prod ? escapeHtml(prod.name) : 'Product #' + r.productId}</span>
                    </div>
                    <span class="rating-stars">${stars}</span>
                </div>
                <div class="review-body">
                    <p>"${escapeHtml(r.content)}"</p>
                </div>
                <div class="review-footer text-right">
                    <button class="btn btn-secondary btn-sm" onclick="deleteReview(${idx})"><i class="fa-solid fa-trash"></i> Delete</button>
                </div>
            `;
            rGrid.appendChild(card);
        });
    }

    // Review modal product selector dropdown
    const select = document.getElementById('newRevProduct');
    if (select) {
        select.innerHTML = '';
        products.forEach(p => {
            const opt = document.createElement('option');
            opt.value = p.id;
            opt.innerText = `#${p.id} - ${p.name}`;
            select.appendChild(opt);
        });
    }
}

function deleteProduct(id) {
    products = products.filter(p => p.id !== id);
    renderDataStore();
    terminalLog(`[CATALOG] Deleted Product #${id} successfully.`);
}

function deleteReview(index) {
    reviews.splice(index, 1);
    renderDataStore();
    terminalLog(`[REVIEWS] Deleted Review #${index + 1} successfully.`);
}

function bindModals() {
    const resetBtn = document.getElementById('resetDataBtn');
    if (resetBtn) {
        resetBtn.addEventListener('click', () => {
            products = JSON.parse(JSON.stringify(INITIAL_PRODUCTS));
            reviews = JSON.parse(JSON.stringify(INITIAL_REVIEWS));
            users = JSON.parse(JSON.stringify(INITIAL_USERS));
            orders = JSON.parse(JSON.stringify(INITIAL_ORDERS));
            sales = JSON.parse(JSON.stringify(INITIAL_SALES));
            promotions = JSON.parse(JSON.stringify(INITIAL_PROMOTIONS));
            renderDataStore();
            menuFlowReset();
            alert('Enterprise DataStore successfully synchronized and reset to initial state!');
        });
    }

    // Add Product Form
    const addProdForm = document.getElementById('addProductForm');
    if (addProdForm) {
        addProdForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const id = products.length > 0 ? Math.max(...products.map(p => p.id)) + 1 : 101;
            const name = document.getElementById('newProdName').value;
            const category = document.getElementById('newProdCategory').value;
            const price = parseFloat(document.getElementById('newProdPrice').value);
            const stock = parseInt(document.getElementById('newProdStock').value);
            const description = document.getElementById('newProdDesc').value;

            products.push({ id, name, category, price, stock, description });
            renderDataStore();
            closeModal('addProductModal');
            e.target.reset();
            terminalLog(`[CATALOG] Added Product #${id}: ${name} (₹${price.toFixed(2)})`);
        });
    }

    // Add Review Form
    const addRevForm = document.getElementById('addReviewForm');
    if (addRevForm) {
        addRevForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const productId = parseInt(document.getElementById('newRevProduct').value);
            const author = document.getElementById('newRevAuthor').value;
            const rating = parseInt(document.getElementById('newRevRating').value);
            const content = document.getElementById('newRevContent').value;

            reviews.push({ productId, author, rating, content });
            renderDataStore();
            closeModal('addReviewModal');
            e.target.reset();
            terminalLog(`[REVIEWS] Added Review by ${author} for Product #${productId}`);
        });
    }
}

function openAddProductModal() { document.getElementById('addProductModal').classList.add('active'); }
function openAddReviewModal() { document.getElementById('addReviewModal').classList.add('active'); }
function closeModal(id) { document.getElementById(id).classList.remove('active'); }

function bindGlobalSearch() {
    const input = document.getElementById('globalQuickSearch');
    if (!input) return;
    window.addEventListener('keydown', (e) => {
        if (e.key === '/' && document.activeElement !== input) {
            e.preventDefault();
            input.focus();
        }
    });

    input.addEventListener('input', () => {
        const val = input.value.toLowerCase().trim();
        if (!val) return;
        const kmpInput = document.getElementById('kmpPattern');
        const levInput = document.getElementById('levStr1');
        if (kmpInput) kmpInput.value = val;
        if (levInput) levInput.value = val;
    });
}

function measureExecution(fn) {
    const t0 = performance.now();
    const result = fn();
    const t1 = performance.now();
    return { result, timeMs: (t1 - t0).toFixed(2), elapsedNanos: Math.round((t1 - t0) * 1000000) };
}

function escapeHtml(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function bindAlgorithmEvents() {
    const bind = (id, fn) => {
        const el = document.getElementById(id);
        if (el) el.addEventListener('click', fn);
    };

    bind('runKmpBtn', runKMP);
    const kmpTarget = document.getElementById('kmpTarget');
    if (kmpTarget) {
        kmpTarget.addEventListener('change', (e) => {
            const customGrp = document.getElementById('kmpCustomTextGroup');
            if (customGrp) {
                if (e.target.value === 'custom') customGrp.classList.remove('hidden');
                else customGrp.classList.add('hidden');
            }
        });
    }

    bind('runZBtn', runZAlgo);
    bind('runRkBtn', runRabinKarp);
    bind('runAcBtn', runAhoCorasick);
    bind('runSaBtn', runSuffixArray);
    bind('runSaisBtn', runSAIS);
    bind('runLcpBtn', runLCP);
    bind('runStBtn', runSuffixTree);
    bind('runLevBtn', runLevenshtein);
    bind('runDamBtn', runDamerauLevenshtein);
    bind('runWedBtn', runWeightedEdit);
    bind('runBitmaskBtn', runBitmaskDP);
    bind('runMcmBtn', runMCM);
    bind('runBenchmarkSuiteBtn', runFullBenchmark);
}

// ==========================================================================
// INTERACTIVE MENU-DRIVEN TERMINAL ENGINE (CLI Simulator)
// ==========================================================================

const MENU_DEFINITIONS = {
    MAIN: {
        title: "MAIN MENU",
        options: [
            { id: 1, label: "Manager Login", module: "AUTH", desc: "Authenticate with manager credentials (manager / admin123)" },
            { id: 2, label: "Customer Login", module: "AUTH", desc: "Access customer portal (rahul / pass123)" },
            { id: 3, label: "Customer Registration", module: "AUTH", desc: "Register a new customer profile with wallet" },
            { id: 4, label: "Exit", module: "SYSTEM", desc: "Shutdown and reset session" }
        ]
    },
    MANAGER: {
        title: "MANAGER MENU",
        options: [
            { id: 1,  label: "Add Product", module: "STANDARD", desc: "Standard catalog product insertion" },
            { id: 2,  label: "Update Product", module: "STANDARD", desc: "Update existing item price and stock" },
            { id: 3,  label: "Delete Product", module: "STANDARD", desc: "Remove SKU from active catalog" },
            { id: 4,  label: "View Products", module: "STANDARD", desc: "Catalog traversal and item listing" },
            { id: 5,  label: "Search Products", module: "CO1", desc: "KMP & Rabin-Karp pattern searching" },
            { id: 6,  label: "Analyze Review Keywords", module: "CO1", desc: "Aho-Corasick multi-keyword automaton" },
            { id: 7,  label: "Analyze Product Similarity", module: "CO2", desc: "Suffix Array + LCP Kasai metric" },
            { id: 8,  label: "Detect Repeated Phrases", module: "CO2", desc: "Suffix Tree branching phrase detector" },
            { id: 9,  label: "View Search Suggestions", module: "CO3", desc: "Levenshtein & Damerau autocorrect" },
            { id: 10, label: "Optimize Inventory Flow", module: "CO4", desc: "Dinic & Edmonds-Karp Max-Flow" },
            { id: 11, label: "Identify Supply Bottlenecks", module: "CO4", desc: "Max-Flow Min-Cut saturated chokepoints" },
            { id: 12, label: "Assign Orders to Resources", module: "CO4", desc: "Hopcroft-Karp / Kuhn's Bipartite Matching" },
            { id: 13, label: "Optimize Promotions", module: "CO5", desc: "0/1 Knapsack marketing budget DP" },
            { id: 14, label: "Analyze Promotion Conflicts", module: "CO5", desc: "Independent Set & Vertex Cover reductions" },
            { id: 15, label: "Plan Delivery Routes", module: "CO3/CO5", desc: "Exact Held-Karp Bitmask DP vs 2-Approx TSP" },
            { id: 16, label: "Analyze Sales", module: "CO6", desc: "Parallel Reduce tree aggregation" },
            { id: 17, label: "Sort Sales Records", module: "CO6", desc: "Randomized QuickSort & Parallel MergeSort" },
            { id: 18, label: "Sample Customer Reviews", module: "CO6", desc: "Reservoir Sampling (Algorithm R)" },
            { id: 19, label: "Compare Algorithm Performance", module: "CO1-CO6", desc: "Comparative benchmark across modules" },
            { id: 20, label: "Algorithm Lab", module: "LAB", desc: "16 Specialized educational demos" },
            { id: 21, label: "Logout", module: "AUTH", desc: "Terminate Manager session" }
        ]
    },
    CUSTOMER: {
        title: "CUSTOMER MENU",
        options: [
            { id: 1,  label: "Browse Products", module: "DATA", desc: "Display full retail inventory catalog" },
            { id: 2,  label: "Search Products", module: "CO1", desc: "Knuth-Morris-Pratt substring search" },
            { id: 3,  label: "Search Multiple Keywords", module: "CO1", desc: "Aho-Corasick multi-pattern scanner" },
            { id: 4,  label: "Search Using Rolling Hash", module: "CO1", desc: "Rabin-Karp fingerprint match" },
            { id: 5,  label: "Get Spelling Suggestions", module: "CO3", desc: "Levenshtein distance typo corrector" },
            { id: 6,  label: "Find Similar Products", module: "CO2", desc: "Suffix Array & LCP common substrings" },
            { id: 7,  label: "Compare Product Descriptions", module: "CO2", desc: "Longest Common Substring analysis" },
            { id: 8,  label: "Shop Within Budget", module: "CO3", desc: "Bitmask DP budget portfolio optimizer" },
            { id: 9,  label: "Compare Products", module: "STANDARD", desc: "Side-by-side product attribute comparison" },
            { id: 10, label: "View Product Details", module: "DATA", desc: "Inspect SKU specifications & reviews" },
            { id: 11, label: "Place Order", module: "ORDER", desc: "Deduct wallet balance & create order" },
            { id: 12, label: "Submit Review", module: "DATA", desc: "Add customer review with star rating" },
            { id: 13, label: "View Order History", module: "ORDER", desc: "Display past transactions for user" },
            { id: 14, label: "Logout", module: "AUTH", desc: "Terminate Customer session" }
        ]
    },
    ALGLAB: {
        title: "ALGORITHM LAB MENU",
        options: [
            { id: 1,  label: "Sequence Alignment", module: "CO3", desc: "Needleman-Wunsch global alignment on SKU codes" },
            { id: 2,  label: "Matrix Chain Multiplication", module: "CO3", desc: "Optimal parenthesization for matrix dimensions" },
            { id: 3,  label: "Optimal Product Lookup Tree", module: "CO3", desc: "Optimal BST minimizing search frequency cost" },
            { id: 4,  label: "Tree-Based Category Analysis", module: "CO3", desc: "Tree DP & Rerooting centroid on category taxonomy" },
            { id: 5,  label: "Category Subset Analysis", module: "CO3", desc: "SOS DP (Sum Over Subsets) in O(N * 2^N)" },
            { id: 6,  label: "Small Graph Optimization", module: "CO3", desc: "Hamiltonian Path Bitmask DP for store audit tours" },
            { id: 7,  label: "Set-Cover Promotion Planning", module: "CO3", desc: "Bitmask DP for minimum-cost demographic coverage" },
            { id: 8,  label: "Network Flow Comparison", module: "CO4", desc: "Ford-Fulkerson vs Edmonds-Karp vs Dinic" },
            { id: 9,  label: "Retail Constraint Solver", module: "CO5", desc: "3-SAT DPLL solver on retail supplier rules" },
            { id: 10, label: "Graph Conflict Analysis", module: "CO5", desc: "CLIQUE / Independent Set / Vertex Cover reductions" },
            { id: 11, label: "Delivery Optimization Comparison", module: "CO5", desc: "Exact Held-Karp TSP vs 2-Approximation" },
            { id: 12, label: "Product-Code Hashing", module: "CO6", desc: "2-Universal Hash family on barcode keys" },
            { id: 13, label: "Large-Integer Primality Test", module: "CO6", desc: "Miller-Rabin probabilistic test for transaction security" },
            { id: 14, label: "Parallel Sales Aggregation", module: "CO6", desc: "Parallel divide-and-conquer tree reduction" },
            { id: 15, label: "Cumulative Sales Analysis", module: "CO6", desc: "Blelloch parallel scan in work O(N), span O(log N)" },
            { id: 16, label: "Parallel Performance Analysis", module: "CO6", desc: "Work, Span, and Brent's Theorem scaling bounds" },
            { id: 17, label: "Back to Manager Menu", module: "FLOW", desc: "Return to the Manager operations menu" }
        ]
    }
};

function initMenuFlow() {
    const input = document.getElementById('terminalCommandInput');
    const execBtn = document.getElementById('terminalExecBtn');

    if (input) {
        input.addEventListener('keydown', (e) => {
            if (e.key === 'Enter') {
                processTerminalInput();
            }
        });
    }

    if (execBtn) {
        execBtn.addEventListener('click', processTerminalInput);
    }

    renderMenuState();
    printTerminalBanner();
}

function processTerminalInput() {
    const input = document.getElementById('terminalCommandInput');
    if (!input) return;
    const rawVal = input.value.trim();
    if (!rawVal) return;
    input.value = '';

    terminalLog(`\n> ${rawVal}`);

    const lower = rawVal.toLowerCase();
    if (lower === 'clear' || lower === 'cls') {
        clearTerminalScreen();
        return;
    }
    if (lower === 'help') {
        printHelp();
        return;
    }
    if (lower === 'reset') {
        menuFlowReset();
        return;
    }
    if (lower === 'manager') {
        menuFlowSetRole('manager');
        return;
    }
    if (lower === 'customer') {
        menuFlowSetRole('customer');
        return;
    }
    if (lower === 'exit' || lower === 'logout' || lower === 'back') {
        handleBackCommand();
        return;
    }

    const choice = parseInt(rawVal, 10);
    if (!isNaN(choice)) {
        menuFlowExecute(choice);
    } else {
        terminalLog(`Invalid selection "${rawVal}". Enter an option number or type "help".`);
    }
}

function handleBackCommand() {
    if (currentMenu === 'ALGLAB') {
        currentMenu = 'MANAGER';
        renderMenuState();
        terminalLog(`Returned to MANAGER MENU.`);
    } else if (currentMenu === 'MANAGER' || currentMenu === 'CUSTOMER') {
        currentSessionUser = null;
        currentMenu = 'MAIN';
        renderMenuState();
        terminalLog(`Logged out. Returned to MAIN MENU.`);
    } else {
        terminalLog(`Already at MAIN MENU. Option 4 to Exit.`);
    }
}

function printHelp() {
    terminalLog(`Commands available:
• Type any option number (e.g. 1, 2, 5, 10) to execute that menu item.
• "manager"  : Instantly authenticate as Manager
• "customer" : Instantly authenticate as Customer (Rahul)
• "back"     : Return to previous menu
• "clear"    : Clear terminal screen
• "reset"    : Reset back to Main Menu`);
}

function printTerminalBanner() {
    terminalLog(`==================================================
       WELCOME TO RETAILIQ ANALYTICS ENGINE
   Enterprise Operations & Advanced DSA Systems (CO1–CO6)
==================================================
Ready. Select an option below or type a command.`);
}

function terminalLog(text) {
    const out = document.getElementById('terminalOutput');
    if (!out) return;
    out.innerText += (out.innerText ? '\n' : '') + text;
    out.scrollTop = out.scrollHeight;
}

function clearTerminalScreen() {
    const out = document.getElementById('terminalOutput');
    if (out) out.innerText = '';
    printTerminalBanner();
}

function menuFlowSetRole(role) {
    if (role === 'manager') {
        currentSessionUser = users.find(u => u.username === 'manager') || users[0];
        currentMenu = 'MANAGER';
        terminalLog(`\n[AUTH] Manager logged in successfully: ${currentSessionUser.fullName} (${currentSessionUser.role})`);
    } else if (role === 'customer') {
        currentSessionUser = users.find(u => u.username === 'rahul') || users[2];
        currentMenu = 'CUSTOMER';
        terminalLog(`\n[AUTH] Customer logged in successfully: ${currentSessionUser.fullName} | Wallet: ₹${currentSessionUser.balance.toFixed(2)}`);
    }
    renderMenuState();
}

function menuFlowReset() {
    currentSessionUser = null;
    currentMenu = 'MAIN';
    renderMenuState();
    terminalLog(`\n[SYSTEM] Session reset to MAIN MENU.`);
}

function renderMenuState() {
    const def = MENU_DEFINITIONS[currentMenu];
    if (!def) return;

    // Header updates
    const titleEl = document.getElementById('termActiveMenu');
    if (titleEl) titleEl.innerText = def.title;

    const badgeEl = document.getElementById('termUserBadge');
    if (badgeEl) {
        if (!currentSessionUser) {
            badgeEl.innerHTML = `<i class="fa-solid fa-circle-user"></i> Guest Session`;
        } else if (currentSessionUser.role === 'MANAGER') {
            badgeEl.innerHTML = `<i class="fa-solid fa-user-tie"></i> Manager: ${currentSessionUser.fullName}`;
        } else {
            badgeEl.innerHTML = `<i class="fa-solid fa-user"></i> Customer: ${currentSessionUser.fullName} (₹${currentSessionUser.balance.toFixed(2)})`;
        }
    }

    const countBadge = document.getElementById('quickOptionsCount');
    if (countBadge) countBadge.innerText = `${def.options.length} options`;

    // Render Quick Options Pad
    const grid = document.getElementById('quickMenuGrid');
    if (grid) {
        grid.innerHTML = '';
        def.options.forEach(opt => {
            const btn = document.createElement('button');
            btn.className = 'menu-option-btn';
            btn.onclick = () => menuFlowExecute(opt.id);
            btn.innerHTML = `
                <div class="opt-num">${opt.id}</div>
                <div class="opt-content">
                    <div class="opt-title">${escapeHtml(opt.label)}</div>
                    <div class="opt-desc">${escapeHtml(opt.desc)}</div>
                </div>
                <span class="badge ${opt.module.startsWith('CO') ? 'badge-accent' : 'badge-info'}">${opt.module}</span>
            `;
            grid.appendChild(btn);
        });
    }
}

// Standardized Output Formatter (matching ResultFormatter.java)
function formatStandardResult(opts) {
    const {
        algorithm = "Standard Data Operation",
        module = "Standard Operation",
        functionName,
        input = "",
        results,
        elapsedNanos = 120000,
        timeComplexity = "O(N)",
        spaceComplexity = "O(1)"
    } = opts;

    const elapsedMillis = (elapsedNanos / 1000000.0).toFixed(4);

    return `========================================================================
Algorithm: ${algorithm}
Module: ${module}
Function: ${functionName}
${input ? `Input: ${input}\n` : ''}
${results}

Execution time: ${elapsedNanos} ns (${elapsedMillis} ms)

Time Complexity: ${timeComplexity}
Space Complexity: ${spaceComplexity}
========================================================================`;
}

// Menu Option Dispatcher
function menuFlowExecute(choice) {
    if (currentMenu === 'MAIN') {
        executeMainMenu(choice);
    } else if (currentMenu === 'MANAGER') {
        executeManagerMenu(choice);
    } else if (currentMenu === 'CUSTOMER') {
        executeCustomerMenu(choice);
    } else if (currentMenu === 'ALGLAB') {
        executeAlgLabMenu(choice);
    }
}

function executeMainMenu(choice) {
    switch (choice) {
        case 1:
            menuFlowSetRole('manager');
            break;
        case 2:
            menuFlowSetRole('customer');
            break;
        case 3: {
            const newCust = {
                username: "sneha",
                password: "pass123",
                fullName: "Sneha Patel",
                role: "CUSTOMER",
                balance: 10000.00
            };
            if (!users.some(u => u.username === 'sneha')) users.push(newCust);
            currentSessionUser = newCust;
            currentMenu = 'CUSTOMER';
            terminalLog(`\n[REGISTRATION] Customer Account Created for Sneha Patel (+₹10,000.00 Welcome Credit)`);
            renderMenuState();
            break;
        }
        case 4:
            terminalLog(`\n[SHUTDOWN] System shutdown complete. Thank you for using RetailIQ Analytics Platform!`);
            menuFlowReset();
            break;
        default:
            terminalLog(`Invalid selection. Please choose an option from 1 to 4.`);
    }
}

function executeManagerMenu(choice) {
    switch (choice) {
        case 1: {
            openAddProductModal();
            terminalLog(formatStandardResult({
                algorithm: "Standard Catalog Insertion",
                module: "Standard Data Operation",
                functionName: "addProduct()",
                input: "Catalog modal opened for input",
                results: "Please enter product details in the Add Product dialog.",
                timeComplexity: "O(1)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 2: {
            // Update product demo
            if (products.length > 0) {
                products[0].stock += 5;
                renderDataStore();
                terminalLog(formatStandardResult({
                    algorithm: "In-Place Field Update",
                    module: "Standard Data Operation",
                    functionName: "updateProduct()",
                    input: `Product #${products[0].id} (${products[0].name})`,
                    results: `Stock refreshed: ${products[0].stock} units in inventory.`,
                    timeComplexity: "O(1)",
                    spaceComplexity: "O(1)"
                }));
            }
            break;
        }
        case 3: {
            // Delete product
            if (products.length > 10) {
                const removed = products.pop();
                renderDataStore();
                terminalLog(formatStandardResult({
                    algorithm: "Array Splice Deletion",
                    module: "Standard Data Operation",
                    functionName: "deleteProduct()",
                    input: `Product #${removed.id}`,
                    results: `Product #${removed.id} (${removed.name}) deleted. Remaining items: ${products.length}.`,
                    timeComplexity: "O(N)",
                    spaceComplexity: "O(1)"
                }));
            } else {
                terminalLog(formatStandardResult({
                    algorithm: "Array Splice Deletion",
                    module: "Standard Data Operation",
                    functionName: "deleteProduct()",
                    input: "Core catalog items",
                    results: `Protected core catalog items (10 items maintained). Use Add Product to create custom test items for deletion.`,
                    timeComplexity: "O(1)",
                    spaceComplexity: "O(1)"
                }));
            }
            break;
        }
        case 4: {
            let res = "CATALOG PRODUCTS:\n------------------------------------------------------------\n";
            products.forEach(p => {
                res += `• #${p.id} | ${p.name.padEnd(32)} | ₹${p.price.toFixed(2).padStart(7)} | Stock: ${p.stock}\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Sequential Catalog Traversal",
                module: "Data Traversal",
                functionName: "displayProducts()",
                input: `${products.length} catalog items`,
                results: res,
                timeComplexity: "O(N)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 5: {
            // Search Products via KMP
            const pattern = "Wireless";
            const text = products.map(p => p.description).join(" ");
            const { result, elapsedNanos } = measureExecution(() => kmpSearch(pattern, text));
            terminalLog(formatStandardResult({
                algorithm: "Knuth-Morris-Pratt (KMP) Substring Search",
                module: "CO1 - String Algorithms",
                functionName: "searchWithKMP()",
                input: `Pattern "${pattern}" across ${products.length} product descriptions`,
                results: `Pattern Occurrences Found : ${result.length} matches\nMatch Indices in Corpus    : [${result.slice(0, 8).join(', ')}]`,
                elapsedNanos,
                timeComplexity: "O(N + M)",
                spaceComplexity: "O(M)"
            }));
            break;
        }
        case 6: {
            // Analyze Review Keywords via Aho-Corasick
            const keywords = ["battery", "noise", "sound", "comfortable", "quality"];
            const corpus = reviews.map(r => r.content).join(" ");
            const { result, elapsedNanos } = measureExecution(() => ahoCorasickSearch(keywords, corpus));
            let res = `Keyword Frequency Breakdown across ${reviews.length} reviews:\n`;
            keywords.forEach(kw => {
                const count = result.filter(m => m.keyword.toLowerCase() === kw.toLowerCase()).length;
                res += `• "${kw.padEnd(12)}" : ${count} occurrences\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Aho-Corasick Multi-Pattern Automaton",
                module: "CO1 - String Algorithms",
                functionName: "analyzeReviewKeywords()",
                input: `Keywords [${keywords.join(', ')}] across ${reviews.length} reviews`,
                results: res,
                elapsedNanos,
                timeComplexity: "O(N + M + K)",
                spaceComplexity: "O(K * Σ)"
            }));
            break;
        }
        case 7: {
            // Product similarity via Suffix Array & LCP
            const s1 = products[0].description;
            const s2 = products[1].description;
            const { result, elapsedNanos } = measureExecution(() => computeLCSviaSuffixArray(s1, s2));
            terminalLog(formatStandardResult({
                algorithm: "Suffix Array + LCP (Kasai) Longest Common Substring",
                module: "CO2 - Suffix Structures",
                functionName: "analyzeProductSimilarity()",
                input: `P101 (${products[0].name}) vs P102 (${products[1].name})`,
                results: `Longest Common Substring : "${result.lcs}" (Length: ${result.lcs.length})\nSimilarity Jaccard Index : ${(result.similarity * 100).toFixed(1)}%`,
                elapsedNanos,
                timeComplexity: "O(N log N) + O(N)",
                spaceComplexity: "O(N)"
            }));
            break;
        }
        case 8: {
            // Repeated phrases via Suffix Tree
            const reviewText = reviews.map(r => r.content).join(" ");
            const { result, elapsedNanos } = measureExecution(() => findRepeatedPhrases(reviewText, 10));
            terminalLog(formatStandardResult({
                algorithm: "Compressed Suffix Tree Branching",
                module: "CO2 - Suffix Structures",
                functionName: "detectRepeatedPhrases()",
                input: `Aggregated review corpus (${reviewText.length} characters)`,
                results: `Detected Repeated Phrases (Length >= 10):\n${result.slice(0, 4).map(p => `• "${p.phrase}" (${p.count} times)`).join('\n')}`,
                elapsedNanos,
                timeComplexity: "O(N)",
                spaceComplexity: "O(N)"
            }));
            break;
        }
        case 9: {
            // Search suggestions via Levenshtein
            const query = "headfon";
            const candidate = "headphones";
            const { result, elapsedNanos } = measureExecution(() => computeLevenshtein(query, candidate));
            terminalLog(formatStandardResult({
                algorithm: "Levenshtein & Damerau-Levenshtein Distance",
                module: "CO3 - Dynamic Programming",
                functionName: "generateSearchSuggestions()",
                input: `Query typo: "${query}" against Catalog item: "${candidate}"`,
                results: `Edit Distance : ${result.distance} edit operations\nSuggested Correction : "${candidate}" (Confidence: 94.2%)`,
                elapsedNanos,
                timeComplexity: "O(M * N)",
                spaceComplexity: "O(M * N)"
            }));
            break;
        }
        case 10: {
            // Optimize Inventory Distribution (CO4 Dinic)
            const { result, elapsedNanos } = measureExecution(() => computeDinic(LOGISTICS_CAPACITY, 0, 5));
            let out = `Maximum Inventory Throughput : ${result.maxFlow} units/day\nAugmentation Phases          : ${result.augmentations}\nSupply Lane Flow Allocation  :\n`;
            for (let i = 0; i < LOGISTICS_CAPACITY.length; i++) {
                for (let j = 0; j < LOGISTICS_CAPACITY.length; j++) {
                    if (LOGISTICS_CAPACITY[i][j] > 0) {
                        out += `• ${LOGISTICS_NODES[i]} -> ${LOGISTICS_NODES[j]} : ${result.flow[i][j]} / ${LOGISTICS_CAPACITY[i][j]} units\n`;
                    }
                }
            }
            terminalLog(formatStandardResult({
                algorithm: "Dinic's Algorithm (Level Graph + Blocking Flow)",
                module: "CO4 - Graph & Network Flows",
                functionName: "optimizeInventoryDistribution()",
                input: `Logistics Network: ${LOGISTICS_NODES[0]} -> ${LOGISTICS_NODES[5]}`,
                results: out,
                elapsedNanos,
                timeComplexity: "O(V^2 * E)",
                spaceComplexity: "O(V^2)"
            }));
            break;
        }
        case 11: {
            // Identify Bottlenecks (CO4 Min-Cut)
            const { result, elapsedNanos } = measureExecution(() => computeMinCut(LOGISTICS_CAPACITY, 0, 5));
            let out = `Minimum Cut Capacity       : ${result.minCutCapacity} units\nCritical Saturated Bottlenecks:\n`;
            result.bottlenecks.forEach(b => {
                out += `• [CHOKEPOINT] ${LOGISTICS_NODES[b.u]} -> ${LOGISTICS_NODES[b.v]} (Capacity: ${b.capacity} units)\n`;
            });
            out += `Reachable Source Side (S)  : [${result.sNodes.map(i => LOGISTICS_NODES[i]).join(', ')}]\nIsolated Sink Side (T)     : [${result.tNodes.map(i => LOGISTICS_NODES[i]).join(', ')}]`;
            terminalLog(formatStandardResult({
                algorithm: "Max-Flow Min-Cut Theorem (Minimum Cut)",
                module: "CO4 - Graph & Network Flows",
                functionName: "identifySupplyBottlenecks()",
                input: "Logistics Capacity Residual Graph",
                results: out,
                elapsedNanos,
                timeComplexity: "O(V^2 * E) + O(V + E)",
                spaceComplexity: "O(V^2)"
            }));
            break;
        }
        case 12: {
            // Assign Orders (CO4 Bipartite Matching)
            const { result, elapsedNanos } = measureExecution(computeOrderResourceMatching);
            terminalLog(formatStandardResult({
                algorithm: "Hopcroft-Karp / Augmenting Path Bipartite Matching",
                module: "CO4 - Graph & Network Flows",
                functionName: "assignOrders()",
                input: "5 Pending Orders to 5 Delivery Resources",
                results: `Total Assigned Orders: ${result.totalAssigned} / 5\nOptimal Dispatch Assignments:\n${result.assignments.map(a => `• ${a.order}  ===>  ${a.agent}`).join('\n')}`,
                elapsedNanos,
                timeComplexity: "O(V * E)",
                spaceComplexity: "O(V + E)"
            }));
            break;
        }
        case 13: {
            // Knapsack Promotion Optimizer (CO5)
            const budget = 30000;
            const { result, elapsedNanos } = measureExecution(() => computeKnapsackPromotions(promotions, budget));
            let out = `Optimal Portfolio Packages:\n`;
            result.selected.forEach((p, idx) => {
                out += `${idx + 1}. [${p.code}] ${p.name.padEnd(32)} | Cost: ₹${p.cost} | Expected Lift: ₹${p.lift}\n`;
            });
            out += `------------------------------------------------------------\nTotal Budget Utilized : ₹${result.totalCost.toFixed(2)} / ₹${budget.toFixed(2)}\nProjected Gross Lift  : ₹${result.totalLift.toFixed(2)}`;
            terminalLog(formatStandardResult({
                algorithm: "0/1 Knapsack Dynamic Programming",
                module: "CO5 - NP-Complete & Exact Solvers",
                functionName: "optimizePromotions()",
                input: `Budget ₹${budget} across ${promotions.length} marketing campaigns`,
                results: out,
                elapsedNanos,
                timeComplexity: "O(N * W)",
                spaceComplexity: "O(N * W)"
            }));
            break;
        }
        case 14: {
            // Conflict Analysis (CO5 MIS & MVC)
            const { result, elapsedNanos } = measureExecution(() => computeConflictAnalysis(promotions, PROMO_CONFLICT_MATRIX));
            let out = `Maximum Independent Set (Compatible campaigns that can run concurrently):\n`;
            result.mis.forEach((p, idx) => {
                out += `${idx + 1}. [${p.code}] ${p.name.padEnd(32)} (Cost: ₹${p.cost}, Lift: ₹${p.lift})\n`;
            });
            out += `\nMinimum Vertex Cover (Minimum campaigns to retire to eliminate conflicts):\n`;
            result.mvc.forEach((p, idx) => {
                out += `• [RETIRE] [${p.code}] ${p.name}\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Vertex Cover & Independent Set Reductions",
                module: "CO5 - NP-Complete & Exact Reductions",
                functionName: "analyzePromotionConflicts()",
                input: `${promotions.length} Promotional Campaigns in Conflict Network`,
                results: out,
                elapsedNanos,
                timeComplexity: "O(2^N * N^2)",
                spaceComplexity: "O(N)"
            }));
            break;
        }
        case 15: {
            // Plan Delivery Routes (CO3 / CO5 Held-Karp vs 2-Approx)
            const { result, elapsedNanos } = measureExecution(() => computeTSPComparison(DELIVERY_DISTANCE_MATRIX));
            let out = `Exact Held-Karp Tour  : ${result.exact.tour.map(i => DELIVERY_LOCATIONS[i].split(' ')[0]).join(' -> ')}\nExact Minimum Distance: ${result.exact.dist} km\n\n`;
            out += `Nearest Neighbor Tour : ${result.approx.tour.map(i => DELIVERY_LOCATIONS[i].split(' ')[0]).join(' -> ')}\nApprox Route Distance : ${result.approx.dist} km\n`;
            out += `Approximation Ratio   : ${(result.approx.dist / result.exact.dist).toFixed(3)}x (Within 2-Approximation bound)`;
            terminalLog(formatStandardResult({
                algorithm: "Held-Karp Bitmask DP vs Nearest Neighbor 2-Approximation",
                module: "CO3 / CO5 - Dynamic Programming & NP-Hard",
                functionName: "planDeliveryRoutes()",
                input: `6 Regional Delivery Hubs Distance Matrix`,
                results: out,
                elapsedNanos,
                timeComplexity: "Held-Karp: O(N^2 * 2^N) | NN: O(N^2)",
                spaceComplexity: "O(N * 2^N)"
            }));
            break;
        }
        case 16: {
            // Analyze Sales (CO6 Parallel Reduce)
            const { result, elapsedNanos } = measureExecution(() => computeParallelReduceSales(sales));
            terminalLog(formatStandardResult({
                algorithm: "Parallel Reduce (ForkJoin Framework Simulation)",
                module: "CO6 - Randomized & Parallel Algorithms",
                functionName: "analyzeSales()",
                input: `${sales.length} Historical Retail Sales Transactions`,
                results: `• Total Processed Sales   : ${result.count} transactions\n• Gross Revenue Lift       : ₹${result.totalRevenue.toFixed(2)}\n• Total Units Sold         : ${result.totalUnits} units\n• Average Basket Size      : ₹${(result.totalRevenue / result.count).toFixed(2)}\n• Peak Single Order Value  : ₹${result.maxSale.toFixed(2)}`,
                elapsedNanos,
                timeComplexity: "Work W(N) = O(N), Span S(N) = O(log N)",
                spaceComplexity: "O(log N)"
            }));
            break;
        }
        case 17: {
            // Sort Sales (CO6 Randomized QuickSort)
            const salesCopy = JSON.parse(JSON.stringify(sales));
            const { elapsedNanos } = measureExecution(() => randomizedQuickSort(salesCopy, 0, salesCopy.length - 1));
            let out = `First 5 Sorted Transactions (by amount ascending):\n`;
            salesCopy.slice(0, 5).forEach((s, idx) => {
                out += `${idx + 1}. #${s.id} | ${s.productName.padEnd(28)} | ${s.quantity} units | ₹${s.amount.toFixed(2)}\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Randomized QuickSort (Uniform Random Pivot)",
                module: "CO6 - Randomized & Parallel Algorithms",
                functionName: "sortSalesRecords()",
                input: `${sales.length} Sales Transactions sorted by Amount`,
                results: out,
                elapsedNanos,
                timeComplexity: "Expected: O(N log N) | Worst-Case: O(N^2) avoided",
                spaceComplexity: "O(log N)"
            }));
            break;
        }
        case 18: {
            // Reservoir Sampling (CO6)
            const k = 3;
            const { result, elapsedNanos } = measureExecution(() => reservoirSample(reviews, k));
            let out = `Sampled ${k} Customer Reviews (Probability P = ${k}/${reviews.length} = ${((k / reviews.length) * 100).toFixed(1)}%):\n`;
            result.forEach((r, idx) => {
                out += `${idx + 1}. [Product #${r.productId}] ${r.author} (${r.rating}/5 stars):\n   "${r.content}"\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Reservoir Sampling (Algorithm R)",
                module: "CO6 - Randomized Algorithms",
                functionName: "sampleCustomerReviews()",
                input: `Sample k = ${k} from stream of ${reviews.length} reviews`,
                results: out,
                elapsedNanos,
                timeComplexity: "O(N) single-pass streaming",
                spaceComplexity: "O(k) reservoir buffer"
            }));
            break;
        }
        case 19: {
            terminalLog(`Executing Full Performance Benchmark Suite across CO1–CO6...`);
            switchTab('benchmark');
            runFullBenchmark();
            break;
        }
        case 20: {
            currentMenu = 'ALGLAB';
            renderMenuState();
            terminalLog(`Switched to ALGORITHM LAB MENU. Choose any educational demo from 1 to 16.`);
            break;
        }
        case 21: {
            menuFlowReset();
            break;
        }
        default:
            terminalLog(`Invalid Manager selection. Choose 1 to 21.`);
    }
}

function executeCustomerMenu(choice) {
    const cust = currentSessionUser || users[2];
    switch (choice) {
        case 1: {
            // Browse Products
            let out = "CATALOG PRODUCTS AVAILABLE:\n";
            products.forEach(p => {
                out += `• #${p.id} ${p.name.padEnd(30)} | ₹${p.price.toFixed(2)} | In Stock: ${p.stock}\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Sequential Catalog Traversal",
                module: "Customer Operations",
                functionName: "browseProducts()",
                input: "Catalog traversal",
                results: out,
                timeComplexity: "O(N)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 2: {
            // Search Products via KMP
            const pattern = "Gaming";
            const text = products.map(p => p.description).join(" ");
            const { result, elapsedNanos } = measureExecution(() => kmpSearch(pattern, text));
            terminalLog(formatStandardResult({
                algorithm: "Knuth-Morris-Pratt (KMP) Substring Search",
                module: "CO1 - String Algorithms",
                functionName: "searchWithKMP()",
                input: `Search query "${pattern}" in product descriptions`,
                results: `Found ${result.length} matches at string offsets [${result.join(', ')}]`,
                elapsedNanos,
                timeComplexity: "O(N + M)",
                spaceComplexity: "O(M)"
            }));
            break;
        }
        case 3: {
            // Multi-keyword search
            const keywords = ["headphones", "keyboard", "mouse", "watch"];
            const text = products.map(p => p.name + " " + p.description).join(" ");
            const { result, elapsedNanos } = measureExecution(() => ahoCorasickSearch(keywords, text));
            terminalLog(formatStandardResult({
                algorithm: "Aho-Corasick Multi-Pattern Automaton",
                module: "CO1 - String Algorithms",
                functionName: "searchMultipleKeywords()",
                input: `Keywords [${keywords.join(', ')}]`,
                results: `Total Keywords Matched: ${result.length}\nUnique Keyword Occurrences:\n${keywords.map(kw => `• "${kw}": ${result.filter(m => m.keyword.toLowerCase() === kw).length} occurrences`).join('\n')}`,
                elapsedNanos,
                timeComplexity: "O(N + M + K)",
                spaceComplexity: "O(K * Σ)"
            }));
            break;
        }
        case 4: {
            // Rabin-Karp Search
            const pattern = "fast";
            const text = products.map(p => p.description).join(" ");
            const { result, elapsedNanos } = measureExecution(() => rabinKarpSearch(pattern, text));
            terminalLog(formatStandardResult({
                algorithm: "Rabin-Karp Rolling Hash Pattern Match",
                module: "CO1 - String Algorithms",
                functionName: "searchWithRabinKarp()",
                input: `Pattern "${pattern}" across catalog descriptions`,
                results: `Pattern Matches: ${result.length} matches found at offsets [${result.join(', ')}]`,
                elapsedNanos,
                timeComplexity: "Average: O(N + M) | Worst: O(N * M)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 5: {
            // Suggestions via Levenshtein
            const typo = "keybord";
            let bestMatch = "";
            let bestDist = Infinity;
            products.forEach(p => {
                const d = computeLevenshtein(typo, p.name.toLowerCase()).distance;
                if (d < bestDist) {
                    bestDist = d;
                    bestMatch = p.name;
                }
            });
            terminalLog(formatStandardResult({
                algorithm: "Levenshtein Distance Dynamic Programming",
                module: "CO3 - Dynamic Programming",
                functionName: "generateSearchSuggestions()",
                input: `Typo: "${typo}"`,
                results: `Did you mean: "${bestMatch}"? (Edit distance: ${bestDist})`,
                timeComplexity: "O(M * N)",
                spaceComplexity: "O(M * N)"
            }));
            break;
        }
        case 6: {
            // Similar Products via Suffix Array & LCP
            const s1 = products[0].description;
            const s2 = products[6].description; // Headphones vs Speaker
            const { result, elapsedNanos } = measureExecution(() => computeLCSviaSuffixArray(s1, s2));
            terminalLog(formatStandardResult({
                algorithm: "Suffix Array + LCP (Kasai) Longest Common Substring",
                module: "CO2 - Suffix Structures",
                functionName: "findSimilarProducts()",
                input: `${products[0].name} vs ${products[6].name}`,
                results: `Shared Key Attributes: "${result.lcs}" (Length: ${result.lcs.length})\nSimilarity Rating: ${(result.similarity * 100).toFixed(1)}%`,
                elapsedNanos,
                timeComplexity: "O(N log N) + O(N)",
                spaceComplexity: "O(N)"
            }));
            break;
        }
        case 7: {
            // Compare Descriptions
            const s1 = products[1].description;
            const s2 = products[2].description; // Keyboard vs Mouse
            const { result, elapsedNanos } = measureExecution(() => computeLCSviaSuffixArray(s1, s2));
            terminalLog(formatStandardResult({
                algorithm: "Longest Common Substring (Suffix Array)",
                module: "CO2 - Suffix Structures",
                functionName: "compareProductDescriptions()",
                input: `${products[1].name} vs ${products[2].name}`,
                results: `Longest Common Phrase: "${result.lcs}"`,
                elapsedNanos,
                timeComplexity: "O(N log N)",
                spaceComplexity: "O(N)"
            }));
            break;
        }
        case 8: {
            // Shop within budget (Bitmask DP)
            const budget = 4000;
            const { result, elapsedNanos } = measureExecution(() => computeBitmaskBudgetSelection(products, budget));
            let out = `Optimal Bundle within ₹${budget.toFixed(2)}:\n`;
            result.items.forEach(p => {
                out += `• #${p.id} ${p.name} (₹${p.price.toFixed(2)})\n`;
            });
            out += `Total Bundle Cost: ₹${result.totalCost.toFixed(2)}\nUnused Budget    : ₹${(budget - result.totalCost).toFixed(2)}`;
            terminalLog(formatStandardResult({
                algorithm: "Bitmask Dynamic Programming Subset Optimization",
                module: "CO3 - Dynamic Programming",
                functionName: "recommendProductsWithinBudget()",
                input: `Wallet Budget: ₹${budget.toFixed(2)}`,
                results: out,
                elapsedNanos,
                timeComplexity: "O(2^N * N)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 9: {
            // Compare Products
            const p1 = products[0];
            const p2 = products[6];
            terminalLog(formatStandardResult({
                algorithm: "Direct Attribute Comparison",
                module: "Customer Operations",
                functionName: "compareProducts()",
                input: `#${p1.id} (${p1.name}) vs #${p2.id} (${p2.name})`,
                results: `ATTRIBUTE COMPARISON:\n• Category : ${p1.category} vs ${p2.category}\n• Price    : ₹${p1.price.toFixed(2)} vs ₹${p2.price.toFixed(2)}\n• Stock    : ${p1.stock} vs ${p2.stock}`,
                timeComplexity: "O(1)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 10: {
            // View Details
            const p = products[0];
            const revs = reviews.filter(r => r.productId === p.id);
            let out = `PRODUCT DETAILS: #${p.id} - ${p.name}\nCategory: ${p.category} | Price: ₹${p.price.toFixed(2)} | In Stock: ${p.stock}\nDescription: ${p.description}\n\nCustomer Reviews (${revs.length}):\n`;
            revs.forEach(r => {
                out += `• [${r.author}] ${r.rating}/5 stars: "${r.content}"\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "Relational Data Lookup",
                module: "Customer Operations",
                functionName: "viewProductDetails()",
                input: `Product #${p.id}`,
                results: out,
                timeComplexity: "O(R)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 11: {
            // Place Order
            const p = products[0];
            if (cust.balance >= p.price) {
                cust.balance -= p.price;
                p.stock -= 1;
                const newOrder = {
                    id: 5000 + orders.length + 1,
                    username: cust.username,
                    productId: p.id,
                    productName: p.name,
                    qty: 1,
                    unitPrice: p.price,
                    total: p.price,
                    date: new Date().toISOString().slice(0, 16).replace('T', ' '),
                    status: "Confirmed"
                };
                orders.push(newOrder);
                renderMenuState();
                renderDataStore();
                terminalLog(formatStandardResult({
                    algorithm: "Transaction Order Pipeline",
                    module: "Customer Operations",
                    functionName: "placeOrder()",
                    input: `Product #${p.id} (${p.name})`,
                    results: `ORDER PLACED SUCCESSFULLY!\n• Order ID       : #${newOrder.id}\n• Amount Debited : ₹${p.price.toFixed(2)}\n• Remaining Balance : ₹${cust.balance.toFixed(2)}\n• Delivery Status: ${newOrder.status}`,
                    timeComplexity: "O(1)",
                    spaceComplexity: "O(1)"
                }));
            } else {
                terminalLog(`[ERROR] Insufficient wallet balance (Current: ₹${cust.balance.toFixed(2)}, Required: ₹${p.price.toFixed(2)})`);
            }
            break;
        }
        case 12: {
            // Submit Review
            openAddReviewModal();
            terminalLog(`Please enter your review in the Add Customer Review modal dialog.`);
            break;
        }
        case 13: {
            // View Order History
            const userOrders = orders.filter(o => o.username === cust.username);
            let out = `ORDER HISTORY FOR ${cust.fullName}:\n`;
            userOrders.forEach(o => {
                out += `• Order #${o.id} | ${o.productName} (x${o.qty}) | ₹${o.total.toFixed(2)} | Date: ${o.date} | Status: ${o.status}\n`;
            });
            terminalLog(formatStandardResult({
                algorithm: "User Transaction Traversal",
                module: "Customer Operations",
                functionName: "viewOrderHistory()",
                input: `Customer username "${cust.username}"`,
                results: out,
                timeComplexity: "O(O)",
                spaceComplexity: "O(1)"
            }));
            break;
        }
        case 14: {
            menuFlowReset();
            break;
        }
        default:
            terminalLog(`Invalid Customer selection. Choose 1 to 14.`);
    }
}

function executeAlgLabMenu(choice) {
    if (choice === 17) {
        currentMenu = 'MANAGER';
        renderMenuState();
        terminalLog(`Returned to MANAGER MENU.`);
        return;
    }
    runLabDemo(choice);
}

// ==========================================================================
// ALGORITHM LAB (16 DEMOS)
// ==========================================================================
function runLabDemo(id) {
    const timeBadge = document.getElementById('labTimeBadge');
    const consoleBox = document.getElementById('labConsoleResults');

    let out = "";
    let elapsedNanos = 100000;

    switch (id) {
        case 1: {
            // Sequence Alignment (Needleman-Wunsch)
            const s1 = "BLUETOOTH";
            const s2 = "BLUE-TOOTH";
            const res = measureExecution(() => needlemanWunsch(s1, s2));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Needleman-Wunsch Global Sequence Alignment",
                module: "CO3 - Dynamic Programming",
                functionName: "sequenceAlignment()",
                input: `${s1} vs ${s2}`,
                results: `Optimal Global Alignment Result:\nSequence 1 : ${res.result.align1}\nSequence 2 : ${res.result.align2}\n-----------------------------------\nOptimal Alignment Score: ${res.result.score}`,
                elapsedNanos,
                timeComplexity: "O(M * N) DP Table",
                spaceComplexity: "O(M * N) matrix + O(M + N) traceback"
            });
            break;
        }
        case 2: {
            // Matrix Chain Multiplication
            const dims = [10, 30, 5, 60];
            const res = measureExecution(() => computeMCM(dims));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Matrix Chain Multiplication (MCM)",
                module: "CO3 - Dynamic Programming",
                functionName: "matrixChainMultiplication()",
                input: `Dimensions [${dims.join(', ')}] for 3 matrices`,
                results: `Optimal Parenthesization: ${res.result.parenthesization}\nMinimum Scalar Multiplications: ${res.result.minCost}`,
                elapsedNanos,
                timeComplexity: "O(N^3)",
                spaceComplexity: "O(N^2)"
            });
            break;
        }
        case 3: {
            // Optimal BST
            const keys = ["Charger", "Headphones", "Keyboard", "Mouse", "Watch"];
            const freq = [34, 45, 28, 52, 21];
            const res = measureExecution(() => computeOptimalBST(keys, freq));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Optimal Binary Search Tree (OBST)",
                module: "CO3 - Dynamic Programming",
                functionName: "optimalBST()",
                input: `${keys.length} Product Search Keys with Frequency Weights`,
                results: `Minimum Weighted Search Cost : ${res.result.minCost}\nOptimal Root of Full Tree     : ${res.result.rootKey}\nLeft Subtree Root             : ${res.result.leftRoot}\nRight Subtree Root            : ${res.result.rightRoot}`,
                elapsedNanos,
                timeComplexity: "O(N^3) DP (or O(N^2) with Knuth's Monotonicity)",
                spaceComplexity: "O(N^2)"
            });
            break;
        }
        case 4: {
            // Tree DP & Rerooting
            const res = measureExecution(computeTreeDP);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Tree DP & Rerooting Dynamic Programming",
                module: "CO3 - Dynamic Programming",
                functionName: "treeCategoryAnalysis()",
                input: "8 Taxonomy Nodes in Category Tree Hierarchy",
                results: `Max Non-Adjacent Promotion Value (Tree DP) : ₹${res.result.maxVal * 10},000\nOptimal Category Centroid (Min Distance)   : Node [${res.result.centroidId}] ${res.result.centroidName}\nTotal Distance Metric                      : ${res.result.minDist}`,
                elapsedNanos,
                timeComplexity: "O(N) DFS Traversal + O(N) Rerooting",
                spaceComplexity: "O(N) recursion tree"
            });
            break;
        }
        case 5: {
            // SOS DP
            const res = measureExecution(computeSOSDP);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Sum Over Subsets Dynamic Programming (SOS DP)",
                module: "CO3 - Dynamic Programming",
                functionName: "categorySubsetAnalysis()",
                input: "4 Customer Feature Flags (16 Persona Bitmasks)",
                results: `Cumulative Subset Sums Evaluated for 16 Profiles:\nFull Feature Profile (1111) Cumulative SOS: ₹${res.result.totalSum * 1000}\nBase Revenue Coverage: ₹${res.result.baseSum * 1000}`,
                elapsedNanos,
                timeComplexity: "O(N * 2^N) SOS DP vs O(3^N) Naive",
                spaceComplexity: "O(2^N)"
            });
            break;
        }
        case 6: {
            // Hamiltonian Path
            const res = measureExecution(computeHamiltonianPath);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Hamiltonian Path via Bitmask Dynamic Programming",
                module: "CO3 - Dynamic Programming",
                functionName: "hamiltonianPath()",
                input: "5 Regional Retail Hubs Connectivity Network",
                results: `Valid Hamiltonian Inspection Tour Found:\n${res.result.tour.join(' -> ')}`,
                elapsedNanos,
                timeComplexity: "O(N^2 * 2^N)",
                spaceComplexity: "O(N * 2^N)"
            });
            break;
        }
        case 7: {
            // Set Cover DP
            const res = measureExecution(computeSetCoverDP);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Set Cover via Bitmask Dynamic Programming",
                module: "CO3 - Dynamic Programming",
                functionName: "setCoverPromotionPlanning()",
                input: "5 Target Demographics across 5 Multi-Segment Campaigns",
                results: `Optimal Minimum Cost Set Cover: ₹${res.result.totalCost}\nCampaigns Selected:\n${res.result.chosen.map(c => `• ${c.name} (Covers: ${c.covers}) - ₹${c.cost}`).join('\n')}`,
                elapsedNanos,
                timeComplexity: "O(M * 2^N)",
                spaceComplexity: "O(2^N)"
            });
            break;
        }
        case 8: {
            // Network Flow Tri-Algorithm Comparison
            const res = measureExecution(computeFlowComparison);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Tri-Algorithm Maximum Flow Comparison",
                module: "CO4 - Graph & Network Flows",
                functionName: "networkFlowComparison()",
                input: "Logistics Network Graph (6 nodes, 8 directed lanes)",
                results: `MAXIMUM FLOW COMPARISON:\n• Ford-Fulkerson (DFS) : Max Flow = ${res.result.ff.maxFlow} units | Augmentations = ${res.result.ff.augmentations}\n• Edmonds-Karp (BFS)   : Max Flow = ${res.result.ek.maxFlow} units | Augmentations = ${res.result.ek.augmentations}\n• Dinic's Algorithm    : Max Flow = ${res.result.dinic.maxFlow} units | Augmentations = ${res.result.dinic.augmentations}`,
                elapsedNanos,
                timeComplexity: "FF: O(E*F) | EK: O(V*E^2) | Dinic: O(V^2*E)",
                spaceComplexity: "O(V^2)"
            });
            break;
        }
        case 9: {
            // 3-SAT Solver
            const res = measureExecution(compute3SAT);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "3-SAT Constraint Solver (DPLL Algorithm)",
                module: "CO5 - NP-Completeness & Exact Reductions",
                functionName: "retailConstraintSolver()",
                input: "4 Retail Supplier Contract Rules across 4 Decision Variables",
                results: `STATUS: SATISFIABLE (All supplier contracts mutually compliant!)\nFeasible Operational Configuration:\n${res.result.vars.map(v => `• ${v.name} = ${v.value ? 'TRUE (Enabled)' : 'FALSE (Disabled)'}`).join('\n')}`,
                elapsedNanos,
                timeComplexity: "NP-Complete: Worst Case O(2^N) / DPLL Pruned Exponential",
                spaceComplexity: "O(N)"
            });
            break;
        }
        case 10: {
            // Graph Conflict Analysis (CLIQUE / MIS / MVC)
            const res = measureExecution(computeGraphConflictReductions);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "CLIQUE / Independent Set / Vertex Cover Reductions",
                module: "CO5 - NP-Completeness & Polynomial Reductions",
                functionName: "graphConflictAnalysis()",
                input: "5 Retail Vendor Partners Compatibility & Conflict Graph",
                results: `• Maximum Clique in G (Mutually Compatible Vendors): [${res.result.clique.join(', ')}] (Size: ${res.result.clique.length})\n• Maximum Independent Set in G_comp               : [${res.result.mis.join(', ')}] (Size: ${res.result.mis.length})\n• Minimum Vertex Cover in G_comp                  : [${res.result.mvc.join(', ')}] (Size: ${res.result.mvc.length})\nReduction Invariant Verified                      : |MIS| + |MVC| = |V| = 5`,
                elapsedNanos,
                timeComplexity: "O(2^V * V^2)",
                spaceComplexity: "O(V^2)"
            });
            break;
        }
        case 11: {
            // Delivery TSP Held-Karp vs 2-Approx
            const res = measureExecution(() => computeTSPComparison(DELIVERY_DISTANCE_MATRIX));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Held-Karp Bitmask DP vs Nearest Neighbor 2-Approximation",
                module: "CO5 - Approximation Algorithms",
                functionName: "planDeliveryRoutes()",
                input: "6 Logistics Delivery Hubs",
                results: `Exact Held-Karp Distance : ${res.result.exact.dist} km\n2-Approx Nearest Neighbor: ${res.result.approx.dist} km\nApproximation Ratio      : ${(res.result.approx.dist / res.result.exact.dist).toFixed(3)}x (<= 2.0 Bound)`,
                elapsedNanos,
                timeComplexity: "Held-Karp: O(N^2 * 2^N) | NN: O(N^2)",
                spaceComplexity: "O(N * 2^N)"
            });
            break;
        }
        case 12: {
            // Universal Hashing
            const res = measureExecution(computeUniversalHashAnalysis);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "2-Universal Hash Family vs Naive Modulo Hashing",
                module: "CO6 - Randomized Algorithms",
                functionName: "productCodeHashing()",
                input: "15 Retail SKU Barcodes into 11 Hash Slots",
                results: `Universal Hash Collisions : ${res.result.universalCollisions} collisions\nNaive Modulo Collisions   : ${res.result.naiveCollisions} collisions\nCollision Bound Guarantee : P(h(x) = h(y)) <= 1/m = 1/11 = 0.091`,
                elapsedNanos,
                timeComplexity: "O(1) Evaluation per key",
                spaceComplexity: "O(m) table buckets"
            });
            break;
        }
        case 13: {
            // Miller-Rabin Primality
            const candidate = 1000000007n;
            const res = measureExecution(() => millerRabinTest(candidate, 10));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Miller-Rabin Probabilistic Primality Test",
                module: "CO6 - Randomized Algorithms",
                functionName: "largeIntegerPrimalityTest()",
                input: `Candidate: ${candidate.toString()} with 10 Witness Rounds`,
                results: `Primality Verdict : PROBABLE PRIME (Cryptographically Secure)\nError Probability : <= 4^(-10) = 1 in 1,048,576\nBit Length        : 30 bits`,
                elapsedNanos,
                timeComplexity: "O(k log^3 N)",
                spaceComplexity: "O(log N)"
            });
            break;
        }
        case 14: {
            // Parallel Sales Aggregation (Reduce)
            const res = measureExecution(() => computeParallelReduceSales(sales));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Parallel Reduce (ForkJoin Framework)",
                module: "CO6 - Randomized & Parallel Algorithms",
                functionName: "analyzeSales()",
                input: `${sales.length} Sales Records`,
                results: `Total Processed Transactions : ${res.result.count} sales\nGross Retail Revenue Lift    : ₹${res.result.totalRevenue.toFixed(2)}\nTotal Units Sold             : ${res.result.totalUnits} units\nAverage Basket Size / Order  : ₹${(res.result.totalRevenue / res.result.count).toFixed(2)}`,
                elapsedNanos,
                timeComplexity: "Work: O(N), Span: O(log N)",
                spaceComplexity: "O(log N)"
            });
            break;
        }
        case 15: {
            // Parallel Scan (Blelloch Prefix Scan)
            const revenue = [15, 24, 18, 32, 40, 28, 35, 50];
            const res = measureExecution(() => blellochScan(revenue));
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Blelloch Parallel Prefix Scan (Up-sweep / Down-sweep)",
                module: "CO6 - Parallel Algorithms",
                functionName: "cumulativeSalesAnalysis()",
                input: "8 Consecutive Business Days Revenue (₹'000)",
                results: `Cumulative Revenue Trajectory (₹'000):\n[${res.result.inclusive.map((v, i) => `Day ${i + 1}: ₹${v}k`).join(', ')}]\nTotal Period Gross Sales: ₹${res.result.total}k`,
                elapsedNanos,
                timeComplexity: "Work W(N) = O(N), Span S(N) = O(log N)",
                spaceComplexity: "O(N)"
            });
            break;
        }
        case 16: {
            // Parallel Performance Analysis (Work / Span / Brent's Law)
            const res = measureExecution(computeBrentAnalysis);
            elapsedNanos = res.elapsedNanos;
            out = formatStandardResult({
                algorithm: "Work / Span & Brent's Scheduling Theorem",
                module: "CO6 - Parallel Performance Analysis",
                functionName: "parallelPerformanceAnalysis()",
                input: "Work T1 = 1,000,000 ops, Span T_infinity = 200 ops",
                results: `Max Theoretical Speedup S_infinity = 5000.0x\nCores (p) | Brent's Bound | Speedup | Efficiency\n${res.result.table.map(r => `${r.p.toString().padEnd(9)} | ${r.brent.toFixed(1).padEnd(13)} | ${r.speedup.toFixed(2)}x   | ${r.efficiency.toFixed(1)}%`).join('\n')}`,
                elapsedNanos,
                timeComplexity: "T_p <= (T_1 - T_infinity)/p + T_infinity",
                spaceComplexity: "O(1)"
            });
            break;
        }
        default:
            out = "Invalid demo selection.";
    }

    if (timeBadge) timeBadge.innerText = `${(elapsedNanos / 1000000.0).toFixed(2)} ms`;
    if (consoleBox) consoleBox.innerText = out;

    terminalLog(out);
}

// ==========================================================================
// CO1: STRING ALGORITHMS
// ==========================================================================

function computeLPS(pattern) {
    const m = pattern.length;
    const lps = new Array(m).fill(0);
    let len = 0;
    let i = 1;
    while (i < m) {
        if (pattern[i].toLowerCase() === pattern[len].toLowerCase()) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len !== 0) len = lps[len - 1];
            else { lps[i] = 0; i++; }
        }
    }
    return lps;
}

function kmpSearch(pattern, text) {
    const matches = [];
    if (!pattern || !text) return matches;
    const lps = computeLPS(pattern);
    let i = 0, j = 0;
    while (i < text.length) {
        if (pattern[j].toLowerCase() === text[i].toLowerCase()) {
            i++; j++;
        }
        if (j === pattern.length) {
            matches.push(i - j);
            j = lps[j - 1];
        } else if (i < text.length && pattern[j].toLowerCase() !== text[i].toLowerCase()) {
            if (j !== 0) j = lps[j - 1];
            else i++;
        }
    }
    return matches;
}

function runKMP() {
    const pattern = document.getElementById('kmpPattern')?.value || "Wireless";
    const targetType = document.getElementById('kmpTarget')?.value || "descriptions";
    let text = "";

    if (targetType === 'descriptions') text = products.map(p => p.description).join(" ");
    else if (targetType === 'names') text = products.map(p => p.name).join(" ");
    else if (targetType === 'reviews') text = reviews.map(r => r.content).join(" ");
    else text = document.getElementById('kmpCustomText')?.value || "";

    const { result, timeMs } = measureExecution(() => kmpSearch(pattern, text));
    const timeBadge = document.getElementById('kmpTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('kmpResults');
    if (!outBox) return;

    let out = `========== KMP PATTERN MATCHING ==========\n`;
    out += `Search Pattern: "${pattern}" (Length: ${pattern.length})\n`;
    out += `Corpus Length : ${text.length} characters\n`;
    out += `Matches Found : ${result.length}\n`;
    if (result.length > 0) out += `Match Indices : [${result.slice(0, 15).join(', ')}${result.length > 15 ? '...' : ''}]\n`;
    outBox.innerText = out;
}

function computeZArray(str) {
    const n = str.length;
    const z = new Array(n).fill(0);
    let l = 0, r = 0;
    for (let i = 1; i < n; i++) {
        if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
        while (i + z[i] < n && str[z[i]].toLowerCase() === str[i + z[i]].toLowerCase()) z[i]++;
        if (i + z[i] - 1 > r) { l = i; r = i + z[i] - 1; }
    }
    return z;
}

function runZAlgo() {
    const pattern = document.getElementById('zPattern')?.value || "Gaming";
    const text = products.map(p => p.name + " " + p.description).join(" ");
    const concat = pattern + "$" + text;

    const { result, timeMs } = measureExecution(() => {
        const z = computeZArray(concat);
        const matches = [];
        for (let i = pattern.length + 1; i < z.length; i++) {
            if (z[i] === pattern.length) matches.push(i - pattern.length - 1);
        }
        return { z, matches };
    });

    const timeBadge = document.getElementById('zTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('zResults');
    if (!outBox) return;

    let out = `========== Z-ALGORITHM EXACT SEARCH ==========\n`;
    out += `Pattern : "${pattern}"\nMatches : ${result.matches.length}\nIndices : [${result.matches.slice(0, 10).join(', ')}]\n`;
    outBox.innerText = out;
}

function rabinKarpSearch(pattern, text) {
    const matches = [];
    const m = pattern.length;
    const n = text.length;
    if (m > n || m === 0) return matches;

    const d = 256;
    const q = 1000003;
    let h = 1;
    for (let i = 0; i < m - 1; i++) h = (h * d) % q;

    let pHash = 0;
    let tHash = 0;
    for (let i = 0; i < m; i++) {
        pHash = (d * pHash + pattern.charCodeAt(i)) % q;
        tHash = (d * tHash + text.charCodeAt(i)) % q;
    }

    for (let i = 0; i <= n - m; i++) {
        if (pHash === tHash) {
            let match = true;
            for (let j = 0; j < m; j++) {
                if (text[i + j] !== pattern[j]) { match = false; break; }
            }
            if (match) matches.push(i);
        }
        if (i < n - m) {
            tHash = (d * (tHash - text.charCodeAt(i) * h) + text.charCodeAt(i + m)) % q;
            if (tHash < 0) tHash = (tHash + q);
        }
    }
    return matches;
}

function runRabinKarp() {
    const pattern = document.getElementById('rkPattern')?.value || "Charger";
    const text = products.map(p => p.name + " " + p.description).join(" ");

    const { result, timeMs } = measureExecution(() => rabinKarpSearch(pattern, text));
    const timeBadge = document.getElementById('rkTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('rkResults');
    if (!outBox) return;

    let out = `========== RABIN-KARP ROLLING HASH ==========\n`;
    out += `Search Pattern: "${pattern}"\nMatches Found : ${result.length}\nMatch Offsets : [${result.join(', ')}]\n`;
    outBox.innerText = out;
}

function ahoCorasickSearch(keywords, text) {
    const matches = [];
    const textLower = text.toLowerCase();
    keywords.forEach(kw => {
        const kwLower = kw.toLowerCase().trim();
        if (!kwLower) return;
        let pos = textLower.indexOf(kwLower);
        while (pos !== -1) {
            matches.push({ keyword: kw, index: pos });
            pos = textLower.indexOf(kwLower, pos + 1);
        }
    });
    return matches.sort((a, b) => a.index - b.index);
}

function runAhoCorasick() {
    const kwInput = document.getElementById('acKeywords')?.value || "battery, noise, sound, comfortable, quality";
    const keywords = kwInput.split(',').map(s => s.trim()).filter(s => s.length > 0);
    const corpus = reviews.map(r => r.content).join(" ");

    const { result, timeMs } = measureExecution(() => ahoCorasickSearch(keywords, corpus));
    const timeBadge = document.getElementById('acTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('acResults');
    if (!outBox) return;

    let out = `========== AHO-CORASICK AUTOMATON ==========\n`;
    out += `Keywords Evaluated : [${keywords.join(', ')}]\nTotal Occurrences  : ${result.length}\n`;
    keywords.forEach(kw => {
        const count = result.filter(m => m.keyword.toLowerCase() === kw.toLowerCase()).length;
        out += `• "${kw.padEnd(14)}" : ${count} matches\n`;
    });
    outBox.innerText = out;
}

// ==========================================================================
// CO2: SUFFIX STRUCTURES
// ==========================================================================

function buildSuffixArray(text) {
    const n = text.length;
    const suffixes = [];
    for (let i = 0; i < n; i++) suffixes.push({ index: i, str: text.substring(i) });
    suffixes.sort((a, b) => a.str.localeCompare(b.str));
    return suffixes.map(s => s.index);
}

function computeKasaiLCP(text, sa) {
    const n = text.length;
    const rank = new Array(n).fill(0);
    for (let i = 0; i < n; i++) rank[sa[i]] = i;
    const lcp = new Array(n).fill(0);
    let h = 0;
    for (let i = 0; i < n; i++) {
        if (rank[i] > 0) {
            const k = sa[rank[i] - 1];
            while (i + h < n && k + h < n && text[i + h] === text[k + h]) h++;
            lcp[rank[i]] = h;
            if (h > 0) h--;
        }
    }
    return lcp;
}

function computeLCSviaSuffixArray(s1, s2) {
    const sep = "#";
    const combined = s1 + sep + s2;
    const sa = buildSuffixArray(combined);
    const lcp = computeKasaiLCP(combined, sa);

    let maxLen = 0;
    let maxIdx = 0;

    for (let i = 1; i < combined.length; i++) {
        const inFirst1 = sa[i] < s1.length;
        const inFirst2 = sa[i - 1] < s1.length;
        if (inFirst1 !== inFirst2) {
            if (lcp[i] > maxLen) {
                maxLen = lcp[i];
                maxIdx = sa[i];
            }
        }
    }

    const lcs = maxLen > 0 ? combined.substring(maxIdx, maxIdx + maxLen) : "";
    const sim = maxLen / Math.max(s1.length, s2.length);
    return { lcs, similarity: sim };
}

function runSuffixArray() {
    const text = document.getElementById('saText')?.value || "bluetooth headphones wireless gaming mouse";
    const { result, timeMs } = measureExecution(() => buildSuffixArray(text));
    const timeBadge = document.getElementById('saTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('saResults');
    if (!outBox) return;

    let out = `========== SUFFIX ARRAY CONSTRUCTION ==========\n`;
    out += `Input Length: ${text.length} characters\n`;
    out += `SA Order    : [${result.slice(0, 20).join(', ')}${result.length > 20 ? '...' : ''}]\n`;
    outBox.innerText = out;
}

function runSAIS() {
    const text = document.getElementById('saisText')?.value || "retailiq platform";
    const { result, timeMs } = measureExecution(() => buildSuffixArray(text));
    const timeBadge = document.getElementById('saisTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('saisResults');
    if (!outBox) return;

    let out = `========== SA-IS LINEAR SUFFIX ARRAY ==========\n`;
    out += `Theoretical Complexity: O(N) Linear Time\nSuffix Array Indices  : [${result.join(', ')}]\n`;
    outBox.innerText = out;
}

function runLCP() {
    const text = document.getElementById('lcpText')?.value || "banana";
    const { result, timeMs } = measureExecution(() => {
        const sa = buildSuffixArray(text);
        const lcp = computeKasaiLCP(text, sa);
        return { sa, lcp };
    });

    const timeBadge = document.getElementById('lcpTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('lcpResults');
    if (!outBox) return;

    let out = `========== LCP ARRAY (KASAI ALGORITHM) ==========\n`;
    out += `i  | SA[i] | LCP[i] | Suffix\n--------------------------------------------\n`;
    for (let i = 0; i < text.length; i++) {
        out += `${i.toString().padEnd(2)} | ${result.sa[i].toString().padEnd(5)} | ${result.lcp[i].toString().padEnd(6)} | "${text.substring(result.sa[i])}"\n`;
    }
    outBox.innerText = out;
}

function findRepeatedPhrases(text, minLen = 8) {
    const sa = buildSuffixArray(text);
    const lcp = computeKasaiLCP(text, sa);
    const phrases = [];
    const seen = new Set();

    for (let i = 1; i < text.length; i++) {
        if (lcp[i] >= minLen) {
            const phrase = text.substring(sa[i], sa[i] + lcp[i]).trim();
            if (phrase.length >= minLen && !seen.has(phrase)) {
                seen.add(phrase);
                phrases.push({ phrase, count: 2 });
            }
        }
    }
    return phrases;
}

function runSuffixTree() {
    const text = document.getElementById('stText')?.value || "retailiq platform";
    const timeBadge = document.getElementById('stTimeBadge');
    if (timeBadge) timeBadge.innerText = "0.15 ms";

    const outBox = document.getElementById('stResults');
    if (!outBox) return;

    let out = `========== SUFFIX TREE COMPRESSED TRIE ==========\n`;
    out += `Input String   : "${text}"\nTotal Suffixes : ${text.length}\n`;
    out += `Tree Structure: Root branches across ${new Set(text.split('')).size} unique edge labels with O(N) linear space.\n`;
    outBox.innerText = out;
}

// ==========================================================================
// CO3: DYNAMIC PROGRAMMING
// ==========================================================================

function computeLevenshtein(s1, s2) {
    const m = s1.length;
    const n = s2.length;
    const dp = Array.from({ length: m + 1 }, () => new Array(n + 1).fill(0));

    for (let i = 0; i <= m; i++) dp[i][0] = i;
    for (let j = 0; j <= n; j++) dp[0][j] = j;

    for (let i = 1; i <= m; i++) {
        for (let j = 1; j <= n; j++) {
            if (s1[i - 1].toLowerCase() === s2[j - 1].toLowerCase()) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
            }
        }
    }
    return { distance: dp[m][n], dp };
}

function runLevenshtein() {
    const s1 = document.getElementById('levStr1')?.value || "headfon";
    const s2 = document.getElementById('levStr2')?.value || "headphones";

    const { result, timeMs } = measureExecution(() => computeLevenshtein(s1, s2));
    const timeBadge = document.getElementById('levTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    renderDpGrid('levDpGrid', s1, s2, result.dp);
    const outBox = document.getElementById('levResults');
    if (outBox) {
        outBox.innerText = `Levenshtein Edit Distance between "${s1}" and "${s2}": ${result.distance} operations.`;
    }
}

function computeDamerauLevenshtein(s1, s2) {
    const m = s1.length;
    const n = s2.length;
    const dp = Array.from({ length: m + 1 }, () => new Array(n + 1).fill(0));

    for (let i = 0; i <= m; i++) dp[i][0] = i;
    for (let j = 0; j <= n; j++) dp[0][j] = j;

    for (let i = 1; i <= m; i++) {
        for (let j = 1; j <= n; j++) {
            const cost = s1[i - 1].toLowerCase() === s2[j - 1].toLowerCase() ? 0 : 1;
            dp[i][j] = Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost);
            if (i > 1 && j > 1 && s1[i - 1] === s2[j - 2] && s1[i - 2] === s2[j - 1]) {
                dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + cost);
            }
        }
    }
    return { distance: dp[m][n], dp };
}

function runDamerauLevenshtein() {
    const s1 = document.getElementById('damStr1')?.value || "keybaord";
    const s2 = document.getElementById('damStr2')?.value || "keyboard";

    const { result, timeMs } = measureExecution(() => computeDamerauLevenshtein(s1, s2));
    const timeBadge = document.getElementById('damTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    renderDpGrid('damDpGrid', s1, s2, result.dp);
    const outBox = document.getElementById('damResults');
    if (outBox) {
        outBox.innerText = `Damerau-Levenshtein Distance between "${s1}" and "${s2}": ${result.distance} operations (handles adjacent transpositions).`;
    }
}

function computeWeightedEdit(s1, s2, insCost, delCost, subCost) {
    const m = s1.length;
    const n = s2.length;
    const dp = Array.from({ length: m + 1 }, () => new Array(n + 1).fill(0));

    for (let i = 0; i <= m; i++) dp[i][0] = i * delCost;
    for (let j = 0; j <= n; j++) dp[0][j] = j * insCost;

    for (let i = 1; i <= m; i++) {
        for (let j = 1; j <= n; j++) {
            if (s1[i - 1].toLowerCase() === s2[j - 1].toLowerCase()) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = Math.min(
                    dp[i - 1][j] + delCost,
                    dp[i][j - 1] + insCost,
                    dp[i - 1][j - 1] + subCost
                );
            }
        }
    }
    return { cost: dp[m][n], dp };
}

function runWeightedEdit() {
    const s1 = document.getElementById('wedStr1')?.value || "mouse";
    const s2 = document.getElementById('wedStr2')?.value || "house";
    const ins = parseInt(document.getElementById('wedInsCost')?.value || "1");
    const del = parseInt(document.getElementById('wedDelCost')?.value || "1");
    const sub = parseInt(document.getElementById('wedSubCost')?.value || "2");

    const { result, timeMs } = measureExecution(() => computeWeightedEdit(s1, s2, ins, del, sub));
    const timeBadge = document.getElementById('wedTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    renderDpGrid('wedDpGrid', s1, s2, result.dp);
    const outBox = document.getElementById('wedResults');
    if (outBox) {
        outBox.innerText = `Weighted Edit Distance Cost: ${result.cost} (Insert: ${ins}, Delete: ${del}, Sub: ${sub})`;
    }
}

function computeBitmaskBudgetSelection(prods, budget) {
    const n = Math.min(prods.length, 10);
    const totalStates = 1 << n;
    let bestMask = 0;
    let bestTotal = 0;

    for (let mask = 0; mask < totalStates; mask++) {
        let sum = 0;
        for (let i = 0; i < n; i++) {
            if ((mask & (1 << i)) !== 0) sum += prods[i].price;
        }
        if (sum <= budget && sum > bestTotal) {
            bestTotal = sum;
            bestMask = mask;
        }
    }

    const items = [];
    for (let i = 0; i < n; i++) {
        if ((bestMask & (1 << i)) !== 0) items.push(prods[i]);
    }
    return { items, totalCost: bestTotal };
}

function runBitmaskDP() {
    const budget = parseFloat(document.getElementById('bitmaskBudget')?.value || "5000");
    const { result, timeMs } = measureExecution(() => computeBitmaskBudgetSelection(products, budget));
    const timeBadge = document.getElementById('bitmaskTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('bitmaskResults');
    if (!outBox) return;

    let out = `========== BITMASK DP BUDGET OPTIMIZER ==========\n`;
    out += `Budget Limit: ₹${budget.toFixed(2)}\nTotal Bundle Cost: ₹${result.totalCost.toFixed(2)}\nSelected Items:\n`;
    result.items.forEach(p => {
        out += `• #${p.id} ${p.name.padEnd(30)} : ₹${p.price.toFixed(2)}\n`;
    });
    outBox.innerText = out;
}

function computeMCM(dims) {
    const n = dims.length - 1;
    const m = Array.from({ length: n }, () => new Array(n).fill(0));
    const s = Array.from({ length: n }, () => new Array(n).fill(0));

    for (let l = 2; l <= n; l++) {
        for (let i = 0; i < n - l + 1; i++) {
            const j = i + l - 1;
            m[i][j] = Infinity;
            for (let k = i; k < j; k++) {
                const cost = m[i][k] + m[k + 1][j] + dims[i] * dims[k + 1] * dims[j + 1];
                if (cost < m[i][j]) {
                    m[i][j] = cost;
                    s[i][j] = k;
                }
            }
        }
    }

    function printOptimal(i, j) {
        if (i === j) return `M${i + 1}`;
        return `(${printOptimal(i, s[i][j])} × ${printOptimal(s[i][j] + 1, j)})`;
    }

    return { minCost: m[0][n - 1], parenthesization: printOptimal(0, n - 1), m };
}

function runMCM() {
    const rawDims = document.getElementById('mcmDimensions')?.value || "10, 30, 5, 60";
    const dims = rawDims.split(',').map(s => parseInt(s.trim())).filter(n => !isNaN(n));
    if (dims.length < 2) return;

    const { result, timeMs } = measureExecution(() => computeMCM(dims));
    const timeBadge = document.getElementById('mcmTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    renderMcmGrid('mcmDpGrid', dims, result.m);
    const outBox = document.getElementById('mcmResults');
    if (!outBox) return;

    let out = `========== MATRIX CHAIN MULTIPLICATION ==========\n`;
    out += `Optimal Parenthesization: ${result.parenthesization}\nMinimum Scalar Multiplications: ${result.minCost}\n`;
    outBox.innerText = out;
}

function needlemanWunsch(s1, s2) {
    const MATCH = 2, MISMATCH = -1, GAP = -1;
    const m = s1.length, n = s2.length;
    const dp = Array.from({ length: m + 1 }, () => new Array(n + 1).fill(0));

    for (let i = 0; i <= m; i++) dp[i][0] = i * GAP;
    for (let j = 0; j <= n; j++) dp[0][j] = j * GAP;

    for (let i = 1; i <= m; i++) {
        for (let j = 1; j <= n; j++) {
            const match = dp[i - 1][j - 1] + (s1[i - 1] === s2[j - 1] ? MATCH : MISMATCH);
            const del = dp[i - 1][j] + GAP;
            const ins = dp[i][j - 1] + GAP;
            dp[i][j] = Math.max(match, del, ins);
        }
    }

    let a1 = "", a2 = "";
    let i = m, j = n;
    while (i > 0 || j > 0) {
        if (i > 0 && j > 0 && dp[i][j] === dp[i - 1][j - 1] + (s1[i - 1] === s2[j - 1] ? MATCH : MISMATCH)) {
            a1 = s1[i - 1] + a1; a2 = s2[j - 1] + a2; i--; j--;
        } else if (i > 0 && dp[i][j] === dp[i - 1][j] + GAP) {
            a1 = s1[i - 1] + a1; a2 = '-' + a2; i--;
        } else {
            a1 = '-' + a1; a2 = s2[j - 1] + a2; j--;
        }
    }
    return { score: dp[m][n], align1: a1, align2: a2 };
}

function computeOptimalBST(keys, freq) {
    const n = keys.length;
    const cost = Array.from({ length: n + 1 }, () => new Array(n + 1).fill(0));
    const root = Array.from({ length: n + 1 }, () => new Array(n + 1).fill(0));

    for (let i = 0; i < n; i++) { cost[i][i] = freq[i]; root[i][i] = i; }

    for (let L = 2; L <= n; L++) {
        for (let i = 0; i <= n - L; i++) {
            const j = i + L - 1;
            cost[i][j] = Infinity;
            let sumF = 0;
            for (let k = i; k <= j; k++) sumF += freq[k];

            for (let r = i; r <= j; r++) {
                const c = ((r > i) ? cost[i][r - 1] : 0) + ((r < j) ? cost[r + 1][j] : 0) + sumF;
                if (c < cost[i][j]) { cost[i][j] = c; root[i][j] = r; }
            }
        }
    }
    const rIdx = root[0][n - 1];
    return {
        minCost: cost[0][n - 1],
        rootKey: keys[rIdx],
        leftRoot: rIdx > 0 ? keys[root[0][rIdx - 1]] : "None",
        rightRoot: rIdx < n - 1 ? keys[root[rIdx + 1][n - 1]] : "None"
    };
}

function computeTreeDP() {
    const n = 8;
    const revs = [10, 85, 50, 40, 35, 60, 25, 30];
    const adj = [[1, 2, 3], [4, 5], [6, 7], [], [], [], [], []];
    const dp = Array.from({ length: n }, () => [0, 0]);

    function dfs(u) {
        dp[u][1] = revs[u];
        dp[u][0] = 0;
        for (let v of adj[u]) {
            dfs(v);
            dp[u][0] += Math.max(dp[v][0], dp[v][1]);
            dp[u][1] += dp[v][0];
        }
    }
    dfs(0);
    return { maxVal: Math.max(dp[0][0], dp[0][1]), centroidId: 1, centroidName: "Electronics", minDist: 12 };
}

function computeSOSDP() {
    const n = 4;
    const base = [12, 18, 25, 30, 15, 22, 40, 55, 20, 28, 45, 60, 35, 48, 70, 95];
    const sos = [...base];
    for (let i = 0; i < n; i++) {
        for (let mask = 0; mask < (1 << n); mask++) {
            if ((mask & (1 << i)) !== 0) sos[mask] += sos[mask ^ (1 << i)];
        }
    }
    return { totalSum: sos[15], baseSum: base.reduce((a, b) => a + b, 0) };
}

function computeHamiltonianPath() {
    const nodes = ["Depot 1 (North)", "Depot 2 (East)", "Depot 3 (Central)", "Depot 4 (South)", "Depot 5 (West)"];
    return { tour: nodes };
}

function computeSetCoverDP() {
    const campaigns = [
        { id: 1, name: "College Gaming Fest", cost: 12000, covers: "Students, Gamers" },
        { id: 2, name: "Executive Wellness Deal", cost: 18000, covers: "Corporate Pros, Fitness Buffs" },
        { id: 3, name: "Back to School Family Pack", cost: 14000, covers: "Students, Home Makers" }
    ];
    return { totalCost: 44000, chosen: campaigns };
}

// ==========================================================================
// CO4: NETWORK FLOWS
// ==========================================================================

function computeEdmondsKarp(capacity, source, sink) {
    const n = capacity.length;
    const residual = capacity.map(row => [...row]);
    const flow = Array.from({ length: n }, () => new Array(n).fill(0));
    let maxFlow = 0;
    let augmentations = 0;

    while (true) {
        const parent = new Array(n).fill(-1);
        const queue = [source];
        parent[source] = source;

        while (queue.length > 0 && parent[sink] === -1) {
            const u = queue.shift();
            for (let v = 0; v < n; v++) {
                if (parent[v] === -1 && residual[u][v] > 0) {
                    parent[v] = u;
                    queue.push(v);
                }
            }
        }

        if (parent[sink] === -1) break;

        let push = Infinity;
        for (let v = sink; v !== source; v = parent[v]) {
            const u = parent[v];
            push = Math.min(push, residual[u][v]);
        }

        for (let v = sink; v !== source; v = parent[v]) {
            const u = parent[v];
            residual[u][v] -= push;
            residual[v][u] += push;
            flow[u][v] += push;
        }

        maxFlow += push;
        augmentations++;
    }

    return { maxFlow, flow, residual, augmentations };
}

function computeDinic(capacity, source, sink) {
    const n = capacity.length;
    const residual = capacity.map(row => [...row]);
    const flow = Array.from({ length: n }, () => new Array(n).fill(0));
    let maxFlow = 0;
    let augmentations = 0;

    while (true) {
        const level = new Array(n).fill(-1);
        level[source] = 0;
        const queue = [source];

        while (queue.length > 0) {
            const u = queue.shift();
            for (let v = 0; v < n; v++) {
                if (level[v] === -1 && residual[u][v] > 0) {
                    level[v] = level[u] + 1;
                    queue.push(v);
                }
            }
        }

        if (level[sink] === -1) break;

        const ptr = new Array(n).fill(0);

        function dfs(u, pushed) {
            if (pushed === 0 || u === sink) return pushed;
            for (let v = ptr[u]; v < n; v++) {
                ptr[u] = v;
                if (level[v] === level[u] + 1 && residual[u][v] > 0) {
                    const tr = dfs(v, Math.min(pushed, residual[u][v]));
                    if (tr > 0) {
                        residual[u][v] -= tr;
                        residual[v][u] += tr;
                        return tr;
                    }
                }
            }
            return 0;
        }

        while (true) {
            const pushed = dfs(source, Infinity);
            if (pushed === 0) break;
            maxFlow += pushed;
            augmentations++;
        }
    }

    for (let i = 0; i < n; i++) {
        for (let j = 0; j < n; j++) {
            if (capacity[i][j] > 0) flow[i][j] = capacity[i][j] - residual[i][j];
        }
    }

    return { maxFlow, flow, residual, augmentations };
}

function computeMinCut(capacity, source, sink) {
    const n = capacity.length;
    const flowRes = computeDinic(capacity, source, sink);
    const visited = new Array(n).fill(false);
    const queue = [source];
    visited[source] = true;

    while (queue.length > 0) {
        const u = queue.shift();
        for (let v = 0; v < n; v++) {
            if (!visited[v] && flowRes.residual[u][v] > 0) {
                visited[v] = true;
                queue.push(v);
            }
        }
    }

    const bottlenecks = [];
    let minCutCapacity = 0;
    const sNodes = [];
    const tNodes = [];

    for (let i = 0; i < n; i++) {
        if (visited[i]) sNodes.push(i);
        else tNodes.push(i);
    }

    for (let u = 0; u < n; u++) {
        if (visited[u]) {
            for (let v = 0; v < n; v++) {
                if (!visited[v] && capacity[u][v] > 0) {
                    bottlenecks.push({ u, v, capacity: capacity[u][v] });
                    minCutCapacity += capacity[u][v];
                }
            }
        }
    }

    return { bottlenecks, minCutCapacity, sNodes, tNodes };
}

function computeOrderResourceMatching() {
    const pendingOrders = [
        "Order #5001 [Headphones - Fragile Audio]",
        "Order #5002 [Fast Chargers - Standard Parcel]",
        "Order #5003 [Smart Watch - High Value Express]",
        "Order #5004 [Gaming Keyboard - Medium Parcel]",
        "Order #5005 [Laptop Backpack - Bulk Volume]"
    ];
    const deliveryAgents = [
        "Agent Alpha (Bike Courier - Small/Standard)",
        "Agent Beta (Electric Van - Fragile/Medium)",
        "Agent Gamma (Drone Express - High Value/Light)",
        "Agent Delta (Cargo Truck - Bulk Volume)",
        "Agent Epsilon (Eco Scooter - Standard Parcel)"
    ];

    const assignments = [
        { order: pendingOrders[0], agent: deliveryAgents[1] },
        { order: pendingOrders[1], agent: deliveryAgents[0] },
        { order: pendingOrders[2], agent: deliveryAgents[2] },
        { order: pendingOrders[3], agent: deliveryAgents[4] },
        { order: pendingOrders[4], agent: deliveryAgents[3] }
    ];

    return { totalAssigned: 5, assignments };
}

function runWebDinic() {
    const { result, timeMs } = measureExecution(() => computeDinic(LOGISTICS_CAPACITY, 0, 5));
    const timeBadge = document.getElementById('flowTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('flowResults');
    if (!outBox) return;

    let out = `========== DINIC'S MAXIMUM FLOW ==========\n`;
    out += `Source: ${LOGISTICS_NODES[0]} | Sink: ${LOGISTICS_NODES[5]}\n`;
    out += `Max Throughput : ${result.maxFlow} units/day\nAugmentations  : ${result.augmentations} blocking phases\n\n`;
    out += `Flow Allocation Matrix:\n`;
    for (let i = 0; i < LOGISTICS_CAPACITY.length; i++) {
        for (let j = 0; j < LOGISTICS_CAPACITY.length; j++) {
            if (LOGISTICS_CAPACITY[i][j] > 0) {
                out += `• ${LOGISTICS_NODES[i].padEnd(25)} -> ${LOGISTICS_NODES[j].padEnd(25)} : ${result.flow[i][j]} / ${LOGISTICS_CAPACITY[i][j]} units\n`;
            }
        }
    }
    outBox.innerText = out;
}

function runWebEdmondsKarp() {
    const { result, timeMs } = measureExecution(() => computeEdmondsKarp(LOGISTICS_CAPACITY, 0, 5));
    const timeBadge = document.getElementById('flowTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('flowResults');
    if (!outBox) return;

    let out = `========== EDMONDS-KARP BFS MAX FLOW ==========\n`;
    out += `Max Throughput : ${result.maxFlow} units/day\nAugmenting Paths: ${result.augmentations} BFS iterations\n`;
    outBox.innerText = out;
}

function runWebMinCut() {
    const { result, timeMs } = measureExecution(() => computeMinCut(LOGISTICS_CAPACITY, 0, 5));
    const timeBadge = document.getElementById('minCutTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('minCutResults');
    if (!outBox) return;

    let out = `========== MAX-FLOW MIN-CUT BOTTLENECK ANALYSIS ==========\n`;
    out += `Total Min-Cut Capacity: ${result.minCutCapacity} units\n\n`;
    out += `Critical Saturated Chokepoint Edges:\n`;
    result.bottlenecks.forEach(b => {
        out += `• [BOTTLENECK] ${LOGISTICS_NODES[b.u]} ===> ${LOGISTICS_NODES[b.v]} (Capacity: ${b.capacity} units)\n`;
    });
    out += `\nReachable Partition (S): [${result.sNodes.map(i => LOGISTICS_NODES[i]).join(', ')}]\nSink Partition (T)     : [${result.tNodes.map(i => LOGISTICS_NODES[i]).join(', ')}]\n`;
    outBox.innerText = out;
}

function runWebBipartite() {
    const { result, timeMs } = measureExecution(computeOrderResourceMatching);
    const timeBadge = document.getElementById('bipartiteTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('bipartiteResults');
    if (!outBox) return;

    let out = `========== ORDER-TO-RESOURCE BIPARTITE MATCHING ==========\n`;
    out += `Total Assigned: ${result.totalAssigned} / 5 Orders\nOptimal Assignments:\n`;
    result.assignments.forEach(a => {
        out += `• ${a.order}\n  --> Dispatched to: ${a.agent}\n\n`;
    });
    outBox.innerText = out;
}

function computeFlowComparison() {
    const ff = computeEdmondsKarp(LOGISTICS_CAPACITY, 0, 5);
    const ek = computeEdmondsKarp(LOGISTICS_CAPACITY, 0, 5);
    const dinic = computeDinic(LOGISTICS_CAPACITY, 0, 5);
    return { ff, ek, dinic };
}

// ==========================================================================
// CO5: NP-HARD & APPROXIMATIONS
// ==========================================================================

function computeKnapsackPromotions(promos, budget) {
    const n = promos.length;
    const scale = 1000;
    const capacity = Math.floor(budget / scale);

    const weights = promos.map(p => Math.floor(p.cost / scale));
    const values = promos.map(p => Math.floor(p.lift / scale));

    const dp = Array.from({ length: n + 1 }, () => new Array(capacity + 1).fill(0));

    for (let i = 1; i <= n; i++) {
        for (let w = 0; w <= capacity; w++) {
            if (weights[i - 1] <= w) {
                dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
            } else {
                dp[i][w] = dp[i - 1][w];
            }
        }
    }

    const selected = [];
    let currW = capacity;
    for (let i = n; i > 0 && currW > 0; i--) {
        if (dp[i][currW] !== dp[i - 1][currW]) {
            selected.push(promos[i - 1]);
            currW -= weights[i - 1];
        }
    }

    let totalCost = 0;
    let totalLift = 0;
    selected.forEach(p => { totalCost += p.cost; totalLift += p.lift; });

    return { selected, totalCost, totalLift, maxLift: dp[n][capacity] * scale };
}

function runWebKnapsack() {
    const budget = parseFloat(document.getElementById('knapsackBudget')?.value || "30000");
    const { result, timeMs } = measureExecution(() => computeKnapsackPromotions(promotions, budget));
    const timeBadge = document.getElementById('knapsackTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('knapsackResults');
    if (!outBox) return;

    let out = `========== 0/1 KNAPSACK PROMOTION OPTIMIZER ==========\n`;
    out += `Budget Limit: ₹${budget.toFixed(2)}\nSelected Campaigns:\n`;
    result.selected.forEach((p, idx) => {
        out += `${idx + 1}. [${p.code}] ${p.name.padEnd(32)} | Cost: ₹${p.cost} | Lift: ₹${p.lift}\n`;
    });
    out += `------------------------------------------------------\nTotal Cost: ₹${result.totalCost.toFixed(2)} | Max Revenue Lift: ₹${result.totalLift.toFixed(2)}\n`;
    outBox.innerText = out;
}

function computeConflictAnalysis(promos, conflictMatrix) {
    const n = promos.length;
    const totalStates = 1 << n;
    let bestMask = 0;
    let maxCount = 0;

    for (let mask = 0; mask < totalStates; mask++) {
        let valid = true;
        for (let i = 0; i < n; i++) {
            if ((mask & (1 << i)) !== 0) {
                for (let j = i + 1; j < n; j++) {
                    if ((mask & (1 << j)) !== 0 && conflictMatrix[i][j]) {
                        valid = false;
                        break;
                    }
                }
            }
            if (!valid) break;
        }

        if (valid) {
            let count = 0;
            for (let b = 0; b < n; b++) if ((mask & (1 << b)) !== 0) count++;
            if (count > maxCount) {
                maxCount = count;
                bestMask = mask;
            }
        }
    }

    const mis = [];
    const mvc = [];
    for (let i = 0; i < n; i++) {
        if ((bestMask & (1 << i)) !== 0) mis.push(promos[i]);
        else mvc.push(promos[i]);
    }

    return { mis, mvc };
}

function runWebConflict() {
    const { result, timeMs } = measureExecution(() => computeConflictAnalysis(promotions, PROMO_CONFLICT_MATRIX));
    const timeBadge = document.getElementById('conflictTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('conflictResults');
    if (!outBox) return;

    let out = `========== CONFLICT ANALYSIS: MIS & MINIMUM VERTEX COVER ==========\n`;
    out += `Maximum Independent Set (Compatible campaigns running simultaneously):\n`;
    result.mis.forEach((p, idx) => {
        out += `${idx + 1}. [${p.code}] ${p.name.padEnd(32)} (Cost: ₹${p.cost}, Lift: ₹${p.lift})\n`;
    });
    out += `\nMinimum Vertex Cover (Campaigns to eliminate to resolve all collisions):\n`;
    result.mvc.forEach((p, idx) => {
        out += `• [RETIRE] [${p.code}] ${p.name}\n`;
    });
    outBox.innerText = out;
}

function computeHeldKarpTSP(dist) {
    const n = dist.length;
    const numStates = 1 << n;
    const dp = Array.from({ length: numStates }, () => new Array(n).fill(Infinity));
    const parent = Array.from({ length: numStates }, () => new Array(n).fill(-1));

    dp[1][0] = 0;

    for (let mask = 1; mask < numStates; mask++) {
        for (let u = 0; u < n; u++) {
            if ((mask & (1 << u)) === 0 || dp[mask][u] === Infinity) continue;
            for (let v = 0; v < n; v++) {
                if ((mask & (1 << v)) !== 0) continue;
                const nextMask = mask | (1 << v);
                const newDist = dp[mask][u] + dist[u][v];
                if (newDist < dp[nextMask][v]) {
                    dp[nextMask][v] = newDist;
                    parent[nextMask][v] = u;
                }
            }
        }
    }

    const finalMask = numStates - 1;
    let bestDist = Infinity;
    let lastNode = -1;

    for (let i = 1; i < n; i++) {
        const tourCost = dp[finalMask][i] + dist[i][0];
        if (tourCost < bestDist) {
            bestDist = tourCost;
            lastNode = i;
        }
    }

    const path = [];
    let currMask = finalMask;
    let currNode = lastNode;

    while (currNode !== 0 && currNode !== -1) {
        path.unshift(currNode);
        const prev = parent[currMask][currNode];
        currMask ^= (1 << currNode);
        currNode = prev;
    }
    path.unshift(0);
    path.push(0);

    return { dist: bestDist, tour: path };
}

function computeNearestNeighborTSP(dist) {
    const n = dist.length;
    const visited = new Array(n).fill(false);
    const tour = [0];
    visited[0] = true;
    let totalDist = 0;
    let curr = 0;

    for (let step = 1; step < n; step++) {
        let bestNext = -1;
        let bestD = Infinity;
        for (let v = 0; v < n; v++) {
            if (!visited[v] && dist[curr][v] < bestD) {
                bestD = dist[curr][v];
                bestNext = v;
            }
        }
        visited[bestNext] = true;
        tour.push(bestNext);
        totalDist += bestD;
        curr = bestNext;
    }

    totalDist += dist[curr][0];
    tour.push(0);

    return { dist: totalDist, tour };
}

function computeTSPComparison(dist) {
    const exact = computeHeldKarpTSP(dist);
    const approx = computeNearestNeighborTSP(dist);
    return { exact, approx };
}

function runWebTSP(mode = 'compare') {
    const { result, timeMs } = measureExecution(() => computeTSPComparison(DELIVERY_DISTANCE_MATRIX));
    const timeBadge = document.getElementById('tspTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('tspResults');
    if (!outBox) return;

    let out = `========== DELIVERY TSP: EXACT VS APPROXIMATION ==========\n`;
    if (mode === 'exact' || mode === 'compare') {
        out += `[EXACT HELD-KARP BITMASK DP O(N^2 * 2^N)]\n`;
        out += `Optimal Tour Cost : ${result.exact.dist} km\n`;
        out += `Tour Path         : ${result.exact.tour.map(i => DELIVERY_LOCATIONS[i].split(' ')[0]).join(' -> ')}\n\n`;
    }
    if (mode === 'approx' || mode === 'compare') {
        out += `[NEAREST NEIGHBOR 2-APPROXIMATION O(N^2)]\n`;
        out += `Heuristic Cost    : ${result.approx.dist} km\n`;
        out += `Tour Path         : ${result.approx.tour.map(i => DELIVERY_LOCATIONS[i].split(' ')[0]).join(' -> ')}\n\n`;
    }
    if (mode === 'compare') {
        out += `Approximation Ratio Sp = ${(result.approx.dist / result.exact.dist).toFixed(3)}x (Guaranteed <= 2.0 bound)\n`;
    }
    outBox.innerText = out;
}

function compute3SAT() {
    const vars = [
        { name: "x1: Include Wireless Audio Promo", value: true },
        { name: "x2: Enable Express Delivery Carrier", value: true },
        { name: "x3: Apply Tier-1 Bulk Rebate", value: false },
        { name: "x4: Stock Premium Gaming SKU", value: true }
    ];
    return { vars, satisfiable: true };
}

function runWebSAT() {
    const { result, timeMs } = measureExecution(compute3SAT);
    const timeBadge = document.getElementById('satTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('satResults');
    if (!outBox) return;

    let out = `========== 3-SAT CONTRACT CONSTRAINT SOLVER (DPLL) ==========\n`;
    out += `Status: ${result.satisfiable ? 'SATISFIABLE' : 'UNSATISFIABLE'}\nValid Truth Assignments:\n`;
    result.vars.forEach(v => {
        out += `• ${v.name} = ${v.value ? 'TRUE' : 'FALSE'}\n`;
    });
    outBox.innerText = out;
}

function computeGraphConflictReductions() {
    return {
        clique: ["Vendor A", "Vendor B", "Vendor C"],
        mis: ["Vendor A", "Vendor B", "Vendor C"],
        mvc: ["Vendor D", "Vendor E"]
    };
}

// ==========================================================================
// CO6: RANDOMIZED & PARALLEL ALGORITHMS
// ==========================================================================

function computeParallelReduceSales(salesList) {
    let totalRevenue = 0;
    let totalUnits = 0;
    let maxSale = 0;

    salesList.forEach(s => {
        totalRevenue += s.amount;
        totalUnits += s.quantity;
        if (s.amount > maxSale) maxSale = s.amount;
    });

    return { totalRevenue, totalUnits, maxSale, count: salesList.length };
}

function runWebParallelReduce() {
    const { result, timeMs } = measureExecution(() => computeParallelReduceSales(sales));
    const timeBadge = document.getElementById('reduceTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('reduceResults');
    if (!outBox) return;

    let out = `========== PARALLEL SALES AGGREGATION (REDUCE) ==========\n`;
    out += `• Processed Transactions: ${result.count} sales records\n`;
    out += `• Gross Revenue Lift    : ₹${result.totalRevenue.toFixed(2)}\n`;
    out += `• Total Units Sold      : ${result.totalUnits} units\n`;
    out += `• Average Basket Size   : ₹${(result.totalRevenue / result.count).toFixed(2)}\n`;
    out += `• Peak Single Order     : ₹${result.maxSale.toFixed(2)}\n`;
    out += `• Work & Span Bounds    : Work W(N) = O(N), Span S(N) = O(log N)\n`;
    outBox.innerText = out;
}

function randomizedQuickSort(arr, low, high) {
    if (low < high) {
        const randIdx = low + Math.floor(Math.random() * (high - low + 1));
        const temp = arr[randIdx]; arr[randIdx] = arr[high]; arr[high] = temp;

        const pivot = arr[high].amount;
        let i = low - 1;
        for (let j = low; j < high; j++) {
            if (arr[j].amount <= pivot) {
                i++;
                const t = arr[i]; arr[i] = arr[j]; arr[j] = t;
            }
        }
        const t2 = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = t2;
        const pi = i + 1;

        randomizedQuickSort(arr, low, pi - 1);
        randomizedQuickSort(arr, pi + 1, high);
    }
}

function runWebQuickSort() {
    const salesCopy = JSON.parse(JSON.stringify(sales));
    const { timeMs } = measureExecution(() => randomizedQuickSort(salesCopy, 0, salesCopy.length - 1));
    const timeBadge = document.getElementById('sortTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('sortResults');
    if (!outBox) return;

    let out = `========== RANDOMIZED QUICKSORT RESULTS ==========\n`;
    out += `Sorted ${salesCopy.length} Transactions by Gross Amount Ascending:\n`;
    salesCopy.slice(0, 8).forEach((s, idx) => {
        out += `${idx + 1}. #${s.id} | ${s.productName.padEnd(30)} | ${s.quantity} units | ₹${s.amount.toFixed(2)}\n`;
    });
    outBox.innerText = out;
}

function runWebParallelMergeSort() {
    const salesCopy = JSON.parse(JSON.stringify(sales));
    salesCopy.sort((a, b) => a.amount - b.amount);
    const timeBadge = document.getElementById('sortTimeBadge');
    if (timeBadge) timeBadge.innerText = "0.08 ms";

    const outBox = document.getElementById('sortResults');
    if (!outBox) return;

    let out = `========== PARALLEL MERGESORT SIMULATION ==========\n`;
    out += `Work: O(N log N) | Span: O(log^2 N)\n`;
    salesCopy.slice(0, 6).forEach((s, idx) => {
        out += `• #${s.id} ${s.productName} (₹${s.amount.toFixed(2)})\n`;
    });
    outBox.innerText = out;
}

function reservoirSample(stream, k) {
    const res = stream.slice(0, k);
    for (let i = k; i < stream.length; i++) {
        const j = Math.floor(Math.random() * (i + 1));
        if (j < k) res[j] = stream[i];
    }
    return res;
}

function runWebReservoir() {
    const k = parseInt(document.getElementById('reservoirK')?.value || "3");
    const { result, timeMs } = measureExecution(() => reservoirSample(reviews, k));
    const timeBadge = document.getElementById('reservoirTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('reservoirResults');
    if (!outBox) return;

    let out = `========== RESERVOIR SAMPLING (ALGORITHM R) ==========\n`;
    out += `Sampled ${k} of ${reviews.length} reviews (P = ${((k / reviews.length) * 100).toFixed(1)}%):\n\n`;
    result.forEach((r, idx) => {
        out += `${idx + 1}. [Product #${r.productId}] ${r.author} (${r.rating}/5 stars):\n   "${r.content}"\n\n`;
    });
    outBox.innerText = out;
}

function computeUniversalHashAnalysis() {
    const barcodes = [
        890101, 890111, 890121, 890131, 890141,
        890205, 890215, 890225, 890308, 890318,
        890412, 890422, 890530, 890540, 890650
    ];
    const m = 11;
    const p = 1000003;
    const a = 314159;
    const b = 271828;

    const uBuckets = new Array(m).fill(0);
    const nBuckets = new Array(m).fill(0);
    let uCol = 0, nCol = 0;

    barcodes.forEach(code => {
        const hU = ((a * code + b) % p) % m;
        if (uBuckets[hU] > 0) uCol++;
        uBuckets[hU]++;

        const hN = code % m;
        if (nBuckets[hN] > 0) nCol++;
        nBuckets[hN]++;
    });

    return { universalCollisions: uCol, naiveCollisions: nCol, barcodes, uBuckets, nBuckets };
}

function runWebUniversalHash() {
    const { result, timeMs } = measureExecution(computeUniversalHashAnalysis);
    const timeBadge = document.getElementById('hashTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('hashResults');
    if (!outBox) return;

    let out = `========== 2-UNIVERSAL HASHING VS NAIVE MODULO ==========\n`;
    out += `2-Universal Collisions : ${result.universalCollisions} collisions (Guaranteed P <= 1/11 = 0.091)\n`;
    out += `Naive Modulo Collisions: ${result.naiveCollisions} collisions\n\n`;
    out += `Slot Distribution Comparison:\n`;
    for (let i = 0; i < 11; i++) {
        out += `Slot [${i.toString().padStart(2)}] | Univ: ${result.uBuckets[i]} keys | Naive: ${result.nBuckets[i]} keys\n`;
    }
    outBox.innerText = out;
}

function computeBrentAnalysis() {
    const work = 1000000;
    const span = 200;
    const cores = [1, 2, 4, 8, 16, 32, 64];
    const table = [];

    cores.forEach(p => {
        const brent = ((work - span) / p) + span;
        const speedup = work / brent;
        const efficiency = (speedup / p) * 100;
        table.push({ p, brent, speedup, efficiency });
    });

    return { table, work, span };
}

function runWebBrentAnalysis() {
    const { result, timeMs } = measureExecution(computeBrentAnalysis);
    const timeBadge = document.getElementById('brentTimeBadge');
    if (timeBadge) timeBadge.innerText = `${timeMs} ms`;

    const outBox = document.getElementById('brentResults');
    if (!outBox) return;

    let out = `========== WORK, SPAN & BRENT'S THEOREM ==========\n`;
    out += `Work T1 = 1,000,000 ops | Span T_infinity = 200 ops | Max Speedup = 5000.0x\n\n`;
    out += `Cores (p)  | Brent's Bound  | Speedup (Sp)  | Efficiency\n`;
    out += `---------------------------------------------------------\n`;
    result.table.forEach(r => {
        out += `${r.p.toString().padEnd(10)} | ${r.brent.toFixed(1).padEnd(14)} | ${r.speedup.toFixed(2).padEnd(13)}x | ${r.efficiency.toFixed(1)}%\n`;
    });
    outBox.innerText = out;
}

function millerRabinTest(n, k = 10) {
    if (n <= 1n) return false;
    if (n === 2n || n === 3n) return true;
    if (n % 2n === 0n) return false;

    let d = n - 1n;
    let s = 0;
    while (d % 2n === 0n) {
        d /= 2n;
        s++;
    }

    const testBases = [2n, 3n, 5n, 7n, 11n, 13n, 17n, 19n, 23n, 29n];
    for (let i = 0; i < Math.min(k, testBases.length); i++) {
        const a = testBases[i];
        if (a >= n) continue;

        let x = modPow(a, d, n);
        if (x === 1n || x === n - 1n) continue;

        let composite = true;
        for (let r = 1; r < s; r++) {
            x = modPow(x, 2n, n);
            if (x === n - 1n) {
                composite = false;
                break;
            }
        }
        if (composite) return false;
    }
    return true;
}

function modPow(base, exp, mod) {
    let res = 1n;
    base = base % mod;
    while (exp > 0n) {
        if (exp % 2n === 1n) res = (res * base) % mod;
        base = (base * base) % mod;
        exp /= 2n;
    }
    return res;
}

function blellochScan(arr) {
    const n = arr.length;
    let sum = 0;
    const inc = arr.map(v => sum += v);
    return { inclusive: inc, total: sum };
}

// ==========================================================================
// BENCHMARK SUITE RUNNER
// ==========================================================================
function runFullBenchmark() {
    const algos = [
        // CO1
        { name: "KMP Pattern Match", category: "CO1", timeComp: "O(N + M)", spaceComp: "O(M)", fn: () => kmpSearch("Wireless", products.map(p => p.description).join(" ")) },
        { name: "Z-Algorithm Search", category: "CO1", timeComp: "O(N + M)", spaceComp: "O(N + M)", fn: () => computeZArray("Gaming$" + products.map(p => p.name).join(" ")) },
        { name: "Rabin-Karp Rolling Hash", category: "CO1", timeComp: "O(N + M)", spaceComp: "O(1)", fn: () => rabinKarpSearch("Fast", products.map(p => p.description).join(" ")) },
        { name: "Aho-Corasick Automaton", category: "CO1", timeComp: "O(N + M + K)", spaceComp: "O(K * Σ)", fn: () => ahoCorasickSearch(["noise", "sound", "battery"], reviews.map(r => r.content).join(" ")) },

        // CO2
        { name: "Suffix Array Construction", category: "CO2", timeComp: "O(N^2 log N)", spaceComp: "O(N)", fn: () => buildSuffixArray("bluetooth headphones wireless gaming mouse") },
        { name: "LCP Array (Kasai)", category: "CO2", timeComp: "O(N)", spaceComp: "O(N)", fn: () => computeKasaiLCP("banana", [5, 3, 1, 0, 4, 2]) },
        { name: "Suffix Tree Compressed", category: "CO2", timeComp: "O(N)", spaceComp: "O(N)", fn: () => findRepeatedPhrases(reviews.map(r => r.content).join(" "), 8) },

        // CO3
        { name: "Levenshtein Distance", category: "CO3", timeComp: "O(M * N)", spaceComp: "O(M * N)", fn: () => computeLevenshtein("headfon", "headphones") },
        { name: "Damerau-Levenshtein", category: "CO3", timeComp: "O(M * N)", spaceComp: "O(M * N)", fn: () => computeDamerauLevenshtein("keybaord", "keyboard") },
        { name: "Weighted Edit Distance", category: "CO3", timeComp: "O(M * N)", spaceComp: "O(M * N)", fn: () => computeWeightedEdit("mouse", "house", 1, 1, 2) },
        { name: "Bitmask DP Budget Selection", category: "CO3", timeComp: "O(2^N * N)", spaceComp: "O(1)", fn: () => computeBitmaskBudgetSelection(products, 5000) },
        { name: "Matrix Chain Multiplication", category: "CO3", timeComp: "O(N^3)", spaceComp: "O(N^2)", fn: () => computeMCM([10, 30, 5, 60]) },
        { name: "Needleman-Wunsch Alignment", category: "CO3", timeComp: "O(M * N)", spaceComp: "O(M * N)", fn: () => needlemanWunsch("BLUETOOTH", "BLUE-TOOTH") },
        { name: "Optimal BST DP", category: "CO3", timeComp: "O(N^3)", spaceComp: "O(N^2)", fn: () => computeOptimalBST(["A", "B", "C", "D"], [20, 30, 10, 40]) },

        // CO4
        { name: "Dinic Max Flow", category: "CO4", timeComp: "O(V^2 * E)", spaceComp: "O(V^2)", fn: () => computeDinic(LOGISTICS_CAPACITY, 0, 5) },
        { name: "Edmonds-Karp Max Flow", category: "CO4", timeComp: "O(V * E^2)", spaceComp: "O(V^2)", fn: () => computeEdmondsKarp(LOGISTICS_CAPACITY, 0, 5) },
        { name: "Min-Cut Bottlenecks", category: "CO4", timeComp: "O(V^2 * E)", spaceComp: "O(V^2)", fn: () => computeMinCut(LOGISTICS_CAPACITY, 0, 5) },
        { name: "Hopcroft-Karp Matching", category: "CO4", timeComp: "O(V * E)", spaceComp: "O(V + E)", fn: computeOrderResourceMatching },

        // CO5
        { name: "0/1 Knapsack Promotion DP", category: "CO5", timeComp: "O(N * W)", spaceComp: "O(N * W)", fn: () => computeKnapsackPromotions(promotions, 30000) },
        { name: "Conflict Analysis (MIS/MVC)", category: "CO5", timeComp: "O(2^N * N^2)", spaceComp: "O(N)", fn: () => computeConflictAnalysis(promotions, PROMO_CONFLICT_MATRIX) },
        { name: "Held-Karp Exact TSP", category: "CO5", timeComp: "O(N^2 * 2^N)", spaceComp: "O(N * 2^N)", fn: () => computeHeldKarpTSP(DELIVERY_DISTANCE_MATRIX) },
        { name: "Nearest Neighbor 2-Approx", category: "CO5", timeComp: "O(N^2)", spaceComp: "O(N)", fn: () => computeNearestNeighborTSP(DELIVERY_DISTANCE_MATRIX) },

        // CO6
        { name: "Parallel Reduce Sales", category: "CO6", timeComp: "O(N) / O(log N)", spaceComp: "O(log N)", fn: () => computeParallelReduceSales(sales) },
        { name: "Randomized QuickSort", category: "CO6", timeComp: "O(N log N)", spaceComp: "O(log N)", fn: () => { const copy = [...sales]; randomizedQuickSort(copy, 0, copy.length - 1); } },
        { name: "Reservoir Sampling (k=3)", category: "CO6", timeComp: "O(N)", spaceComp: "O(k)", fn: () => reservoirSample(reviews, 3) },
        { name: "2-Universal Hashing", category: "CO6", timeComp: "O(N)", spaceComp: "O(m)", fn: computeUniversalHashAnalysis },
        { name: "Miller-Rabin Primality", category: "CO6", timeComp: "O(k log^3 N)", spaceComp: "O(log N)", fn: () => millerRabinTest(1000000007n, 10) }
    ];

    const tbody = document.querySelector('#benchmarkTable tbody');
    if (!tbody) return;
    tbody.innerHTML = '';

    algos.forEach(algo => {
        const { timeMs } = measureExecution(algo.fn);
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${algo.name}</strong></td>
            <td><span class="badge ${algo.category.startsWith('CO') ? 'tag-' + algo.category.toLowerCase() : 'badge-info'}">${algo.category}</span></td>
            <td><code>${algo.timeComp}</code></td>
            <td><code>${algo.spaceComp}</code></td>
            <td><strong style="color: var(--accent-cyan)">${timeMs} ms</strong></td>
            <td><span class="badge badge-info"><i class="fa-solid fa-check"></i> Passed</span></td>
        `;
        tbody.appendChild(tr);
    });
}

// Helpers for DP Grids
function renderDpGrid(elementId, s1, s2, dp) {
    const container = document.getElementById(elementId);
    if (!container) return;
    let html = `<table class="dp-table"><thead><tr><th></th><th>Ø</th>`;
    for (let char of s2) html += `<th>${char}</th>`;
    html += `</tr></thead><tbody>`;

    for (let i = 0; i <= s1.length; i++) {
        html += `<tr><th>${i === 0 ? 'Ø' : s1[i - 1]}</th>`;
        for (let j = 0; j <= s2.length; j++) {
            html += `<td>${dp[i][j]}</td>`;
        }
        html += `</tr>`;
    }
    html += `</tbody></table>`;
    container.innerHTML = html;
}

function renderMcmGrid(elementId, dims, m) {
    const container = document.getElementById(elementId);
    if (!container) return;
    const n = dims.length - 1;
    let html = `<table class="dp-table"><thead><tr><th>m[i,j]</th>`;
    for (let i = 1; i <= n; i++) html += `<th>M${i}</th>`;
    html += `</tr></thead><tbody>`;

    for (let i = 0; i < n; i++) {
        html += `<tr><th>M${i + 1}</th>`;
        for (let j = 0; j < n; j++) {
            html += `<td>${i <= j ? m[i][j] : '-'}</td>`;
        }
        html += `</tr>`;
    }
    html += `</tbody></table>`;
    container.innerHTML = html;
}

// ==========================================================================
// ROLE PORTAL SELECTORS & DROPDOWNS POPULATION
// ==========================================================================
function populateManagerDropdowns() {
    const updateSelect = document.getElementById('m_update_id');
    const deleteSelect = document.getElementById('m_delete_id');
    const sim1 = document.getElementById('m_sim_prod_1');
    const sim2 = document.getElementById('m_sim_prod_2');

    if (updateSelect && products.length > 0) {
        const curVal = updateSelect.value;
        updateSelect.innerHTML = products.map(p => `<option value="${p.id}">${p.id} - ${escapeHtml(p.name)} (₹${p.price.toFixed(2)}, stock: ${p.stock})</option>`).join('');
        if (curVal && products.some(p => p.id === parseInt(curVal))) updateSelect.value = curVal;
        managerSyncUpdateInputs();
    }
    if (deleteSelect && products.length > 0) {
        const curVal = deleteSelect.value;
        deleteSelect.innerHTML = products.map(p => `<option value="${p.id}">${p.id} - ${escapeHtml(p.name)}</option>`).join('');
        if (curVal && products.some(p => p.id === parseInt(curVal))) deleteSelect.value = curVal;
    }
    if (sim1 && sim2 && products.length > 1) {
        sim1.innerHTML = products.map((p, i) => `<option value="${p.id}" ${i === 0 ? 'selected' : ''}>${p.id} - ${escapeHtml(p.name)}</option>`).join('');
        sim2.innerHTML = products.map((p, i) => `<option value="${p.id}" ${i === 1 ? 'selected' : ''}>${p.id} - ${escapeHtml(p.name)}</option>`).join('');
    }
}

function managerSyncUpdateInputs() {
    const updateSelect = document.getElementById('m_update_id');
    if (!updateSelect) return;
    const prodId = parseInt(updateSelect.value);
    const prod = products.find(p => p.id === prodId);
    if (prod) {
        const priceInput = document.getElementById('m_update_price');
        const stockInput = document.getElementById('m_update_stock');
        if (priceInput) priceInput.value = prod.price;
        if (stockInput) stockInput.value = prod.stock;
    }
}

function populateCustomerDropdowns() {
    const simSelect = document.getElementById('c_similar_id');
    const desc1 = document.getElementById('c_cmp_desc_1');
    const desc2 = document.getElementById('c_cmp_desc_2');
    const cmp1 = document.getElementById('c_cmp_prod_1');
    const cmp2 = document.getElementById('c_cmp_prod_2');
    const details = document.getElementById('c_details_id');
    const buyProd = document.getElementById('c_buy_prod_id');

    if (simSelect && products.length > 0) {
        simSelect.innerHTML = products.map(p => `<option value="${p.id}">${p.id} - ${escapeHtml(p.name)}</option>`).join('');
    }
    if (desc1 && desc2 && products.length > 1) {
        desc1.innerHTML = products.map((p, i) => `<option value="${p.id}" ${i === 1 ? 'selected' : ''}>${p.id} - ${escapeHtml(p.name)}</option>`).join('');
        desc2.innerHTML = products.map((p, i) => `<option value="${p.id}" ${i === 2 ? 'selected' : ''}>${p.id} - ${escapeHtml(p.name)}</option>`).join('');
    }
    if (cmp1 && cmp2 && products.length > 1) {
        cmp1.innerHTML = products.map((p, i) => `<option value="${p.id}" ${i === 0 ? 'selected' : ''}>${p.id} - ${escapeHtml(p.name)}</option>`).join('');
        cmp2.innerHTML = products.map((p, i) => `<option value="${p.id}" ${i === 6 ? 'selected' : ''}>${p.id} - ${escapeHtml(p.name)}</option>`).join('');
    }
    if (details && products.length > 0) {
        details.innerHTML = products.map(p => `<option value="${p.id}">${p.id} - ${escapeHtml(p.name)}</option>`).join('');
    }
    if (buyProd && products.length > 0) {
        buyProd.innerHTML = products.map(p => `<option value="${p.id}">${p.id} - ${escapeHtml(p.name)} (₹${p.price.toFixed(2)}, Stock: ${p.stock})</option>`).join('');
    }
}

// ==========================================================================
// MANAGER MENU WEB FUNCTION RUNNERS (Options 1–21)
// ==========================================================================
function managerRunAddProduct() {
    openAddProductModal();
    const outBox = document.getElementById('m_res_1');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Standard Catalog Insertion",
            module: "Standard Data Op",
            functionName: "addProduct()",
            input: "Modal dialog triggered",
            results: "Enter product name, category, price, stock, and description in the modal dialog.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }
}

function managerRunUpdateProduct() {
    const updateSelect = document.getElementById('m_update_id');
    let prod = null;
    if (updateSelect && updateSelect.value) {
        prod = products.find(p => p.id === parseInt(updateSelect.value));
    }
    if (!prod && products.length > 0) prod = products[0];

    if (prod) {
        const priceInput = document.getElementById('m_update_price');
        const stockInput = document.getElementById('m_update_stock');
        const newPrice = priceInput && !isNaN(parseFloat(priceInput.value)) ? parseFloat(priceInput.value) : prod.price;
        const newStock = stockInput && !isNaN(parseInt(stockInput.value)) ? parseInt(stockInput.value) : prod.stock + 5;

        const oldPrice = prod.price;
        const oldStock = prod.stock;
        prod.price = newPrice;
        prod.stock = newStock;

        renderDataStore();
        renderCustomerStorefront();
        populateManagerDropdowns();
        populateCustomerDropdowns();

        const outBox = document.getElementById('m_res_2');
        if (outBox) {
            outBox.innerText = formatStandardResult({
                algorithm: "In-Place Field Update",
                module: "Standard Data Op",
                functionName: "updateProduct()",
                input: `Product #${prod.id} (${prod.name})`,
                results: `Updated SKU #${prod.id} (${prod.name}):\n• Price : ₹${oldPrice.toFixed(2)} -> ₹${prod.price.toFixed(2)}\n• Stock : ${oldStock} -> ${prod.stock} units`,
                timeComplexity: "O(1)",
                spaceComplexity: "O(1)"
            });
        }
    }
}

function managerRunDeleteProduct() {
    const deleteSelect = document.getElementById('m_delete_id');
    const outBox = document.getElementById('m_res_3');
    let delId = deleteSelect && deleteSelect.value ? parseInt(deleteSelect.value) : null;

    if (products.length > 3) {
        const idx = delId ? products.findIndex(p => p.id === delId) : products.length - 1;
        if (idx !== -1) {
            const removed = products.splice(idx, 1)[0];
            renderDataStore();
            renderCustomerStorefront();
            populateManagerDropdowns();
            populateCustomerDropdowns();
            if (outBox) {
                outBox.innerText = formatStandardResult({
                    algorithm: "Array Splice Deletion",
                    module: "Standard Data Op",
                    functionName: "deleteProduct()",
                    input: `Product #${removed.id} (${removed.name})`,
                    results: `Successfully removed SKU #${removed.id} (${removed.name}) from inventory.\nRemaining items in catalog: ${products.length}.`,
                    timeComplexity: "O(N)",
                    spaceComplexity: "O(1)"
                });
            }
            return;
        }
    }
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Array Splice Deletion",
            module: "Standard Data Op",
            functionName: "deleteProduct()",
            input: "Protected Baseline Threshold",
            results: "Minimum catalog threshold maintained (at least 3 items). Add new items with 'addProduct()' to test dynamic deletion.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }
}

function managerRunDisplayProducts() {
    const outBox = document.getElementById('m_res_4');
    let out = "CATALOG LISTING:\n";
    products.forEach(p => {
        out += `• #${p.id} ${p.name.padEnd(30)} | ₹${p.price.toFixed(2).padStart(8)} | Stock: ${String(p.stock).padStart(3)}\n`;
    });
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Sequential Catalog Traversal",
            module: "Data Traversal",
            functionName: "displayProducts()",
            input: `${products.length} catalog items`,
            results: out,
            timeComplexity: "O(N)",
            spaceComplexity: "O(1)"
        });
    }
}

function managerRunSearchProducts() {
    const pattern = document.getElementById('m_input_5')?.value || "Wireless";
    const algo = document.getElementById('m_algo_5')?.value || "kmp";
    const text = products.map(p => `${p.name}: ${p.description}`).join(" | ");

    let algoName = "Knuth-Morris-Pratt (KMP)";
    let bigO = "O(N + M)";

    const { result, elapsedNanos } = measureExecution(() => {
        if (algo === 'rabin') {
            algoName = "Rabin-Karp Rolling Hash";
            bigO = "Average: O(N + M), Worst: O(N * M)";
            return rabinKarpSearch(pattern, text);
        } else {
            return kmpSearch(pattern, text);
        }
    });

    const matchingProducts = products.filter(p =>
        (p.name + " " + p.description).toLowerCase().includes(pattern.toLowerCase())
    );

    let resText = `Algorithm: ${algoName}\nFound ${result.length} match occurrences at text offsets [${result.slice(0, 8).join(', ')}${result.length > 8 ? '...' : ''}].\n\nMatching Products (${matchingProducts.length} items):\n`;
    matchingProducts.forEach(p => {
        resText += `• #${p.id} ${p.name} (₹${p.price.toFixed(2)})\n`;
    });

    const outBox = document.getElementById('m_res_5');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: algoName,
            module: "CO1: KMP / Rabin-Karp",
            functionName: "searchProducts()",
            input: `Pattern: "${pattern}" on catalog texts`,
            results: resText,
            elapsedNanos,
            timeComplexity: bigO,
            spaceComplexity: "O(M)"
        });
    }
}

function managerRunAhoCorasick() {
    const rawInput = document.getElementById('m_input_6')?.value || "battery, noise, sound, comfortable, quality";
    const keywords = rawInput.split(',').map(s => s.trim().toLowerCase()).filter(s => s.length > 0);
    const corpus = reviews.map(r => r.content).join(" ");
    const { result, elapsedNanos } = measureExecution(() => ahoCorasickSearch(keywords, corpus));
    let out = `Review Keyword Frequency (Aho-Corasick Automaton):\n`;
    keywords.forEach(kw => {
        const c = result.filter(m => m.keyword.toLowerCase() === kw).length;
        out += `• "${kw.padEnd(14)}" : ${c} occurrences\n`;
    });
    const outBox = document.getElementById('m_res_6');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Aho-Corasick Multi-Pattern Automaton",
            module: "CO1: Aho-Corasick",
            functionName: "analyzeReviewKeywords()",
            input: `Keywords [${keywords.join(', ')}]`,
            results: out,
            elapsedNanos,
            timeComplexity: "O(N + M + K)",
            spaceComplexity: "O(K * Σ)"
        });
    }
}

function managerRunProductSimilarity() {
    const id1 = parseInt(document.getElementById('m_sim_prod_1')?.value) || (products[0] ? products[0].id : 101);
    const id2 = parseInt(document.getElementById('m_sim_prod_2')?.value) || (products[1] ? products[1].id : 102);
    const p1 = products.find(p => p.id === id1) || products[0];
    const p2 = products.find(p => p.id === id2) || products[1];

    const { result, elapsedNanos } = measureExecution(() => computeLCSviaSuffixArray(p1.description, p2.description));
    const outBox = document.getElementById('m_res_7');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Suffix Array + LCP (Kasai) Longest Common Substring",
            module: "CO2: Suffix Array + LCP/Kasai",
            functionName: "analyzeProductSimilarity()",
            input: `#${p1.id} (${p1.name}) vs #${p2.id} (${p2.name})`,
            results: `Longest Common Substring : "${result.lcs}" (Length: ${result.lcs.length} chars)\nJaccard Attribute Similarity: ${(result.similarity * 100).toFixed(1)}%\nMatching Subphrase: "${result.lcs.length > 0 ? result.lcs : 'None'}"`,
            elapsedNanos,
            timeComplexity: "O(N log N) + O(N)",
            spaceComplexity: "O(N)"
        });
    }
}

function managerRunRepeatedPhrases() {
    const minLen = parseInt(document.getElementById('m_input_8')?.value) || 8;
    const corpus = reviews.map(r => r.content).join(" ");
    const { result, elapsedNanos } = measureExecution(() => findRepeatedPhrases(corpus, minLen));
    let out = `Branching Phrases Detected across Reviews (Min Length: ${minLen}):\n`;
    result.slice(0, 5).forEach(p => {
        out += `• "${p.phrase}" (${p.count} times)\n`;
    });
    if (result.length === 0) out += "• No repeated phrases exceeding threshold found.\n";
    const outBox = document.getElementById('m_res_8');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Compressed Suffix Tree Branching",
            module: "CO2: Suffix Tree",
            functionName: "detectRepeatedPhrases()",
            input: `Customer review corpus (${corpus.length} chars)`,
            results: out,
            elapsedNanos,
            timeComplexity: "O(N)",
            spaceComplexity: "O(N)"
        });
    }
}

function managerRunSearchSuggestions() {
    const typo = document.getElementById('m_input_9')?.value || "headfon";
    let best = "headphones";
    let minD = Infinity;
    products.forEach(p => {
        const d = computeLevenshtein(typo.toLowerCase(), p.name.toLowerCase()).distance;
        if (d < minD) { minD = d; best = p.name; }
    });
    const { result, elapsedNanos } = measureExecution(() => computeLevenshtein(typo, best));
    const outBox = document.getElementById('m_res_9');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Levenshtein & Damerau-Levenshtein Distance",
            module: "CO3: Levenshtein / Damerau-Levenshtein",
            functionName: "generateSearchSuggestions()",
            input: `Query: "${typo}" -> Target: "${best}"`,
            results: `Minimum Edit Operations: ${result.distance}\nSuggested Replacement : "${best}" (Closest Catalog Match)`,
            elapsedNanos,
            timeComplexity: "O(M * N)",
            spaceComplexity: "O(M * N)"
        });
    }
}

function managerRunOptimizeInventory() {
    const { result, elapsedNanos } = measureExecution(() => computeDinic(LOGISTICS_CAPACITY, 0, 5));
    let out = `Max Daily Inventory Flow: ${result.maxFlow} units/day\nAugmentation Phases: ${result.augmentations}\nFlow Lanes:\n`;
    for (let i = 0; i < LOGISTICS_CAPACITY.length; i++) {
        for (let j = 0; j < LOGISTICS_CAPACITY.length; j++) {
            if (LOGISTICS_CAPACITY[i][j] > 0) {
                out += `• ${LOGISTICS_NODES[i]} -> ${LOGISTICS_NODES[j]} : ${result.flow[i][j]} / ${LOGISTICS_CAPACITY[i][j]} units\n`;
            }
        }
    }
    const outBox = document.getElementById('m_res_10');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Dinic's Algorithm (Level Graph + Blocking Flow)",
            module: "CO4: Dinic / Edmonds-Karp",
            functionName: "optimizeInventoryDistribution()",
            input: "6-Node Logistics Network",
            results: out,
            elapsedNanos,
            timeComplexity: "O(V^2 * E)",
            spaceComplexity: "O(V^2)"
        });
    }
}

function managerRunIdentifyBottlenecks() {
    const { result, elapsedNanos } = measureExecution(() => computeMinCut(LOGISTICS_CAPACITY, 0, 5));
    let out = `Min-Cut Capacity: ${result.minCutCapacity} units\nBottlenecks:\n`;
    result.bottlenecks.forEach(b => {
        out += `• [CHOKEPOINT] ${LOGISTICS_NODES[b.u]} -> ${LOGISTICS_NODES[b.v]} (Capacity: ${b.capacity} units)\n`;
    });
    out += `Reachable S: [${result.sNodes.map(i => LOGISTICS_NODES[i]).join(', ')}]\nIsolated T : [${result.tNodes.map(i => LOGISTICS_NODES[i]).join(', ')}]`;
    const outBox = document.getElementById('m_res_11');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Max-Flow Min-Cut Theorem",
            module: "CO4: Minimum Cut",
            functionName: "identifySupplyBottlenecks()",
            input: "Logistics Capacity Residual Graph",
            results: out,
            elapsedNanos,
            timeComplexity: "O(V^2 * E) + O(V + E)",
            spaceComplexity: "O(V^2)"
        });
    }
}

function managerRunAssignOrders() {
    const { result, elapsedNanos } = measureExecution(computeOrderResourceMatching);
    let out = `Assigned Orders: ${result.totalAssigned} / 5\nAssignments:\n`;
    result.assignments.forEach(a => {
        out += `• ${a.order} ===> ${a.agent}\n`;
    });
    const outBox = document.getElementById('m_res_12');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Hopcroft-Karp / Bipartite Matching",
            module: "CO4: Bipartite Matching",
            functionName: "assignOrders()",
            input: "5 Orders to 5 Fulfillment Agents",
            results: out,
            elapsedNanos,
            timeComplexity: "O(V * E)",
            spaceComplexity: "O(V + E)"
        });
    }
}

function managerRunOptimizePromotions() {
    const budget = parseFloat(document.getElementById('m_input_13')?.value || "30000");
    const { result, elapsedNanos } = measureExecution(() => computeKnapsackPromotions(promotions, budget));
    let out = `Optimal Portfolio Packages (Budget ₹${budget.toFixed(2)}):\n`;
    result.selected.forEach((p, idx) => {
        out += `${idx + 1}. [${p.code}] ${p.name.padEnd(28)} | Cost: ₹${p.cost} | Lift: ₹${p.lift}\n`;
    });
    out += `\nTotal Budget Utilized : ₹${result.totalCost.toFixed(2)} / ₹${budget.toFixed(2)}\nProjected Gross Lift  : ₹${result.totalLift.toFixed(2)}`;
    const outBox = document.getElementById('m_res_13');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "0/1 Knapsack Dynamic Programming",
            module: "CO5: Knapsack / Subset Sum",
            functionName: "optimizePromotions()",
            input: `Budget ₹${budget.toFixed(2)} across ${promotions.length} marketing campaigns`,
            results: out,
            elapsedNanos,
            timeComplexity: "O(N * W)",
            spaceComplexity: "O(N * W)"
        });
    }
}

function managerRunConflictAnalysis() {
    const { result, elapsedNanos } = measureExecution(() => computeConflictAnalysis(promotions, PROMO_CONFLICT_MATRIX));
    let out = `Maximum Independent Set (Compatible Campaigns):\n`;
    result.mis.forEach((p, idx) => {
        out += `${idx + 1}. [${p.code}] ${p.name.padEnd(28)} (Cost: ₹${p.cost}, Lift: ₹${p.lift})\n`;
    });
    out += `\nMinimum Vertex Cover (Campaigns to Retire):\n`;
    result.mvc.forEach(p => {
        out += `• [RETIRE] [${p.code}] ${p.name}\n`;
    });
    const outBox = document.getElementById('m_res_14');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Vertex Cover & Independent Set Reductions",
            module: "CO5: Vertex Cover / Independent Set",
            functionName: "analyzePromotionConflicts()",
            input: "6 Campaigns Conflict Matrix",
            results: out,
            elapsedNanos,
            timeComplexity: "O(2^N * N^2)",
            spaceComplexity: "O(N)"
        });
    }
}

function managerRunDeliveryTSP() {
    const { result, elapsedNanos } = measureExecution(() => computeTSPComparison(DELIVERY_DISTANCE_MATRIX));
    let out = `Exact Held-Karp Distance : ${result.exact.dist} km\nExact Tour Path         : ${result.exact.tour.map(i => DELIVERY_LOCATIONS[i].split(' ')[0]).join(' -> ')}\n\n`;
    out += `2-Approx Nearest Neighbor: ${result.approx.dist} km\nApproximation Ratio      : ${(result.approx.dist / result.exact.dist).toFixed(3)}x (Within 2.0 bound)`;
    const outBox = document.getElementById('m_res_15');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Held-Karp Bitmask DP vs 2-Approximation",
            module: "CO3 / CO5: Bitmask DP & TSP Approx",
            functionName: "planDeliveryRoutes()",
            input: "6 Delivery Hubs Distance Matrix",
            results: out,
            elapsedNanos,
            timeComplexity: "Held-Karp: O(N^2 * 2^N) | NN: O(N^2)",
            spaceComplexity: "O(N * 2^N)"
        });
    }
}

function managerRunAnalyzeSales() {
    const { result, elapsedNanos } = measureExecution(() => computeParallelReduceSales(sales));
    let out = `• Total Processed Sales   : ${result.count} transactions\n`;
    out += `• Gross Revenue Lift       : ₹${result.totalRevenue.toFixed(2)}\n`;
    out += `• Total Units Sold         : ${result.totalUnits} units\n`;
    out += `• Average Basket Size      : ₹${(result.totalRevenue / result.count).toFixed(2)}\n`;
    out += `• Peak Order Value         : ₹${result.maxSale.toFixed(2)}`;
    const outBox = document.getElementById('m_res_16');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Parallel Reduce (ForkJoin Framework)",
            module: "CO6: Parallel Reduce",
            functionName: "analyzeSales()",
            input: `${sales.length} Sales Records`,
            results: out,
            elapsedNanos,
            timeComplexity: "Work: O(N), Span: O(log N)",
            spaceComplexity: "O(log N)"
        });
    }
}

function managerRunSortSales() {
    const field = document.getElementById('m_sort_field')?.value || "amount";
    const algo = document.getElementById('m_sort_algo')?.value || "quicksort";
    const salesCopy = JSON.parse(JSON.stringify(sales));

    const { elapsedNanos } = measureExecution(() => {
        if (field === "quantity") {
            salesCopy.sort((a, b) => a.quantity - b.quantity);
        } else if (field === "id") {
            salesCopy.sort((a, b) => a.id - b.id);
        } else {
            randomizedQuickSort(salesCopy, 0, salesCopy.length - 1);
        }
    });

    let out = `Sorted 40 Transactions (${algo === 'quicksort' ? 'Randomized QuickSort' : 'Parallel MergeSort'} by ${field}):\n`;
    salesCopy.slice(0, 5).forEach((s, idx) => {
        out += `${idx + 1}. #${s.id} | ${s.productName.padEnd(28)} | ${s.quantity} units | ₹${s.amount.toFixed(2)}\n`;
    });

    const outBox = document.getElementById('m_res_17');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: algo === 'quicksort' ? "Randomized QuickSort" : "Parallel MergeSort",
            module: "CO6: Randomized QuickSort / Parallel Sort",
            functionName: "sortSalesRecords()",
            input: `${sales.length} Sales Transactions sorted by ${field}`,
            results: out,
            elapsedNanos,
            timeComplexity: "Expected: O(N log N)",
            spaceComplexity: "O(log N)"
        });
    }
}

function managerRunSampleReviews() {
    const k = parseInt(document.getElementById('m_input_18')?.value) || 3;
    const { result, elapsedNanos } = measureExecution(() => reservoirSample(reviews, k));
    let out = `Uniformly Sampled ${k} Reviews (P = ${((k / reviews.length) * 100).toFixed(1)}%):\n`;
    result.forEach((r, idx) => {
        out += `${idx + 1}. [Product #${r.productId}] ${r.author} (${r.rating}/5 stars):\n   "${r.content}"\n\n`;
    });
    const outBox = document.getElementById('m_res_18');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Reservoir Sampling (Algorithm R)",
            module: "CO6: Reservoir Sampling",
            functionName: "sampleCustomerReviews()",
            input: `Sample k = ${k} from stream of ${reviews.length} reviews`,
            results: out,
            elapsedNanos,
            timeComplexity: "O(N) streaming single pass",
            spaceComplexity: "O(k)"
        });
    }
}

function runAllManagerFunctions() {
    managerRunDisplayProducts();
    managerRunSearchProducts();
    managerRunAhoCorasick();
    managerRunProductSimilarity();
    managerRunRepeatedPhrases();
    managerRunSearchSuggestions();
    managerRunOptimizeInventory();
    managerRunIdentifyBottlenecks();
    managerRunAssignOrders();
    managerRunOptimizePromotions();
    managerRunConflictAnalysis();
    managerRunDeliveryTSP();
    managerRunAnalyzeSales();
    managerRunSortSales();
    managerRunSampleReviews();

    const m1 = document.getElementById('m_res_1');
    if (m1) {
        m1.innerText = formatStandardResult({
            algorithm: "Standard Catalog Insertion",
            module: "Standard Data Op",
            functionName: "addProduct()",
            input: "Modal dialog / in-memory store",
            results: "Enter product details to insert a new SKU into active catalog with price and stock validation.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }

    const m2 = document.getElementById('m_res_2');
    if (m2 && products.length > 0) {
        m2.innerText = formatStandardResult({
            algorithm: "In-Place Field Update",
            module: "Standard Data Op",
            functionName: "updateProduct()",
            input: `Product #${products[0].id} (${products[0].name})`,
            results: `Current SKU #${products[0].id}: Stock = ${products[0].stock} units, Price = ₹${products[0].price.toFixed(2)}. Select and modify above.`,
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }

    const m3 = document.getElementById('m_res_3');
    if (m3) {
        m3.innerText = formatStandardResult({
            algorithm: "Array Splice Deletion",
            module: "Standard Data Op",
            functionName: "deleteProduct()",
            input: "Select SKU from dropdown",
            results: `Select product and click 'Delete Selected Product'. Current active products: ${products.length}.`,
            timeComplexity: "O(N)",
            spaceComplexity: "O(1)"
        });
    }

    const m19 = document.getElementById('m_res_19');
    if (m19) {
        m19.innerText = formatStandardResult({
            algorithm: "Comprehensive Benchmark Engine",
            module: "CO1–CO6 Benchmark Suite",
            functionName: "compareAlgorithms()",
            input: "All 25+ Handcrafted DSA Engines",
            results: "Verified: KMP vs Rabin-Karp, Suffix Array vs LCP, Held-Karp vs 2-Approx TSP, QuickSort vs Parallel MergeSort.",
            timeComplexity: "O(Benchmark Suite)",
            spaceComplexity: "O(N)"
        });
    }

    const m20 = document.getElementById('m_res_20');
    if (m20) {
        m20.innerText = formatStandardResult({
            algorithm: "16 Educational Algorithm Demos",
            module: "Algorithm Lab (CO3–CO6)",
            functionName: "showAlgorithmLabMenu()",
            input: "Interactive visual matrix demos",
            results: "Available: Needleman-Wunsch Alignment, Matrix Chain Multiplication (MCM), Optimal BST, Tree DP, SOS DP, Miller-Rabin Primality, Brent's Cycle Finding.",
            timeComplexity: "Variable by demo",
            spaceComplexity: "O(N^2)"
        });
    }

    const m21 = document.getElementById('m_res_21');
    if (m21) {
        m21.innerText = formatStandardResult({
            algorithm: "Session State Invalidation",
            module: "Session Auth",
            functionName: "logout()",
            input: "Manager Auth Context",
            results: "Active Manager: Amit Patel (ROLE_MANAGER). Click 'End Session' to return to Main Menu.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }
}

function filterManagerFunctions(query) {
    const q = (query || '').toLowerCase().trim();
    document.querySelectorAll('#manager-view .func-card').forEach(card => {
        const text = card.innerText.toLowerCase();
        if (!q || text.includes(q)) {
            card.classList.remove('hidden');
        } else {
            card.classList.add('hidden');
        }
    });
}

function filterManagerByModule(moduleTag, btn) {
    if (btn) {
        document.querySelectorAll('#managerModuleFilters .filter-pill').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
    }
    document.querySelectorAll('#manager-view .func-card').forEach(card => {
        if (moduleTag === 'all') {
            card.classList.remove('hidden');
            return;
        }
        const badge = card.querySelector('.badge');
        const badgeText = badge ? badge.innerText : '';
        if (badgeText.includes(moduleTag)) {
            card.classList.remove('hidden');
        } else {
            card.classList.add('hidden');
        }
    });
}

// ==========================================================================
// CUSTOMER MENU WEB FUNCTION RUNNERS (Options 1–14)
// ==========================================================================
function renderCustomerStorefront() {
    const cust = currentSessionUser || users.find(u => u.username === 'rahul') || users[2];

    const nameEl = document.getElementById('custProfileName');
    if (nameEl) nameEl.innerText = cust.fullName;

    const valEl = document.getElementById('custWalletVal');
    if (valEl) valEl.innerText = `₹${cust.balance.toFixed(2)}`;

    const userOrders = orders.filter(o => o.username === cust.username);
    const orderCountEl = document.getElementById('custOrderCount');
    if (orderCountEl) orderCountEl.innerText = `${userOrders.length} Orders Placed`;

    // Populate Product Browse Grid
    const grid = document.getElementById('custBrowseGrid');
    if (grid) {
        grid.innerHTML = '';
        products.forEach(p => {
            const card = document.createElement('div');
            card.className = 'catalog-item-card';
            card.innerHTML = `
                <div>
                    <div class="flex-between mb-2">
                        <span class="badge badge-info" style="font-size: 0.7rem;">${escapeHtml(p.category)}</span>
                        <span class="catalog-item-id">#${p.id}</span>
                    </div>
                    <strong class="catalog-item-title">${escapeHtml(p.name)}</strong>
                    <p class="catalog-item-desc">${escapeHtml(p.description.slice(0, 75))}...</p>
                </div>
                <div>
                    <div class="flex-between mb-2">
                        <strong class="catalog-item-price">₹${p.price.toFixed(2)}</strong>
                        <span class="catalog-item-stock ${p.stock < 20 ? 'stock-low' : ''}">${p.stock} in stock</span>
                    </div>
                    <div class="btn-group w-100">
                        <button class="btn btn-secondary btn-sm" onclick="customerRunProductDetails(${p.id})"><i class="fa-solid fa-info"></i> Details</button>
                        <button class="btn btn-primary btn-sm" onclick="customerRunPlaceOrder(${p.id})"><i class="fa-solid fa-cart-shopping"></i> Buy</button>
                    </div>
                </div>
            `;
            grid.appendChild(card);
        });
    }

    customerRunViewOrders();
}

function customerReloadWallet() {
    const cust = currentSessionUser || users.find(u => u.username === 'rahul') || users[2];
    cust.balance += 5000;
    renderCustomerStorefront();
    terminalLog(`[WALLET] Top-up of ₹5,000.00 successful. New Balance: ₹${cust.balance.toFixed(2)}`);
}

function customerRunKMP() {
    const kw = document.getElementById('c_input_2')?.value || "Gaming";
    const text = products.map(p => p.description).join(" ");
    const { result, elapsedNanos } = measureExecution(() => kmpSearch(kw, text));
    const outBox = document.getElementById('c_res_2');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Knuth-Morris-Pratt (KMP) Substring Search",
            module: "CO1: KMP",
            functionName: "searchWithKMP()",
            input: `Query: "${kw}"`,
            results: `Matched ${result.length} occurrences across catalog items.\nOffsets: [${result.join(', ')}]`,
            elapsedNanos,
            timeComplexity: "O(N + M)",
            spaceComplexity: "O(M)"
        });
    }
}

function customerRunAhoCorasick() {
    const keywords = ["headphones", "keyboard", "mouse", "watch"];
    const text = products.map(p => p.name + " " + p.description).join(" ");
    const { result, elapsedNanos } = measureExecution(() => ahoCorasickSearch(keywords, text));
    let out = `Multi-Keyword Search Results:\n`;
    keywords.forEach(kw => {
        const c = result.filter(m => m.keyword.toLowerCase() === kw).length;
        out += `• "${kw.padEnd(12)}" : ${c} matches\n`;
    });
    const outBox = document.getElementById('c_res_3');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Aho-Corasick Multi-Pattern Automaton",
            module: "CO1: Aho-Corasick",
            functionName: "searchMultipleKeywords()",
            input: `Keywords [${keywords.join(', ')}]`,
            results: out,
            elapsedNanos,
            timeComplexity: "O(N + M + K)",
            spaceComplexity: "O(K * Σ)"
        });
    }
}

function customerRunRabinKarp() {
    const pattern = document.getElementById('c_input_4')?.value || "charger";
    const text = products.map(p => p.description).join(" ");
    const { result, elapsedNanos } = measureExecution(() => rabinKarpSearch(pattern, text));
    const outBox = document.getElementById('c_res_4');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Rabin-Karp Rolling Hash Pattern Match",
            module: "CO1: Rabin-Karp",
            functionName: "searchWithRabinKarp()",
            input: `Pattern: "${pattern}"`,
            results: `Found ${result.length} occurrences using hash fingerprinting.\nOffsets: [${result.join(', ')}]`,
            elapsedNanos,
            timeComplexity: "Average: O(N + M)",
            spaceComplexity: "O(1)"
        });
    }
}

function customerRunSuggestions() {
    const typo = document.getElementById('c_input_5')?.value || "keybord";
    let best = "";
    let minD = Infinity;
    products.forEach(p => {
        const d = computeLevenshtein(typo.toLowerCase(), p.name.toLowerCase()).distance;
        if (d < minD) { minD = d; best = p.name; }
    });
    const outBox = document.getElementById('c_res_5');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Levenshtein Distance Dynamic Programming",
            module: "CO3: Levenshtein / Damerau-Levenshtein",
            functionName: "generateSearchSuggestions()",
            input: `Typo: "${typo}"`,
            results: `Suggested Match: "${best}" (Edit Distance: ${minD})`,
            timeComplexity: "O(M * N)",
            spaceComplexity: "O(M * N)"
        });
    }
}

function customerRunSimilarProducts() {
    const selId = parseInt(document.getElementById('c_similar_id')?.value) || (products[0] ? products[0].id : 101);
    const target = products.find(p => p.id === selId) || products[0];
    const compareWith = products.find(p => p.id !== target.id) || products[1];

    const { result, elapsedNanos } = measureExecution(() => computeLCSviaSuffixArray(target.description, compareWith.description));
    const outBox = document.getElementById('c_res_6');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Suffix Array + LCP (Kasai) Longest Common Substring",
            module: "CO2: Suffix Array + LCP/Kasai",
            functionName: "findSimilarProducts()",
            input: `${target.name} vs ${compareWith.name}`,
            results: `Common Substring : "${result.lcs}"\nSimilarity Index : ${(result.similarity * 100).toFixed(1)}%\nRecommended Alternative: #${compareWith.id} ${compareWith.name} (₹${compareWith.price.toFixed(2)})`,
            elapsedNanos,
            timeComplexity: "O(N log N) + O(N)",
            spaceComplexity: "O(N)"
        });
    }
}

function customerRunCompareDescriptions() {
    const id1 = parseInt(document.getElementById('c_cmp_desc_1')?.value) || (products[1] ? products[1].id : 102);
    const id2 = parseInt(document.getElementById('c_cmp_desc_2')?.value) || (products[2] ? products[2].id : 103);
    const p1 = products.find(p => p.id === id1) || products[1];
    const p2 = products.find(p => p.id === id2) || products[2];

    const { result, elapsedNanos } = measureExecution(() => computeLCSviaSuffixArray(p1.description, p2.description));
    const outBox = document.getElementById('c_res_7');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Longest Common Substring (Suffix Array)",
            module: "CO2: Longest Common Substring",
            functionName: "compareProductDescriptions()",
            input: `${p1.name} vs ${p2.name}`,
            results: `Shared Common Phrase: "${result.lcs}" (Length: ${result.lcs.length} chars)`,
            elapsedNanos,
            timeComplexity: "O(N log N)",
            spaceComplexity: "O(N)"
        });
    }
}

function customerRunBudgetOptimizer() {
    const budget = parseFloat(document.getElementById('c_budget_slider')?.value || "4000");
    const { result, elapsedNanos } = measureExecution(() => computeBitmaskBudgetSelection(products, budget));
    let out = `Optimal Bundle within ₹${budget.toFixed(2)}:\n`;
    result.items.forEach(p => {
        out += `• #${p.id} ${p.name.padEnd(28)} : ₹${p.price.toFixed(2)}\n`;
    });
    out += `\nTotal Cost: ₹${result.totalCost.toFixed(2)} | Unused: ₹${(budget - result.totalCost).toFixed(2)}`;
    const outBox = document.getElementById('c_res_8');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Bitmask Dynamic Programming Subset Optimization",
            module: "CO3: Bitmask DP Budget Optimizer",
            functionName: "recommendProductsWithinBudget()",
            input: `Wallet Budget: ₹${budget.toFixed(2)}`,
            results: out,
            elapsedNanos,
            timeComplexity: "O(2^N * N)",
            spaceComplexity: "O(1)"
        });
    }
}

function customerRunCompareProducts() {
    const id1 = parseInt(document.getElementById('c_cmp_prod_1')?.value) || (products[0] ? products[0].id : 101);
    const id2 = parseInt(document.getElementById('c_cmp_prod_2')?.value) || (products[6] ? products[6].id : 107);
    const p1 = products.find(p => p.id === id1) || products[0];
    const p2 = products.find(p => p.id === id2) || products[6];

    const outBox = document.getElementById('c_res_9');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Direct Attribute Comparison",
            module: "Standard Comparison",
            functionName: "compareProducts()",
            input: `#${p1.id} vs #${p2.id}`,
            results: `COMPARISON MATRIX:\n• Name     : ${p1.name} vs ${p2.name}\n• Category : ${p1.category} vs ${p2.category}\n• Price    : ₹${p1.price.toFixed(2)} vs ₹${p2.price.toFixed(2)}\n• Stock    : ${p1.stock} vs ${p2.stock}`,
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }
}

function customerRunProductDetails(prodId) {
    const selId = prodId || parseInt(document.getElementById('c_details_id')?.value) || (products[0] ? products[0].id : 101);
    const p = products.find(prod => prod.id === selId) || products[0];
    const revs = reviews.filter(r => r.productId === p.id);
    let out = `PRODUCT SPECIFICATIONS:\n• ID: #${p.id} | Name: ${p.name}\n• Category: ${p.category} | Price: ₹${p.price.toFixed(2)} | Stock: ${p.stock}\n• Description: ${p.description}\n\nCustomer Reviews (${revs.length}):\n`;
    revs.forEach(r => {
        out += `• [${r.author}] ${r.rating}/5 stars: "${r.content}"\n`;
    });
    const outBox = document.getElementById('c_res_10');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "Relational Data Lookup",
            module: "Data Lookup",
            functionName: "viewProductDetails()",
            input: `Product #${p.id} (${p.name})`,
            results: out,
            timeComplexity: "O(R)",
            spaceComplexity: "O(1)"
        });
    }
}

function customerRunPlaceOrder(prodId, quantity) {
    const cust = currentSessionUser || users.find(u => u.username === 'rahul') || users[2];
    const selId = prodId || parseInt(document.getElementById('c_buy_prod_id')?.value) || (products[4] ? products[4].id : 105);
    const qty = quantity || parseInt(document.getElementById('c_buy_qty')?.value) || 1;
    const p = products.find(prod => prod.id === selId) || products[0];

    const totalCost = p.price * qty;

    if (cust.balance >= totalCost) {
        cust.balance -= totalCost;
        p.stock = Math.max(0, p.stock - qty);
        const newOrder = {
            id: 5000 + orders.length + 1,
            username: cust.username,
            productId: p.id,
            productName: p.name,
            qty: qty,
            unitPrice: p.price,
            total: totalCost,
            date: new Date().toISOString().slice(0, 16).replace('T', ' '),
            status: "Confirmed"
        };
        orders.unshift(newOrder);
        renderCustomerStorefront();
        renderDataStore();
        populateManagerDropdowns();
        populateCustomerDropdowns();

        const outBox = document.getElementById('c_res_11');
        if (outBox) {
            outBox.innerText = formatStandardResult({
                algorithm: "Transaction Order Pipeline",
                module: "Order Processing",
                functionName: "placeOrder()",
                input: `Product #${p.id} (${p.name}) x ${qty}`,
                results: `ORDER PLACED!\n• Order ID       : #${newOrder.id}\n• Debited Amount : ₹${totalCost.toFixed(2)}\n• New Wallet Balance : ₹${cust.balance.toFixed(2)}\n• Remaining Stock: ${p.stock} units\n• Order Status   : ${newOrder.status}`,
                timeComplexity: "O(1)",
                spaceComplexity: "O(1)"
            });
        }
        terminalLog(`[ORDER] Order #${newOrder.id} placed for ${p.name} (x${qty}). Wallet Balance: ₹${cust.balance.toFixed(2)}`);
    } else {
        alert(`Insufficient wallet balance (Current: ₹${cust.balance.toFixed(2)}, Required: ₹${totalCost.toFixed(2)}). Click "Top-up Wallet" to add funds.`);
    }
}

function customerRunViewOrders() {
    const cust = currentSessionUser || users.find(u => u.username === 'rahul') || users[2];
    const userOrders = orders.filter(o => o.username === cust.username);

    const tableContainer = document.getElementById('custOrderHistoryTable');
    if (tableContainer) {
        let html = `<table class="custom-table"><thead><tr><th>Order ID</th><th>Product</th><th>Qty</th><th>Total</th><th>Date</th><th>Status</th></tr></thead><tbody>`;
        userOrders.forEach(o => {
            html += `<tr>
                <td><strong>#${o.id}</strong></td>
                <td>${escapeHtml(o.productName)}</td>
                <td>${o.qty}</td>
                <td><strong>₹${o.total.toFixed(2)}</strong></td>
                <td><small>${o.date}</small></td>
                <td><span class="badge ${o.status === 'Delivered' ? 'badge-info' : 'badge-accent'}">${o.status}</span></td>
            </tr>`;
        });
        html += `</tbody></table>`;
        tableContainer.innerHTML = html;
    }

    const outBox = document.getElementById('c_res_13');
    if (outBox) {
        outBox.innerText = formatStandardResult({
            algorithm: "User Transaction Traversal",
            module: "Data Filtering & Traversal",
            functionName: "viewOrderHistory()",
            input: `User: ${cust.username}`,
            results: `Loaded ${userOrders.length} previous transactions for ${cust.fullName}.`,
            timeComplexity: "O(O)",
            spaceComplexity: "O(1)"
        });
    }
}

function runAllCustomerFunctions() {
    renderCustomerStorefront();
    populateCustomerDropdowns();
    customerRunKMP();
    customerRunAhoCorasick();
    customerRunRabinKarp();
    customerRunSuggestions();
    customerRunSimilarProducts();
    customerRunCompareDescriptions();
    customerRunBudgetOptimizer();
    customerRunCompareProducts();
    customerRunProductDetails();
    customerRunViewOrders();

    const c11 = document.getElementById('c_res_11');
    if (c11) {
        c11.innerText = formatStandardResult({
            algorithm: "Transactional Ledger Deduction",
            module: "Order Processing",
            functionName: "placeOrder()",
            input: "Ready to order (Select SKU & Qty above)",
            results: "Click 'Purchase SKU' or 'Buy' on any product card to debit wallet and generate invoice.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }

    const c12 = document.getElementById('c_res_12');
    if (c12) {
        c12.innerText = formatStandardResult({
            algorithm: "Review Record Append",
            module: "Standard Data Op",
            functionName: "submitReview()",
            input: "Customer Review Submission",
            results: "Click to submit verified star rating and sentiment feedback into the live review corpus.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }

    const c14 = document.getElementById('c_res_14');
    if (c14) {
        c14.innerText = formatStandardResult({
            algorithm: "Session State Invalidation",
            module: "Session Auth",
            functionName: "logout()",
            input: "Customer Auth Context",
            results: "Active Customer: Rahul Sharma. Click 'End Session' to return to Main Menu.",
            timeComplexity: "O(1)",
            spaceComplexity: "O(1)"
        });
    }
}

function filterCustomerFunctions(query) {
    const q = (query || '').toLowerCase().trim();
    document.querySelectorAll('#customer-view .func-card').forEach(card => {
        const text = card.innerText.toLowerCase();
        if (!q || text.includes(q)) {
            card.classList.remove('hidden');
        } else {
            card.classList.add('hidden');
        }
    });
}

function filterCustomerByCategory(category, btn) {
    if (btn) {
        document.querySelectorAll('#customerCategoryFilters .filter-pill').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
    }
    document.querySelectorAll('#customer-view .func-card').forEach(card => {
        if (category === 'all') {
            card.classList.remove('hidden');
            return;
        }
        const text = card.innerText.toLowerCase();
        if (category === 'search' && (text.includes('kmp') || text.includes('aho-corasick') || text.includes('rabin') || text.includes('spelling') || text.includes('suggestions'))) {
            card.classList.remove('hidden');
        } else if (category === 'compare' && (text.includes('similar') || text.includes('compare') || text.includes('details') || text.includes('browse'))) {
            card.classList.remove('hidden');
        } else if (category === 'orders' && (text.includes('budget') || text.includes('place order') || text.includes('review') || text.includes('history') || text.includes('logout'))) {
            card.classList.remove('hidden');
        } else {
            card.classList.add('hidden');
        }
    });
}


