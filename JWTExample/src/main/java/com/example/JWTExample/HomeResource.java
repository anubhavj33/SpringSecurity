package com.example.JWTExample;

import com.example.JWTExample.models.AuthenticationRequest;
import com.example.JWTExample.models.AuthenticationResponse;
import com.example.JWTExample.services.MyUserDetailService;
import com.example.JWTExample.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HomeResource {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private  JwtUtil jwtTokenUtil;
    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
      try {
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
          );
      } catch (BadCredentialsException e){
          throw  new Exception("Incorrect username or password",e);
      }
      final UserDetails userDetails=userDetailService
              .loadUserByUsername(authenticationRequest.getUsername());
      final String jwt = jwtTokenUtil.generateToken(userDetails);
      return ResponseEntity.ok(new AuthenticationResponse((jwt)));
    }
}
