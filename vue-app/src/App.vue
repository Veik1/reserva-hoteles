<template>
  <header>
    <nav class="navbar">
      <div class="logo">Reserva Hoteles</div>
      <ul class="nav-links">
        <li><router-link to="/">Inicio</router-link></li>
        <li><router-link to="/hoteles">Hoteles</router-link></li>
        <li><router-link to="/habitaciones">Habitaciones</router-link></li>
        <li><router-link to="/clientes">Clientes</router-link></li>
        <li><router-link to="/reservas">Reservas</router-link></li>
      </ul>
    </nav>
  </header>
  <main class="main-content">
    <router-view />
  </main>
</template>

<style scoped>
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #1976d2;
  padding: 1rem 2rem;
}
.logo {
  color: #fff;
  font-size: 1.5rem;
  font-weight: bold;
}
.nav-links {
  list-style: none;
  display: flex;
  gap: 1.5rem;
  margin: 0;
  padding: 0;
}
.nav-links a {
  color: #fff;
  text-decoration: none;
  font-weight: 500;
}
.nav-links a.router-link-active {
  border-bottom: 2px solid #fff;
}
.main-content {
  padding: 2rem;
  max-width: 900px;
  margin: 0 auto;
}
@media (max-width: 600px) {
  .navbar {
    flex-direction: column;
    align-items: flex-start;
  }
  .main-content {
    padding: 1rem;
  }
}
</style>