<template>
    <div class="spinner" aria-label="Cargando" role="status"></div>
</template>

<style scoped>
.spinner {
    border: 4px solid #e3f2fd;
    border-top: 4px solid #3a6ea5;
    border-radius: 50%;
    width: 32px;
    height: 32px;
    animation: spin 1s linear infinite;
    margin: 1rem auto;
}

@keyframes spin {
    to {
        transform: rotate(360deg);
    }
}
</style>