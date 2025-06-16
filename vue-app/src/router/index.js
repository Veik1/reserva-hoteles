import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import HotelsListView from '../views/HotelsListView.vue'
import HotelDetailsView from '../views/HotelDetailsView.vue'
import RoomsListView from '../views/RoomsListView.vue'
import ClientsView from '../views/ClientesView.vue'
import BookingsListView from '../views/ReservasView.vue'
import AdminHotelsView from '../views/AdminHotelsView.vue'
import AdminRoomsView from '../views/AdminRoomsView.vue'
import AdminClientsView from '../views/AdminClientsView.vue'
import AdminBookingsView from '../views/AdminBookingsView.vue'
import LoginView from '../views/LoginView.vue'
import { useAuthStore } from '../store/auth'

const routes = [
  { path: '/', name: 'home', component: HomeView },
  { path: '/login', name: 'login', component: LoginView },
  { path: '/hoteles', name: 'hoteles', component: HotelsListView },
  { path: '/hoteles/:id', name: 'hotel-details', component: HotelDetailsView },
  { path: '/habitaciones', name: 'habitaciones', component: RoomsListView },
  { path: '/clientes', name: 'clientes', component: ClientsView },
  { path: '/reservas', name: 'reservas', component: BookingsListView },
  // Admin
  { path: '/admin/hoteles', name: 'admin-hoteles', component: AdminHotelsView, meta: { requiresAdmin: true } },
  { path: '/admin/habitaciones', name: 'admin-habitaciones', component: AdminRoomsView, meta: { requiresAdmin: true } },
  { path: '/admin/clientes', name: 'admin-clientes', component: AdminClientsView, meta: { requiresAdmin: true } },
  { path: '/admin/reservas', name: 'admin-reservas', component: AdminBookingsView, meta: { requiresAdmin: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()
  if (to.meta.requiresAdmin && auth.role !== 'admin') {
    next('/login')
  } else if (to.meta.requiresAuth && !auth.token) {
    next('/login')
  } else {
    next()
  }
})

export default router