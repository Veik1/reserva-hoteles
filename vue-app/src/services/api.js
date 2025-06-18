import axios from 'axios'

const api = axios.create({ baseURL: '/api' })

// Hoteles
export function getHotels() {
  return api.get('/hoteles')
}
export function getHotelById(id) {
  return api.get(`/hoteles/${id}`)
}
export function createHotel(data) {
  return api.post('/hoteles', data)
}
export function updateHotel(id, data) {
  return api.put(`/hoteles/${id}`, data)
}
export function deleteHotel(id) {
  return api.delete(`/hoteles/${id}`)
}

// Habitaciones
export function getRooms() {
  return api.get('/habitaciones')
}
export function getRoomById(id) {
  return api.get(`/habitaciones/${id}`)
}
export function createRoom(data) {
  return api.post('/habitaciones', data)
}
export function updateRoom(id, data) {
  return api.put(`/habitaciones/${id}`, data)
}
export function deleteRoom(id) {
  return api.delete(`/habitaciones/${id}`)
}

// Clientes
export function getClients() {
  return api.get('/clientes')
}
export function getClientById(id) {
  return api.get(`/clientes/${id}`)
}
export function createClient(data) {
  return api.post('/clientes', data)
}
export function updateClient(id, data) {
  return api.put(`/clientes/${id}`, data)
}
export function deleteClient(id) {
  return api.delete(`/clientes/${id}`)
}

// Reservas
export function getBookings() {
  return api.get('/reservas')
}
export function getBookingById(id) {
  return api.get(`/reservas/${id}`)
}
export function createBooking(data) {
  return api.post('/reservas', data)
}
export function updateBooking(id, data) {
  return api.put(`/reservas/${id}`, data)
}
export function deleteBooking(id) {
  return api.delete(`/reservas/${id}`)
}

// Auth
export function login({ username, password }) {
  return api.post('/auth/login', { username, password })
}
export function register({ username, password, ...rest }) {
  return api.post('/auth/register', { username, password, ...rest })
}

export default api