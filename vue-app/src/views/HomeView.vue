<template>
  <main class="home" aria-label="Inicio">
    <section class="welcome">
      <h1 tabindex="0">
        Bienvenido a <span class="brand">Reserva Hoteles</span>
      </h1>
      <p class="subtitle">¡Reserva tu habitación en los mejores hoteles de manera fácil, rápida y segura!</p>
      <nav aria-label="Accesos rápidos">
        <ul class="quick-links">
          <li>
            <router-link to="/hoteles">
              <span class="icon" aria-hidden="true">🏨</span>
              Ver Hoteles
            </router-link>
          </li>
          <li>
            <router-link to="/habitaciones">
              <span class="icon" aria-hidden="true">🛏️</span>
              Buscar Habitaciones
            </router-link>
          </li>
          <li>
            <router-link to="/reservas">
              <span class="icon" aria-hidden="true">📅</span>
              Mis Reservas
            </router-link>
          </li>
        </ul>
      </nav>
    </section>
    <section class="features" aria-label="Ventajas">
      <h2>¿Por qué elegirnos?</h2>
      <ul>
        <li><span class="check">✔️</span> Proceso de reserva simple y rápido</li>
        <li><span class="check">✔️</span> Gestión segura con autenticación</li>
        <li><span class="check">✔️</span> Panel de administración para hoteles</li>
        <li><span class="check">✔️</span> Accesible y responsive en cualquier dispositivo</li>
        <li><span class="check">✔️</span> Soporte y atención personalizada</li>
      </ul>
      <div class="info-msg" role="status">
        <span class="info-icon" aria-hidden="true">ℹ️</span>
        Recuerda: puedes consultar tus reservas y gestionar tus datos en cualquier momento desde el menú principal.
      </div>
    </section>
  </main>
</template>

<style scoped>
.home {
  max-width: 700px;
  margin: 2rem auto;
  padding: 2rem;
  background: #f7fbfd;
  border-radius: 14px;
  box-shadow: 0 2px 16px #1976d220;
}

.welcome {
  text-align: center;
  margin-bottom: 2.5rem;
}

.brand {
  color: #1976d2;
  font-weight: bold;
  letter-spacing: 1px;
}

.subtitle {
  color: #3a6ea5;
  margin-top: 0.5rem;
  margin-bottom: 1.5rem;
  font-size: 1.15rem;
}

.quick-links {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin: 1.5rem 0;
  padding: 0;
  list-style: none;
}

.quick-links a {
  background: linear-gradient(90deg, #3a6ea5 60%, #1976d2 100%);
  color: #fff;
  padding: 0.85rem 1.7rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 500;
  font-size: 1.08rem;
  box-shadow: 0 2px 8px #1976d220;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: background 0.2s, box-shadow 0.2s;
}

.quick-links a:hover,
.quick-links a:focus {
  background: linear-gradient(90deg, #125ea2 60%, #1976d2 100%);
  box-shadow: 0 4px 16px #1976d240;
}

.icon {
  font-size: 1.3em;
}

.features {
  margin-top: 2rem;
  background: #eaf6fb;
  padding: 1.5rem 1rem 1rem 1rem;
  border-radius: 10px;
  box-shadow: 0 1px 8px #1976d210;
}

.features h2 {
  color: #1976d2;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.features ul {
  list-style: none;
  padding: 0;
  margin-bottom: 1.2rem;
}

.features li {
  margin-bottom: 0.7rem;
  font-size: 1.08rem;
  color: #3a6ea5;
  display: flex;
  align-items: center;
}

.check {
  color: #1976d2;
  margin-right: 0.5rem;
  font-size: 1.1em;
}

.info-msg {
  background: #e3f2fd;
  color: #1976d2;
  border-left: 5px solid #3a6ea5;
  padding: 0.9rem 1rem;
  border-radius: 6px;
  font-size: 1.05rem;
  display: flex;
  align-items: center;
  gap: 0.7rem;
  margin-top: 1rem;
}

.info-icon {
  font-size: 1.3em;
}

@media (max-width: 600px) {
  .home {
    padding: 1rem;
  }

  .quick-links {
    flex-direction: column;
    gap: 1rem;
  }

  .features {
    padding: 1rem 0.5rem;
  }
}
</style>