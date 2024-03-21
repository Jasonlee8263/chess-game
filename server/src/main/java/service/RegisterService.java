package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import model.requestAndResult.RegisterRequest;
import model.requestAndResult.RegisterResult;

import java.sql.SQLException;
import java.util.UUID;

public class RegisterService {
    private AuthDAO authDAO;
    private UserDAO userDAO;
    public RegisterService(AuthDAO authDAO,UserDAO userDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public RegisterResult register(RegisterRequest request) throws SQLException, DataAccessException {
        if(request.password()==null){
            return new RegisterResult(null, null,"Error: bad request");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(request.password());
        UserData user = new UserData(request.username(), hashedPassword, request.email());
        if(user.username()==null || user.password()==null || user.email()==null){
            return new RegisterResult(null, null,"Error: bad request");
        }
        else if(userDAO.getUser(user.username())!=null && userDAO.getUser(user.username()).username().equals(user.username())){
            return new RegisterResult(null, null,"Error: already taken");
        }
        else{
            userDAO.createUser(user);
            String authToken = UUID.randomUUID().toString();
            authDAO.createAuth(new AuthData(request.username(), authToken));
            return new RegisterResult(request.username(), authToken,null);
        }
    }
}
