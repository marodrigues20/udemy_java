package com.javathlon.section18.reqres;

import com.google.gson.Gson;
import com.javathlon.section18.reqres.model.BaseRequest;
import com.javathlon.section18.reqres.model.BaseResponse;
import com.javathlon.section18.reqres.model.GetUserResponse;
import com.javathlon.section18.reqres.model.User;
import com.javathlon.section18.reqres.model.UserLoginRequest;
import com.javathlon.section18.reqres.model.UserLoginResponse;
import com.javathlon.section18.reqres.model.UserRegistrationRequest;
import com.javathlon.section18.reqres.model.UserRegistrationResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class ReqresApplication {

  public static void main(String[] args) {

    ReqresApplication reqresApplication = new ReqresApplication();
    UserRegistrationResponse response = reqresApplication.register("eve.holt@reqres.in", "pistol");

    if (!"".equals(response.getToken())) {
      System.out.println("registration is succeful");
    }

    UserLoginResponse userLoginResponse = reqresApplication.login("eve.holt@reqres.in", "pistol");

    if (!"".equals(userLoginResponse.getToken())) {
      System.out.println("login is succeful");
    }

    GetUserResponse userResponse = reqresApplication.getUsers(5,1);

    for (User user: userResponse.getData()) {

      System.out.println(user.getFirstName());

    }

  }

  private GetUserResponse getUsers(int page, int perPage){
    String targetUrl = "https://reqres.in/api/users";

    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("page", page);
    parameters.put("per_page", perPage);

   return (GetUserResponse) doGet(targetUrl, parameters, GetUserResponse.class);
  }

  private BaseResponse doGet(String targetUrl, Map<String, Object> queryParameters, Class<? extends BaseResponse> clazz){

    try{

      if(queryParameters != null){

        int i = 0;
        for(Map.Entry<String, Object> entry : queryParameters.entrySet()){

          if(i == 0){
            targetUrl += "?";
          } else {
            targetUrl += "&";
          }
          targetUrl += entry.getKey() + "=" + entry.getValue().toString();
        }

      }

      System.out.println(targetUrl);


      URL url = new URL(targetUrl);
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.addRequestProperty("User-Agent", "Mozilla/5.0");

      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));

      String line;
      StringBuffer stringBuffer = new StringBuffer();

      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
      }

      String response = stringBuffer.toString();

      BaseResponse responseObject = new Gson().fromJson(response, clazz);

      return responseObject;

    }catch (ProtocolException e){
      e.printStackTrace();
    }catch (MalformedURLException e){
      e.printStackTrace();
    }catch (IOException e){
      e.printStackTrace();
    }

    return null;

  }

  private BaseResponse doPost(final String targetUrl, BaseRequest request,
      Class<? extends BaseResponse> clazz) {

    try {
      URL url = new URL(targetUrl);
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setRequestMethod("POST");

      conn.addRequestProperty("Content-Type", "application/json");
      conn.addRequestProperty("User-Agent", "Mozilla/5.0");
      conn.setDoOutput(true);

      String requestPayload = new Gson().toJson(request);

      OutputStream outputStream = conn.getOutputStream();
      outputStream.write(requestPayload.getBytes());
      outputStream.close();

      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));

      String line;
      StringBuffer stringBuffer = new StringBuffer();

      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
      }

      String response = stringBuffer.toString();

      BaseResponse responseObject = new Gson().fromJson(response, clazz);

      return responseObject;


    } catch (IOException e) {
      e.printStackTrace();

    }

    return null;

  }

  private UserRegistrationResponse register(String userName, String password) {
    String targetUrl = "https://reqres.in/api/register";
    UserRegistrationRequest request = new UserRegistrationRequest();
    request.setEmail(userName);
    request.setPassword(password);
    return (UserRegistrationResponse) doPost(targetUrl, request, UserRegistrationResponse.class);
  }

  private UserLoginResponse login(String userName, String password) {
    String targetUrl = "https://reqres.in/api/login";

    UserLoginRequest request = new UserLoginRequest();
    request.setEmail(userName);
    request.setPassword(password);

    return (UserLoginResponse) doPost(targetUrl, request, UserLoginResponse.class);
  }

}
