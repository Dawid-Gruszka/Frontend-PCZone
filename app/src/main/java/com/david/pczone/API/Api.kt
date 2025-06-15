package com.david.pczone.API

import com.david.pczone.Models.CartItems
import com.david.pczone.Models.Category
import com.david.pczone.Models.Order
import com.david.pczone.Models.OrderCreateResponse
import com.david.pczone.Models.Product
import com.david.pczone.Models.Token
import com.david.pczone.Models.User
import com.david.pczone.Models.UserCreate
import retrofit2.http.*
import retrofit2.Response

interface Api {

    @POST("/Users/register")
    suspend fun register(@Body userCreate: UserCreate): Response<User>

    @FormUrlEncoded
    @POST("/Users/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<Token>

    @GET("/Users/users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): Response<User>

    @GET("categorias")
    suspend fun getCategorias(): Response<List<Category>>

    @GET("productos")
    suspend fun getProductos(): Response<List<Product>>

    @GET("productos/{id}")
    suspend fun getProducto(
        @Path("id") id: Int
    ): Response<Product>

    @POST("cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Query("product_id") productId: Int,
        @Query("quantity") quantity: Int = 1
    ): Response<Unit>

    @GET("cart/full")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): Response<List<CartItems>>

    @PATCH("cart/update/by_product/{productId}")
    suspend fun updateCart(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int,
        @Body quantity: Int
    ): Response<Unit>

    @GET("favorites/full")
    suspend fun getFav(
        @Header("Authorization") token: String
    ): Response<List<Product>>

    @POST("favorites/add")
    suspend fun addToFav(
        @Header("Authorization") token: String,
        @Query("product_id") productId: Int
    ): Response<Unit>

    @DELETE("favorites/delete/by_product/{productId}")
    suspend fun deleteFav(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int
    ): Response<Unit>

    @POST("order/ordercreate")
    suspend fun createOrder(
        @Header("Authorization") token: String
    ): Response<OrderCreateResponse>

    @GET("order/by_user")
    suspend fun getOrdersByUser(
        @Header("Authorization") token: String
    ): Response<List<Order>>
}
