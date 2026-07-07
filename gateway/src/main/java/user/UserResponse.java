package user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponse {
	
	private String id;
	private String keycloakId;
	private String email;
	private String FName;
	private String LName;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
