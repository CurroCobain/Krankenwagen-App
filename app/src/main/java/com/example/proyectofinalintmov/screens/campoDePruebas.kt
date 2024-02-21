package com.example.proyectofinalintmov.screens

/*
@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomePage(
    navController: NavHostController, viewModel: WelcomePageViewModel
) {
    val showMenu by viewModel.showMenu.collectAsState()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed )
    Menu_desplegable(
        navController = navController,
        drawerState =  drawerState) {
        ContenidoWelcome(
            navController = navController,
            menuDesplegado = showMenu,
            viewModel = viewModel)

    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContenidoWelcome(
    navController: NavHostController,
    menuDesplegado: Boolean,
    viewModel: WelcomePageViewModel
) {
    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = stringResource(R.string.bienvenido_dr_house)
            )
        }
    }, content = {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            // Fondo de la pantalla
            Image(
                painter = painterResource(id = R.drawable.fondo),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize()
            )
            Row(modifier = Modifier.fillMaxSize()) {
                BarraLateral(onWelcTapped = { navController.navigate(Routes.PantallaWelcome.route) },
                    onAmbTapped = { navController.navigate(Routes.PantallaAmbulances.route) },
                    onHospTapped = { navController.navigate(Routes.PantallaHospitals.route) },
                    onDocTapped = { navController.navigate(Routes.PantallaDocs.route) })
                ScrollProvincias(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 100.dp)
                )
            }

        }

    }, bottomBar = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BarraMenuUsuario(modifier = Modifier.padding(start = 100.dp), onMenuTapped = {
                viewModel.openMenu()
            }, onSesionTapped = { // TODO:
            })
        }
    })

}

@Composable
fun Menu_desplegable(
    navController: NavHostController, drawerState: DrawerState, contenido: @Composable () -> Unit
) {
    val viewModel = WelcomePageViewModel()
    val showMenu by viewModel.showMenu.collectAsState()
    val menu_items = listOf(
        Iterms_menu_lateral.Item_menu, Iterms_menu_lateral.Item_sesion
    )
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            menu_items.forEach { item ->
                NavigationDrawerItem(label = { Text(text = item.tittle) },
                    selected = CurrentRoute(navController = navController) == item.route,
                    onClick = { /*TODO*/ })
            }
        }
    }) {
        ContenidoWelcome(
            navController = rememberNavController(),
            menuDesplegado = showMenu,
            viewModel = viewModel)
    }

}

@Composable
fun CurrentRoute(navController: NavHostController): String? =
    navController.currentBackStackEntryAsState().value?.destination?.route

/*
Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = stringResource(R.string.bienvenido_dr_house)
            )
        }
    }, content = {
        ContentenidoWelcome(
            navController = navController, menuDesplegado = showMenu
        )
    }, bottomBar = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BarraMenuUsuario(modifier = Modifier.padding(start = 100.dp), onMenuTapped = {
                viewModel.openMenu()
            }, onSesionTapped = { // TODO:
            })
        }
    })
 */

 */