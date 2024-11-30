package com.scrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrs.dto.LoginDTO;
import com.scrs.dto.OtpDTO;
import com.scrs.service.AuthService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping
	public ResponseEntity<String> testinAuth() {
		return new ResponseEntity<>("Path working bro, all good", HttpStatus.OK);
	}

	@PostMapping("login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginInfo) {
		String msg = authService.authenticate(loginInfo.getUsername(), loginInfo.getPassword());

		if (msg.startsWith("OTP has been sent")) {

			return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED);

		} else if (msg.equalsIgnoreCase("Invalid Credentials")) {

			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);

		} else if (msg.equalsIgnoreCase("User doesn't exist")) {

			return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);

		} else {

			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpDTO otp) {
		String res = authService.verifyOtp(otp.getOtp()).toString();
		System.out.println("RES: "+ res);
		if (!res.startsWith("Invalid")) {
			System.out.println("OKAY OKAY");
			return ResponseEntity.ok().body(res);
		} else {
			return ResponseEntity.badRequest().body(res);
		}
	}

}
