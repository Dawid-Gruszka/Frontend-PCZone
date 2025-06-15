# PC Zone - Aplicación Móvil Android

Aplicación móvil desarrollada en **Kotlin** para la gestión de una tienda de productos tecnológicos. Esta app representa el frontend móvil del sistema **PC Zone**, comunicándose con una API REST para funciones como autenticación, navegación por productos, gestión de carrito de compras, favoritos y pedidos.

---

## 📱 Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **Arquitectura**: MVVM
- **UI**: XML, Android Jetpack Navigation
- **Consumo de API**: Retrofit2 + Gson
- **Carga de imágenes**: Glide
- **Gestión de tokens**: TokenManager
- **ViewModels y LiveData**: Jetpack Lifecycle
- **Compatibilidad**: `minSdk 24` - `targetSdk 35`

---

## 🗂️ Estructura del Proyecto

```
com.david.pczone
│
├── Adapter/                # Adaptadores para RecyclerViews
├── API/                    # Retrofit API y gestión de tokens
├── Models/                 # Modelos de datos: Usuario, Producto, Carrito, etc.
├── UI/                     # Fragmentos de la interfaz de usuario por sección
└── MainActivity.kt         # Actividad principal
```

---

## 🔌 Integración con API

Las funciones principales que se comunican con el backend incluyen:

- **Usuarios**: Registro, login y perfil actual.
- **Productos**: Listado, detalle por ID.
- **Carrito**: Agregar, actualizar cantidad, obtener contenido.
- **Favoritos**: Listar, agregar, eliminar productos.
- **Pedidos**: Crear pedido, ver historial de pedidos.

---

## 🧩 Dependencias Usadas

```kotlin
implementation("androidx.core:core-ktx")
implementation("androidx.appcompat:appcompat")
implementation("com.google.android.material:material")
implementation("androidx.constraintlayout:constraintlayout")
implementation("androidx.navigation:navigation-fragment-ktx")
implementation("androidx.navigation:navigation-ui-ktx")
implementation("androidx.lifecycle:lifecycle-livedata-ktx")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
implementation("com.github.bumptech.glide:glide:4.16.0")
ksp("com.github.bumptech.glide:ksp:4.16.0")
implementation("com.squareup.retrofit2:retrofit")
implementation("com.squareup.retrofit2:converter-gson")
```

---

## 📷 Capturas

<table>
  <tr>
    <td align="center"><strong>Pantalla principal</strong></td>
    <td align="center"><strong>Pantalla Cesta</strong></td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/c411814e-4f7f-4b3d-93b5-75c0f71c728b" width="300"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/c9932fba-39fe-4903-abec-18563a373366" width="300"/>
    </td>
  </tr>
  <tr>
    <td align="center"><strong>Menú</strong></td>
    <td align="center"><strong>Favoritos</strong></td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/23b8a613-ae1c-4828-ad07-a887e4801f6a" width="300"/>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/773cffb2-b85a-49b9-81a7-a6056feb1999" width="300"/>
    </td>
  </tr>
  <tr>
    <td align="center"><strong>Perfil</strong></td>
    <td align="center"></td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/e3771ca1-e76a-43ef-87c5-88907a190f50" width="300"/>
    </td>
  </tr>
</table>


---

## 🚀 Cómo Ejecutar

1. Clona el repositorio
2. Abre el proyecto en **Android Studio**.
3. Sincroniza las dependencias con Gradle.
4. Ejecuta en un dispositivo físico o emulador con Android 7.0+ (API 24+).

---

## 📝 Autor

Desarrollado por **Dawid Gruszka** como parte de un proyecto educativo del ciclo DAM 🧑‍💻.
