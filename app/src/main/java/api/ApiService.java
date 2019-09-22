package api;


import model.BusState;
import model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
 
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of Article
    */
    @GET("/smartdispatcher/android/login/{login}/{password}")
    Call<User> getLoginData(
                @Path("login") String login
              , @Path("password") String password
    );

    @GET("/smartdispatcher/android/location_data/{token}/{busId}")
    Call<BusState> getBusLocationData(
            @Path("token") String token
           ,@Path("busId") String busid
    );
}